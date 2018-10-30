package com.company.sftp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
@EnableScheduling
@ImportResource("classpath*:SftpOutboundGatewayFlow.xml")
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(false).run(args);
    }
}