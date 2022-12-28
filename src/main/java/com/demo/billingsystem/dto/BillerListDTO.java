package com.demo.billingsystem.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillerListDTO {

	@ApiModelProperty(position = 1)
	private String date_time;

	@ApiModelProperty(position = 2)
	private List<BillerDTO> billers;

}
