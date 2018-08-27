package com.checkOut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages="com.checkOut.businessFunction.mapper")
public class CheckOutSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckOutSysApplication.class, args);
	}
}
