package com.demo.billingsystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillerListDTO {

	private String date_time;

	private List<BillerDTO> billers;

}
