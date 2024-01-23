package com.intrum.task.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutDTO {

	@CsvBindByName(column = "Company name")
	private String companyName;

	@CsvBindByName(column = "Company tax number")
	@NotBlank(message = "companyIdentityNumber is mandatory")
	private String companyIdentityNumber;

	@CsvBindByName(column = "Status")
	private String status;

	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "Payment Date")
	@NotNull(message = "paymentDate is mandatory")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date paymentDate;

	@CsvNumber("#0,0#")
	@CsvBindByName(column = "Amount")
	@NotNull(message = "paymentAmount is mandatory")
	private Double paymentAmount;
}
