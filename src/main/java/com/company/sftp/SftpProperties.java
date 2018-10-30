package com.company.sftp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;



@Validated
@Configuration
@ConfigurationProperties("bulksftp")
public class SftpProperties {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private Source source;

    private String destination;

    private String cache;

    private int rate;

    private String remoteDirectory;

    private String filePattern;

    public String getDestination() {
        return destination;
    }

    public int getRate() {
        return rate;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCache() {
        return cache;
    }

    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    public void setRemoteDirectory(String remoteDirectory) {
        this.remoteDirectory = remoteDirectory;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public static class Source {

        private String host;
        private String userName;
        private String password;
        private int port;
        private Resource privateKey;
        private String privateKeyPassphrase;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public Resource getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(Resource privateKey) {
            this.privateKey = privateKey;
        }

        public String getPrivateKeyPassphrase() {
            return privateKeyPassphrase;
        }

        public void setPrivateKeyPassphrase(String privateKeyPassphrase) {
            this.privateKeyPassphrase = privateKeyPassphrase;
        }
    }
}
