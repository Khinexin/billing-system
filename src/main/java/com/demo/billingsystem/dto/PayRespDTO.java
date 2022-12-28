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
public class PayRespDTO {

//	"status_message": "Transaction is successful!",
//	“transaction_id”: 1,
//	“amount”: 1000,
//	“transaction_date”: “20220929112851”,
//	“phone_number”: “959xxxx”

	@ApiModelProperty(position = 0)
	private String status_message;
	@ApiModelProperty(position = 1)
	private String transaction_id;
	@ApiModelProperty(position = 3)
	private String amount;
	@ApiModelProperty(position = 4)
	private String transaction_date;
	@ApiModelProperty(position = 5)
	private String phone_number;

}
