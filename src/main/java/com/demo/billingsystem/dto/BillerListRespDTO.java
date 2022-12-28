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
public class BillerListRespDTO {

	private String status_message;

	private List<BillerListDTO> billerListDTO;

}
