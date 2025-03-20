package base64;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class EncodeDemoTest {

    @Test
    public void testEncodeToBase64() {
        EncodeDemo encodeDemo = new EncodeDemo();
        String input = "Hello, World!";
        String expectedOutput = "SGVsbG8sIFdvcmxkIQ==";
        String actualOutput = encodeDemo.encodeToBase64(input);
        assertEquals(expectedOutput, actualOutput, "The Base64 encoding should match the expected output.");
    }
}