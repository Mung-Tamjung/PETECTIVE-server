package com.mungtamjung.petective.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt") //java에서 properties값 가져옴
public class JwtProperties {


    private String issuer;

    private String secretKey;
}
