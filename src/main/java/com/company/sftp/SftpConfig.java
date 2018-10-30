package com.company.sftp;

import com.jcraft.jsch.ChannelSftp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

@Configuration
public class SftpConfig {

    private static final Logger logger = LoggerFactory.getLogger(SftpConfig.class);

    @Autowired
    protected SftpProperties sftpProperties;

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory()
    {
        logger.info("SessionFactory Host: {}, Port: {}", sftpProperties.getSource().getHost(), sftpProperties.getSource().getPort());

        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(sftpProperties.getSource().getHost());
        factory.setPort(sftpProperties.getSource().getPort());
        factory.setUser(sftpProperties.getSource().getUserName());
        if (sftpProperties.getSource().getPrivateKey() != null) {
            factory.setPrivateKey(sftpProperties.getSource().getPrivateKey());
            factory.setPrivateKeyPassphrase(sftpProperties.getSource().getPrivateKeyPassphrase());
        } else {
            factory.setPassword(sftpProperties.getSource().getPassword());
        }

        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<>(factory);
    }
}