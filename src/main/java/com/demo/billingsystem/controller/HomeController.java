package com.demo.billingsystem.controller;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.billingsystem.dto.AddBillerResponseDTO;
import com.demo.billingsystem.dto.BillerListResponseDTO;
import com.demo.billingsystem.dto.LogInRespDTO;
import com.demo.billingsystem.dto.PayRespDTO;
import com.demo.billingsystem.dto.TransactionsResponseDTO;
import com.demo.billingsystem.model.Billers;
import com.demo.billingsystem.model.Transactions;
import com.demo.billingsystem.service.BillersService;
import com.demo.billingsystem.service.TransactionsService;
import com.demo.billingsystem.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "home", description = "Operations about JWT Token and Billing System")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
//	POST	login	Login to get JWT access token.
//	POST	Add	Adding bill in the system
//	GET	list	Get all list of bills in the system.
//	POST	pay	Pay the bills
//	GET	transaction	Get the transaction history

	private final UserService userService;
	private final BillersService billersService;
	private final TransactionsService transactionsService;
	private final ModelMapper modelMapper;

	// login - endpoint
	@PostMapping("/login")
	@ApiOperation(value = "Login user to generate JWT token")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public LogInRespDTO login(@ApiParam("Username") @RequestParam(required = true) String username,
			@ApiParam("Password") @RequestParam(required = true) String password) {
		log.info("Sign In User.");
		return LogInRespDTO.builder().status_message("Login is successful")
				.access_token(userService.signin(username, password)).build();
	}

	// add - endpoint
	@PostMapping("/add")
	@ApiOperation(value = "Add Biller", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public AddBillerResponseDTO addBiller(@ApiParam("name") @RequestParam(required = true) String name,
			@ApiParam("description") @RequestParam(required = false) String description) {

		log.info("Add Biller.");
		Billers biller = billersService.saveBiller(Billers.builder().name(name).description(description).build());

		return AddBillerResponseDTO.builder().status_message("Bill Top up is successfully saved in the system.")
				.date_time(biller.getDateTime()).bill_id(String.valueOf(biller.getId())).name(biller.getName())
				.description(biller.getDescription()).build();
	}

	// list - endpoint
	@GetMapping("/list")
	@ApiOperation(value = "Biller List", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public BillerListResponseDTO findAllBillers(@ApiParam("id") @RequestParam(required = false) String id) {

		if (Objects.isNull(id)) {
			return billersService.allBillers();
		} else {
			return billersService.billersById(id);
		}

	}

	// pay - endpoint
	@PostMapping("/pay")
	@ApiOperation(value = "Add Pay", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public PayRespDTO addBiller(@ApiParam("api_caller") @RequestParam(required = true) String api_caller,
			@ApiParam("id") @RequestParam(required = true) String id,
			@ApiParam("amount") @RequestParam(required = true) String amount,
			@ApiParam("reference_no") @RequestParam(required = true) String reference_no,
			@ApiParam("phone_number") @RequestParam(required = true) String phone_number) {

		log.info("Add Pay.");
		Transactions trs = transactionsService.savePay(Transactions.builder().apiCaller(api_caller)
				.phoneNumber(phone_number).referenceNo(reference_no).amount(Long.valueOf(amount))
				.billers(billersService.findBillerById(Integer.valueOf(id))).build());

		return PayRespDTO.builder().status_message("Transaction is successful!")
				.transaction_id(String.valueOf(trs.getTransition_id())).amount(String.valueOf(trs.getAmount()))
				.transaction_date(trs.getReferenceNo()).phone_number(trs.getPhoneNumber()).build();
	}

	// transaction - endpoint

	@GetMapping("/transaction")
	@ApiOperation(value = "Transaction List", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public TransactionsResponseDTO findTransactionById(@ApiParam("id") @RequestParam(required = true) String id) {
		return transactionsService.findTransactionsResponseDTOById(Integer.parseInt(id));

	}

//	@GetMapping("/transaction")
//	@ApiOperation(value = "Transaction List", authorizations = { @Authorization(value = "apiKey") })
//	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
//			@ApiResponse(code = 403, message = "Access denied"),
//			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
//	public List<TransactionsResponseDTO> findAllTransactions(@ApiParam("id") @RequestParam(required = true) String id) {
//		return transactionsService.findAllTransactionsResponseDTOList(id);
//
//	}
}
