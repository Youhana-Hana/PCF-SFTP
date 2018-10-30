package com.company.sftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SftpOutboundGatewayFlowConfig {
    private static final Logger logger = LoggerFactory.getLogger(SftpOutboundGatewayFlowConfig.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    protected ToSftpFlowGateway sftpFlowGateway;

    @Autowired
    protected SftpProperties sftpProperties;

    @Scheduled(fixedRateString = "${bulksftp.rate}")
    public void syncFiles() {
        logger.info("syncFiles {}", dateFormat.format(new Date()));

        List<Boolean> results = sftpFlowGateway.lsGetAndRmFiles(sftpProperties.getRemoteDirectory());

        logger.info("syncFiles results: {}", results);

        if(results == null) {
            logger.info("syncFiles, no files transferred");
        }
    }
}
