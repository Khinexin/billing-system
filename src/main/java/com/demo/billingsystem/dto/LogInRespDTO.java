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
public class LogInRespDTO {

	@ApiModelProperty(position = 0)
	private String status_message;
	@ApiModelProperty(position = 1)
	private String access_token;

}
