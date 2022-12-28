package com.demo.billingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transition_id;

	@Column(name = "api_caller")
	private String apiCaller;

	@Column(name = "reference_no")
	private String referenceNo;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Min(value = 0, message = "Age should not be less than 0")
    @Max(value = 100001, message = "Age should not be greater than 100000")
	private Long amount;

	@ManyToOne
	@JoinColumn(name = "billers_id", nullable = true)
	private Billers billers;

}
