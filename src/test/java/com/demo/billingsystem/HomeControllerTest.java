package com.demo.billingsystem;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.billingsystem.controller.HomeController;
import com.demo.billingsystem.dto.BillerDTO;
import com.demo.billingsystem.dto.BillerListDTO;
import com.demo.billingsystem.dto.BillerListRespDTO;
import com.demo.billingsystem.dto.BillerRespDTO;
import com.demo.billingsystem.dto.PayReqDTO;
import com.demo.billingsystem.dto.PayRespDTO;
import com.demo.billingsystem.dto.TransactionsRespDTO;
import com.demo.billingsystem.model.Billers;
import com.demo.billingsystem.model.Transactions;
import com.demo.billingsystem.model.User;
import com.demo.billingsystem.security.JwtTokenFilter;
import com.demo.billingsystem.security.JwtTokenProvider;
import com.demo.billingsystem.security.WebSecurityConfig;
import com.demo.billingsystem.service.BillersService;
import com.demo.billingsystem.service.TransactionsService;
import com.demo.billingsystem.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = { HomeController.class })
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")

@WithMockUser
@WebMvcTest(controllers = { HomeController.class }, excludeAutoConfiguration = SecurityAutoConfiguration.class)
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

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenSignUp_thenCreateUser_thenReturnToken() throws Exception {

		User user = User.builder().username("apple").password("apple").build();

		mockMvc.perform(get("/register") //
				.contentType(MediaType.APPLICATION_JSON)//
				.header("authorization", "Bearer " + userService.signup(user)))//
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

		verify(userService).signup(user);
		reset(userService);
	}

	@Test
	public void whenLogin_thenReturnToken() throws Exception {
		when(userService.signin("admin", "admin")).thenReturn("THIS_IS_TEMP_JWT_TOKEN");
		mockMvc.perform(get("/login")//
				.contentType(MediaType.APPLICATION_JSON))//
				.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
		reset(userService);
	}

	@Test
	public void whenAddBiller_thenReturnBillerRespDTO() throws Exception {
		Billers biller = Billers.builder().id(1).name("Biller 1").description("Biller Description")
				.dateTime("20221228214133").build();
		given(billersService.saveBiller(Mockito.any())).willReturn(biller);

		mockMvc.perform(post("/add")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(toJson(biller)))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$.name", is("Biller 1")))//
				.andExpect(jsonPath("$.description", is("Biller Description")));
		verify(billersService, VerificationModeFactory.times(1)).saveBiller(Mockito.any());
		reset(billersService);
	}

	@Test
	public void whenFindBillers_thenReturnBillers() throws Exception {

		when(userService.signin("admin", "admin")).thenReturn("this_is_JWT_token");

		Billers biller1 = Billers.builder().name("apple").description("desc 1").dateTime("20221228214122")
				.transactionsList(Arrays.asList(Transactions.builder().build())).build();
		Billers biller2 = Billers.builder().name("orange").description("desc 2 ").dateTime("20221228214122")
				.transactionsList(Arrays.asList(Transactions.builder().build())).build();
		Billers biller3 = Billers.builder().name("cherry").description("desc 3").dateTime("20221228214122")
				.transactionsList(Arrays.asList(Transactions.builder().build())).build();

		List<BillerListDTO> billerListDTOList = Arrays.asList(BillerListDTO.builder().date_time("20221228214122")
				.billers(mapList(Arrays.asList(biller1, biller2, biller3), BillerDTO.class)).build());

		BillerListRespDTO responseDto = BillerListRespDTO.builder()//
				.status_message("Transaction is successful!")//
				.billerListDTO(billerListDTOList).build();

		given(billersService.allBillers()).willReturn(responseDto);

		mockMvc.perform(get("/list")//
				.contentType(MediaType.APPLICATION_JSON)//
				.header("authorization", "Bearer " + "THIS_IS_TEMP_JWT_TOKEN"))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.status_message", is("Transaction is successful!")));

		verify(billersService, VerificationModeFactory.times(1)).allBillers();
		reset(billersService);

	}

	@Test
	public void whenAddPay_thenReturnPayRespDTO() throws Exception {

		when(userService.signin("admin", "admin")).thenReturn("this_is_JWT_token");

		Billers biller = Billers.builder().id(1).description("description").dateTime("dateTime").build();

		Transactions transactions = Transactions.builder().transition_id(1).apiCaller("test_caller").referenceNo("111")
				.phoneNumber("111").amount(1L).billers(biller).build();

		given(transactionsService.savePay(Mockito.any())).willReturn(transactions);

		PayRespDTO response = PayRespDTO.builder().status_message("Transaction is successful!")//
				.transaction_id(String.valueOf(transactions.getTransition_id()))//
				.amount(String.valueOf(transactions.getAmount()))//
				.transaction_date(transactions.getReferenceNo())//
				.phone_number(transactions.getPhoneNumber()).build();
		mockMvc.perform(post("/pay")//
				.header("authorization", "Bearer " + "THIS_IS_TEMP_JWT_TOKEN")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(toJson(transactions)))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$.amount", is(response.getAmount())))//
				.andExpect(jsonPath("$.transaction_date", is(response.getTransaction_date())))//
				.andExpect(jsonPath("$.status_message", is("Transaction is successful!")));
		verify(transactionsService, VerificationModeFactory.times(1)).savePay(Mockito.any());
		reset(transactionsService);

	}

	@Test
	public void whenFindTransactionById_thenReturnTransactionsRespDTO() throws Exception {

//		TransactionsRespDTO response = TransactionsRespDTO.builder().api_caller("api_caller").id("1").amount("amount")
//				.reference_no("reference_no").phone_number("phone_number").build();
//
//		given(transactionsService.findTransactionsResponseDTOById(Mockito.any())).willReturn(response);
//		mockMvc.perform(get("/transaction")//
//				.contentType(MediaType.APPLICATION_JSON))//
//				.andExpect(status().isOk())//
//				.andExpect(jsonPath("$.api_caller", is("")))//
//				.andExpect(jsonPath("$.reference_no", is("")))//
//				.andExpect(jsonPath("$.phone_number", is("")));
//		reset(transactionsService);

	}

	@Test
	public void whenFindTransactionList_thenReturnTransactions() throws Exception {

		Transactions tr1 = Transactions.builder().transition_id(1).apiCaller("apiCaller 1").referenceNo("referenceNo 1")
				.phoneNumber("phoneNumber 1").amount(100L).build();
		Transactions tr2 = Transactions.builder().transition_id(2).apiCaller("apiCaller 2").referenceNo("referenceNo 2")
				.phoneNumber("phoneNumber 2").amount(200L).build();

		List<Transactions> response = Arrays.asList(tr1, tr2);

		given(transactionsService.findAllTransactions()).willReturn(response);

		mockMvc.perform(get("/transaction/list")//
				.contentType(MediaType.APPLICATION_JSON)//
				.header("authorization", "Bearer " + "THIS_IS_TEMP_JWT_TOKEN"))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.length()", is(2)));

		verify(transactionsService, VerificationModeFactory.times(1)).findAllTransactions();
		reset(transactionsService);

	}

	/*
	 * @Test public void greetin_withoutValidJwtToken() throws Exception {
	 * when(userService.greet()).thenReturn("Hello!");
	 * mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
	 * .andExpect(content().string(containsString(""))); }
	 * 
	 * @Test @DisplayName("greeting - GET /greeting") public void testGreeting()
	 * throws Exception { when(userService.greet()).thenReturn("Hello, Mock");
	 * mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
	 * .andExpect(content().string(containsString("Hello, Mock"))); }
	 */

	<S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
	}

	byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
