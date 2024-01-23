package com.intrum.task.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrum.task.api.dto.PayoutDTO;
import com.intrum.task.data.entities.PayoutEntity;
import com.intrum.task.data.repository.PayoutRepository;

@Service
public class PayoutService {
	
	@Autowired
	private PayoutRepository payoutRepository;
	
	public PayoutEntity savePayout (final PayoutDTO payoutDTO) {
		
		PayoutEntity entity = new PayoutEntity();
		
		entity.setCompanyIdentityNumber(payoutDTO.getCompanyIdentityNumber());
		entity.setCompanyName(payoutDTO.getCompanyName());
		entity.setStatus(payoutDTO.getStatus());
		entity.setPaymentDate(payoutDTO.getPaymentDate());
		entity.setPaymentAmount(payoutDTO.getPaymentAmount());

		payoutRepository.save(entity);
		
		return entity;
	}
}
