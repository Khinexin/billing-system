package com.demo.billingsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.demo.billingsystem.dto.TransactionsResponseDTO;
import com.demo.billingsystem.model.Transactions;
import com.demo.billingsystem.repository.TransactionsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionsService {

	private final TransactionsRepository transactionsRepository;

	public Transactions savePay(Transactions transactions) {
		return transactionsRepository.saveAndFlush(transactions);
	}

	public List<Transactions> findAllTransactions() {
		return transactionsRepository.findAll();
	}

	public List<TransactionsResponseDTO> findAllTransactionsResponseDTOList() {
		List<TransactionsResponseDTO> trResList = new ArrayList<TransactionsResponseDTO>();
		for (Transactions tr : findAllTransactions()) {
			trResList.add(TransactionsResponseDTO.builder().api_caller(tr.getApiCaller())
					.id(String.valueOf(tr.getTransition_id())).amount(String.valueOf(tr.getAmount()))
					.reference_no(tr.getReferenceNo()).phone_number(tr.getPhoneNumber()).build());
		}
		return trResList;
	}

	public TransactionsResponseDTO findTransactionsResponseDTOById(int id) {
		Transactions tr = transactionsRepository.findById(id).orElse(null);

		if (!Objects.isNull(tr)) {
			return TransactionsResponseDTO.builder().api_caller(tr.getApiCaller())
					.id(String.valueOf(tr.getTransition_id())).amount(String.valueOf(tr.getAmount()))
					.reference_no(tr.getReferenceNo()).phone_number(tr.getPhoneNumber()).build();
		}
		return TransactionsResponseDTO.builder().build();
	}

}
