package com.intrum.task.data.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayoutEntity {
	
	@Id
	private String companyIdentityNumber;

	private String companyName;

	private String status;

	private Date paymentDate;

	private Double paymentAmount;
}
