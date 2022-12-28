package com.demo.billingsystem.controller;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.billingsystem.dto.BillerListRespDTO;
import com.demo.billingsystem.dto.BillerReqDTO;
import com.demo.billingsystem.dto.BillerRespDTO;
import com.demo.billingsystem.dto.LogInRespDTO;
import com.demo.billingsystem.dto.PayReqDTO;
import com.demo.billingsystem.dto.PayRespDTO;
import com.demo.billingsystem.dto.TransactionsRespDTO;
import com.demo.billingsystem.dto.UserDataDTO;
import com.demo.billingsystem.model.Billers;
import com.demo.billingsystem.model.Transactions;
import com.demo.billingsystem.model.User;
import com.demo.billingsystem.service.BillersService;
import com.demo.billingsystem.service.TransactionsService;
import com.demo.billingsystem.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {

	private final UserService userService;
	private final BillersService billersService;
	private final TransactionsService transactionsService;
	private final ModelMapper modelMapper;

	@PostMapping("/register")
	public String signup(@RequestBody UserDataDTO user) {
		log.info("Register New User.");
		return userService.signup(modelMapper.map(user, User.class));
	}

	// login - endpoint
	@PostMapping("/login")
	public LogInRespDTO login(@RequestParam(required = true) String username,
			@RequestParam(required = true) String password) {
		log.info("Sign In User.");
		return LogInRespDTO.builder().status_message("Login is successful")
				.access_token(userService.signin(username, password)).build();
	}

	// add - endpoint
	@PostMapping("/add")
	public BillerRespDTO addBiller(@RequestBody BillerReqDTO billerReqDTO) {

		log.info("Add Biller.");
		Billers biller = billersService.saveBiller(modelMapper.map(billerReqDTO, Billers.class));
		return BillerRespDTO.builder().status_message("Bill Top up is successfully saved in the system.")
				.date_time(biller.getDateTime()).bill_id(String.valueOf(biller.getId())).name(biller.getName())
				.description(biller.getDescription()).build();
	}

	// list - endpoint
	@GetMapping("/list")
	public BillerListRespDTO findAllBillers(@RequestParam(required = false) String id) {

		if (Objects.isNull(id)) {
			return billersService.allBillers();
		} else {
			return billersService.billersById(id);
		}

	}

	// pay - endpoint
	@PostMapping("/pay")
	public PayRespDTO addBiller(@RequestBody PayReqDTO payReqDTO) {

		log.info("Add Pay.");
		Transactions trs = transactionsService.savePay(
				Transactions.builder().apiCaller(payReqDTO.getApi_caller()).phoneNumber(payReqDTO.getPhone_number())
						.referenceNo(payReqDTO.getReference_no()).amount(Long.valueOf(payReqDTO.getAmount()))
						.billers(billersService.findBillerById(Integer.valueOf(payReqDTO.getId()))).build());

		return PayRespDTO.builder().status_message("Transaction is successful!")
				.transaction_id(String.valueOf(trs.getTransition_id())).amount(String.valueOf(trs.getAmount()))
				.transaction_date(trs.getReferenceNo()).phone_number(trs.getPhoneNumber()).build();
	}

	// transaction - endpoint
	@GetMapping("/transaction")
	public TransactionsRespDTO findTransactionById(@RequestParam(required = true) String id) {
		return transactionsService.findTransactionsResponseDTOById(Integer.parseInt(id));

	}

	@GetMapping("/transaction/list")
	public List<Transactions> findTransactionById() {
		return transactionsService.findAllTransactions();

	}

}
