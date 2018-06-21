package com.cardprocessor.cardprocessorbatchproject.configuration;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

import java.io.File;


@Configuration
public class FtpConfig {

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.user}")
    private String ftpUser;

    @Value("${ftp.password}")
    private String password;


    @Value("/")
    private String ftpRemoteDirectory;

    @Bean
    public SessionFactory<FTPFile>  ftpSessionFactory() {
        DefaultFtpSessionFactory factory;
        factory = new DefaultFtpSessionFactory();
        factory.setHost(ftpHost);
        factory.setPort(ftpPort);
        factory.setUsername(ftpUser);
        factory.setPassword(password);

        //factory.setClientMode(PASSIVE_LOCAL_DATA_CONNECTION_MODE );
        System.out.println("host " + ftpHost);
        System.out.println("port " + ftpPort);
        System.out.println("user " + ftpUser);
        System.out.println("password " + password);
        return new CachingSessionFactory<FTPFile> (factory);
    }

    @Bean
    @ServiceActivator(inputChannel = "toftpChannel")
    public MessageHandler handler() {
        FtpMessageHandler handler = new FtpMessageHandler(ftpSessionFactory());
        handler.setAutoCreateDirectory(true);
        ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

        handler.setRemoteDirectoryExpression(EXPRESSION_PARSER.parseExpression("headers['file_remoteDirectory']"));

        handler.setFileNameGenerator(defaultFileNameGenerator());

        return handler;
    }
    @Bean
    public DefaultFileNameGenerator defaultFileNameGenerator() {
        DefaultFileNameGenerator defaultFileNameGenerator = new DefaultFileNameGenerator();
        defaultFileNameGenerator.setHeaderName(FileHeaders.REMOTE_DIRECTORY);

        defaultFileNameGenerator.setExpression("payload.name");

        return defaultFileNameGenerator;
    }
    @MessagingGateway
    public interface UploadGateway {

        @Gateway(requestChannel = "toftpChannel")

        void sendToFtp(File file, @Header(FileHeaders.REMOTE_DIRECTORY) String dynamicHeader);


    }
}