package com.demo.billingsystem;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.billingsystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class BillingSystemApplicationTest {

	private final UserRepository userRepository;

	@Test
	void test() {
		assertThat(userRepository).isNotNull();
	}



}
