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
public class TransactionsResponseDTO {

	@ApiModelProperty(position = 0)
	private String api_caller;
	@ApiModelProperty(position = 1)
	private String id;
	@ApiModelProperty(position = 3)
	private String amount;
	@ApiModelProperty(position = 4)
	private String reference_no;
	@ApiModelProperty(position = 5)
	private String phone_number;

}
