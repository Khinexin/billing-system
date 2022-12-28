package com.demo.billingsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddBillerResponseDTO {

//	"status_message": "Bill Top up is successfully saved in the system.",
//	"date_time": “20220929112851”,
//	"bill_id": 1,
//	"name": "Top up",
//	“description”: “”
	
	@ApiModelProperty(position = 0)
	private String status_message;
	
	@ApiModelProperty(position = 1)
	private String date_time;
	@ApiModelProperty(position = 2)
	private String bill_id;
	@ApiModelProperty(position = 3)
	private String name;
	@ApiModelProperty(position = 4)
	private String description;
	
}
