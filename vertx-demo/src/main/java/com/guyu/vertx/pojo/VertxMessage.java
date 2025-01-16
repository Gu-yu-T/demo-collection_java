package com.guyu.vertx.pojo;

import io.vertx.mqtt.MqttEndpoint;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VertxMessage {
    private LocalDateTime time ;
    private MqttEndpoint endpoint;
}
