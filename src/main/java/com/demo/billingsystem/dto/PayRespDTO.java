package com.demo.billingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayRespDTO {

	private String status_message;
	
	private String transaction_id;
	private String amount;
	private String transaction_date;
	private String phone_number;

}
