package com.lt.rttest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource(value = "classpath:project.properties")
@ConfigurationProperties()
@Data
public class ProjectConfig {

	String videouri;
	String proxyhost;
	private int proxyport;
	private String user;
	private String password;

}
