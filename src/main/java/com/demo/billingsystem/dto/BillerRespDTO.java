package com.demo.billingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillerRespDTO {

	private String status_message;
	
	private String date_time;
	private String bill_id;
	private String name;
	private String description;
	
}
