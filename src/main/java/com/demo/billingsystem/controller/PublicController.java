package com.demo.billingsystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {
	
	@GetMapping("/test")
	public String testNoAnnotation(HttpServletRequest request) throws InterruptedException {
		return "Test success ... ";
	}
}
