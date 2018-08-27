package com.checkOut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import tk.mybatis.spring.annotation.MapperScan;

//@Component
@SpringBootApplication
//@ComponentScan(basePackages="com.checkOut")
@MapperScan(basePackages="com.checkOut.mapper")
public class CheckOutSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckOutSysApplication.class, args);
	}
}
