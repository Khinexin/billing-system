package com.demo.billingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity(name = "billers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Billers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String description;

	@Column(name = "date_time")
	private String dateTime;

}
