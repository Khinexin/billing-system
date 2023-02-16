package com.demo.billingsystem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.billingsystem.model.User;
import com.demo.billingsystem.model.UserRole;
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

	@Test
	void testRegister() {
		
		List<UserRole> userRoleList = new ArrayList<UserRole>();
		userRoleList.add(UserRole.ROLE_CLIENT);
		
		User user = User.builder()
				.username("").password("")
				.userRoles(userRoleList)
				.build();
	}

}
