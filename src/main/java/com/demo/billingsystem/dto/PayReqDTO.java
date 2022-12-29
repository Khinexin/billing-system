package com.demo.billingsystem.dto;

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
	
	private String phone_number;
 
}
