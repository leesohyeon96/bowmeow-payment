package com.bowmeow.payment.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component // @ConstructorBinding : springboot 2.2 이후 더 간편한 방법
@ConfigurationProperties(prefix = "import.api")
public class ImportProperties {
    private String key;
    private String secret;

// 위에껄 쓰려면 아래 별도 설정해줘야 함 > todo: 후에 설정 예정
//    @Configuration
//    @EnableConfigurationProperties(IamportProperties.class)
//    public class AppConfig {
//        // 설정 내용
//    }
}
