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
public class BillerListResponseDTO {

	@ApiModelProperty(position = 0)
	private String status_message;

	@ApiModelProperty(position = 1)
	private List<BillerListDTO> date_time_billers;

}
