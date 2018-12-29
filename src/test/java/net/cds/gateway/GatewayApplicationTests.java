package net.cds.gateway;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {

    @Value("${filter.PreLogFilter.pattern}")
    private String pattern = "(^/product1/.*)|(^/product2/.*)";
    @Test
    public void contextLoads() {
    }

    @Test
    public void filterPattern(){
        String content = "/product2/as am noob from runoob.com.";
        boolean isMatch = Pattern.matches(pattern, content);
        Assert.assertTrue(isMatch);
    }

}

