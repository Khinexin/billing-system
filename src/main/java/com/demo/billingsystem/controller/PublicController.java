package com.demo.billingsystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@Api(tags = "public", description = "Test Server")
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {
	
	@GetMapping("/test")
	public String testNoAnnotation(HttpServletRequest request) throws InterruptedException {
		return "Test success ... ";
	}
}
