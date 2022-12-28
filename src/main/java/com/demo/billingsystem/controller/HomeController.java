package com.demo.billingsystem.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.billingsystem.dto.AddBillerResponseDTO;
import com.demo.billingsystem.dto.BillerListResponseDTO;
import com.demo.billingsystem.dto.LogInRespDTO;
import com.demo.billingsystem.model.Billers;
import com.demo.billingsystem.service.BillersService;
import com.demo.billingsystem.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.models.Response;
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
	private final ModelMapper modelMapper;

	// login - endpoint
	@PostMapping("/login")
	@ApiOperation(value = "Login user to generate JWT token")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public LogInRespDTO login(@ApiParam("Username") @RequestParam String username,
			@ApiParam("Password") @RequestParam String password) {
		log.info("Sign In User.");
		return LogInRespDTO.builder().status_message("Login is successful")
				.access_token(userService.signin(username, password)).build();
	}

	// add - endpoint
	@PostMapping("/add")
	@ApiOperation(value = "Add Biller", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public AddBillerResponseDTO addBiller(@ApiParam("name") @RequestParam String name,
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

}
