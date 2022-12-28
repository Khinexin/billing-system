package com.demo.billingsystem.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.demo.billingsystem.dto.BillerDTO;
import com.demo.billingsystem.dto.BillerListResponseDTO;
import com.demo.billingsystem.dto.BillerListDTO;
import com.demo.billingsystem.model.Billers;
import com.demo.billingsystem.repository.BillersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillersService {

	private final BillersRepository billersRepository;

	public Billers saveBiller(Billers biller) {

		Date today = Calendar.getInstance().getTime();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String dateString = formatter.format(today);

		biller.setDateTime(dateString.replaceAll("-", ""));
		System.out.println(biller.toString());
		return billersRepository.saveAndFlush(biller);
	}

	public BillerListResponseDTO allBillers() {

		List<BillerListDTO> dtbDto = new ArrayList<BillerListDTO>();

		List<Billers> allBillersInDB = billersRepository.findAll();

		Set<String> dateTimeSet = new HashSet<String>();
		for (Billers billers : allBillersInDB) {
			dateTimeSet.add(billers.getDateTime());
		}

		if (dateTimeSet.size() > 0) {
			Map<String, List<BillerDTO>> billerMap = new HashMap<String, List<BillerDTO>>();

			for (String dtStr : dateTimeSet) {

				List<BillerDTO> billerList = new ArrayList<BillerDTO>();

				for (Billers biller : allBillersInDB) {
					if (dtStr.equals(biller.getDateTime())) {
						billerList.add(BillerDTO.builder().bill_id(String.valueOf(biller.getId()))
								.name(biller.getName()).description(biller.getDescription()).build());
					}
				}

				billerMap.put(dtStr, billerList);

			}

			List<String> dateTimeList = new ArrayList<String>(dateTimeSet);

			for (String dateTimeStr : dateTimeList) {

				BillerListDTO dateTimeBillerDTO = BillerListDTO.builder().date_time(dateTimeStr)
						.billers(billerMap.get(dateTimeStr)).build();

				dtbDto.add(dateTimeBillerDTO);

			}
		}

		BillerListResponseDTO dto = BillerListResponseDTO.builder().status_message("Transaction is successful!")
				.date_time_billers(dtbDto).build();

		return dto;
	}

	public BillerListResponseDTO billersById(String biller_id) {

		Billers biller = billersRepository.findById(Integer.valueOf(biller_id)).orElse(new Billers());

		BillerListResponseDTO dto = BillerListResponseDTO.builder().status_message("Transaction is successful!")
				.date_time_billers(Arrays.asList(BillerListDTO.builder().date_time(biller.getDateTime())
						.billers(Arrays.asList(BillerDTO.builder().bill_id(String.valueOf(biller.getId()))
								.name(biller.getName()).description(biller.getDescription()).build()))
						.build()))
				.build();

		return dto;

	}

}
