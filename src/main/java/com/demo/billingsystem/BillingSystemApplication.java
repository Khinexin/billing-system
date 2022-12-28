package com.demo.billingsystem;

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.billingsystem.model.User;
import com.demo.billingsystem.model.UserRole;
import com.demo.billingsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class BillingSystemApplication implements CommandLineRunner {

	private final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BillingSystemApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {

		if (userService.countAppUser() == 0) {

			// admin
			userService.signup(User.builder().username("admin").password("admin")
					.userRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_ADMIN))).build());

			// client
			userService.signup(User.builder().username("client").password("client")
					.userRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_CLIENT))).build());
			
			
			//
			userService.signup(User.builder().username("Citizen Pay User").password("123456")
					.userRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_CLIENT))).build());

		}

	}
}
