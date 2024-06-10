package com.skyhorsemanpower.BE_AuctionPost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.TimeZone;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
public class BeAuctionPostApplication {


	public static void main(String[] args) {
		// application 전체 timezone을 UTC로 설정
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(BeAuctionPostApplication.class, args);
	}

}
