package com.company.sftp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
public class SftpPropertiesTest {

    @Autowired
    SftpProperties sftpProperties;

    @Test
    public void testConfig(){
        assertThat("src/test/fixtures/destination").isEqualToIgnoringCase(sftpProperties.getDestination());
        assertThat("elaborati").isEqualToIgnoringCase(sftpProperties.getCache());
        assertThat("/").isEqualToIgnoringCase(sftpProperties.getRemoteDirectory());
        assertThat(".*[.]gz").isEqualToIgnoringCase(sftpProperties.getFilePattern());
        assertThat(1000).isEqualTo(sftpProperties.getRate());
    }

    @Test
    public void testSource(){
        assertThat("127.0.0.1").isEqualToIgnoringCase(sftpProperties.getSource().getHost());
        assertThat("test").isEqualToIgnoringCase(sftpProperties.getSource().getUserName());
        assertThat("password").isEqualToIgnoringCase(sftpProperties.getSource().getPassword());
        assertThat(10022).isEqualTo(sftpProperties.getSource().getPort());
    }
}