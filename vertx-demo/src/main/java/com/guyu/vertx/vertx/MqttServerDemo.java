package com.guyu.vertx.vertx;

import com.guyu.vertx.pojo.VertxMessage;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.codes.MqttSubAckReasonCode;
import io.vertx.mqtt.messages.impl.MqttPublishMessageImpl;
import io.vertx.mqtt.messages.impl.MqttSubscribeMessageImpl;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import javax.crypto.interfaces.PBEKey;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class MqttServerDemo {

    private final static EmitterProcessor<VertxMessage> PROCESSOR = EmitterProcessor.create(false);
    private final static FluxSink<VertxMessage> CONNECTIONSINK = PROCESSOR.sink(FluxSink.OverflowStrategy.BUFFER);

    private final static AtomicInteger count = new AtomicInteger(1);

    private void init() {
        Vertx vertx = Vertx.vertx();
        MqttServer mqttServer = MqttServer.create(vertx);
        mqttServer.exceptionHandler(error -> {
                    log.error(error.getMessage(), error);
                })
                // 将连接塞入到连接数据流中，连接相关逻辑将由数据流的订阅者处理，关注方法handleConnection
                .endpointHandler(endpoint -> {
                    log.info("服务端处理客户端：{}的请求中。。。", endpoint.auth().getUsername());
                    CONNECTIONSINK.next(new VertxMessage(LocalDateTime.now(), endpoint));
                });

        mqttServer.listen(8080)
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        System.out.println("MQTT server is listening on port " + ar.result().actualPort());
                    } else {
                        System.out.println("Error on starting the server");
                        ar.cause().printStackTrace();
                    }
                });
        this.handlerVertxMsg();
    }

    public void handlerVertxMsg() {
        PROCESSOR.onBackpressureBuffer(1000, e -> {
                    log.warn("vertx处理能力达到了上限，开启背压模式");
                }, BufferOverflowStrategy.DROP_LATEST)
                .publishOn(Schedulers.newSingle("Txy-Pulblish"), 1)
                .parallel()
                .runOn(Schedulers.newParallel("Txy_consume", 4), 1)
                .map(Function.identity())
                .map(msg -> {
                    log.info("接收到服务的请求时间为：{}", msg.getTime());
                    MqttEndpoint endpoint = msg.getEndpoint();
                    //处理客户端关闭
                    endpoint.closeHandler(handler -> {
                        log.warn("客户端：{}关闭连接", endpoint.auth().getUsername());
                    });
                    //处理客户端发送请求
                    endpoint.publishHandler(mqttPublishMessage -> {
                        this.handlerPublishRequest(endpoint, (MqttPublishMessageImpl) mqttPublishMessage);
                    }).publishReleaseHandler(messageId -> {
                        endpoint.publishComplete(messageId);
                    });
                    //处理客户端订阅
                    endpoint.subscribeHandler(mqttSubscribeMessage -> {
                        this.handlerSubscribeRequest(endpoint, (MqttSubscribeMessageImpl) mqttSubscribeMessage);
                    });
                    //处理客户端取消订阅
                    endpoint.unsubscribeHandler(unsubscribe -> {
                        for (String t : unsubscribe.topics()) {
                            log.info("Unsubscription for " + t);
                        }
                        // ack the subscriptions request
                        endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
                    });
                    //心跳检测
                    endpoint.pingHandler(v -> {
                        log.info("Ping received from client");
                    });

                    endpoint.accept();
                    log.info("调用com.guyu.vertx.vertx.MqttServerDemo.handlerVertxMsg的次数为：{}", count.get());
                    count.getAndIncrement();
                    return msg;
                }).subscribe();
    }

    public void handlerPublishRequest(MqttEndpoint endpoint, MqttPublishMessageImpl message) {
        log.info("接收到客户端：{}发送的请求，topic是：{}，内容是：{}", endpoint.auth().getUsername(), message,
                message.payload());
        Buffer response = Buffer.buffer("服务已经收到你的请求：" + message.payload() + ";正在处理，请稍后！");
        endpoint.publish(message.topicName(), response, MqttQoS.AT_MOST_ONCE, false, false, 0, result -> {
            if (!result.succeeded()) {
                log.error("MQTT服务器发送消息失败！");
            }
        });
        //根据通话等级来进行设置
        if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
            endpoint.publishAcknowledge(message.messageId());
        } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
            endpoint.publishReceived(message.messageId());
        }
    }

    public void handlerSubscribeRequest(MqttEndpoint endpoint, MqttSubscribeMessageImpl message) {
        List<MqttSubAckReasonCode> reasonCodes = new ArrayList<>();
        for (MqttTopicSubscription s : message.topicSubscriptions()) {
            System.out.println("Subscription for " + s.topicName() + " with QoS " + s.qualityOfService());
            reasonCodes.add(MqttSubAckReasonCode.qosGranted(s.qualityOfService()));
        }
        // ack the subscriptions request
        endpoint.subscribeAcknowledge(message.messageId(), reasonCodes, MqttProperties.NO_PROPERTIES);


        String topicName = message.topicSubscriptions().get(0).topicName();
        log.info("接收到客户端：{}发送的订阅请求，topic是：{}", endpoint.auth().getUsername(), topicName);
        Buffer response = Buffer.buffer("服务已经收到你的订阅：" + topicName + ";正在处理，请稍后！");
        endpoint.publish(topicName, response, MqttQoS.AT_MOST_ONCE, false, false, 0, result -> {
            if (!result.succeeded()) {
                log.error("MQTT服务器发送消息失败！");
            }
        });
    }

    public static void main(String[] args) {
        MqttServerDemo mqttServerDemo = new MqttServerDemo();
        mqttServerDemo.init();

    }
}


