package base64;

import com.sun.org.apache.regexp.internal.RE;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncodeDemo {
    public static void main(String[] args) {
        String str="hello world!";
       encodeToBase64(str);
    }

    /**
     * 将字符串编码为Base64格式，然后解码回原始字符串
     * 此方法主要用于演示Base64编码和解码的过程，以及如何使用日志记录不同阶段的信息
     *
     * @param str 需要编码和解码的原始字符串
     * @return 解码后的字符串，预期与原始输入相同
     */
    public static String encodeToBase64(String str){
        // 记录原始消息
        log.info("原消息为：{}",str);

        // 将原始字符串编码为Base64格式
        byte[] encode = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
        // 记录编码后的消息
        log.info("编码后：{}",encode);

        // 将Base64编码后的数据解码回原始字符串
        byte[] decode =
                Base64.getDecoder().decode(encode);

        // 将解码后的字节数组转换为字符串
        String s = new String(decode);
        // 记录解码后的消息
        log.info("解码后：{}",s);

        // 返回解码后的字符串
        return s;
    }

}
