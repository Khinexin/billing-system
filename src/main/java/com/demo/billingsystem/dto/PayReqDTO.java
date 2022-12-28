package com.demo.billingsystem.dto;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayReqDTO {

	private String api_caller;
	
	private String id;
	
	private String amount;
	
	private String reference_no;
	
//	@Pattern(regexp = "[0-9]+", message = "Phone number can have numbers only.")
	@Pattern(regexp = "[9][5][9]([,\\\\s])?[0-9]+", message = "Phone number can have numbers only and must start with 959... ")
	private String phone_number;
 
}
