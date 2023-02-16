package com.demo.billingsystem;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.billingsystem.controller.HomeController;
import com.demo.billingsystem.dto.LogInRespDTO;
import com.demo.billingsystem.model.User;
import com.demo.billingsystem.security.JwtTokenFilter;
import com.demo.billingsystem.security.JwtTokenProvider;
import com.demo.billingsystem.security.WebSecurityConfig;
import com.demo.billingsystem.service.BillersService;
import com.demo.billingsystem.service.TransactionsService;
import com.demo.billingsystem.service.UserService;

//@SpringBootTest
//@AutoConfigureMockMvc
//@Import(HomeController.class)

@WithMockUser
@WebMvcTest(controllers = { HomeController.class })
@ContextConfiguration(classes = { HomeController.class, WebSecurityConfig.class, JwtTokenProvider.class,
		JwtTokenFilter.class })
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	@MockBean
	private BillersService billersService;
	@MockBean
	private TransactionsService transactionsService;
	@MockBean
	private ModelMapper modelMapper;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private JwtTokenProvider jwtTokenProvider;
	@MockBean
	private AuthenticationManager authenticationManager;

//	@Test
//	public void greetin_withoutValidJwtToken() throws Exception {
//		when(userService.greet()).thenReturn("Hello!");
//		mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().string(containsString("")));
//	}
//	@Test
//	@DisplayName("greeting - GET /greeting")
//	public void testGreeting() throws Exception {
//		when(userService.greet()).thenReturn("Hello, Mock");
//		mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().string(containsString("Hello, Mock")));
//	}

	@Test
	@DisplayName("signup - POST /register")
	public void testSignUp() throws Exception {
		User user = User.builder().username("admin").password("admin").build();
		mockMvc.perform(MockMvcRequestBuilders.get("/register")
				.header("authorization", "Bearer " + userService.signup(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		verify(userService).signup(user);
	}

	@Test
	@DisplayName("login - POST /login")
	public void testLogin() throws Exception {

		when(userService.signin("admin", "admin")).thenReturn("THIS_IS_TEMP_JWT_TOKEN");

		mockMvc.perform(MockMvcRequestBuilders.get("/login"))
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

	}

	@Test
	@DisplayName("addBiller - POST /add")
	public void testAddBiller() throws Exception {

	}

	@Test
	@DisplayName("findAllBillers - GET /list")
	public void testFindAllBillers() throws Exception {
		when(userService.signin("admin", "admin")).thenReturn("this_is_JWT_token");

		mockMvc.perform(MockMvcRequestBuilders.get("/list").header("authorization", "Bearer " + "this_is_JWT_token")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
		verify(billersService).allBillers();
	}

	@Test
	@DisplayName("payBiller - POST /pay")
	public void testPayBiller() throws Exception {

	}

	@Test
	@DisplayName("findTransactionById - POST /transaction")
	public void testFindTransactionById() throws Exception {

	}

	@Test
	@DisplayName("findTransactionList - POST /transaction/list")
	public void testFindTransactionList() throws Exception {

	}

}
