package com.intrum.task.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intrum.task.api.dto.PayoutDTO;
import com.intrum.task.data.entities.PayoutEntity;
import com.intrum.task.data.service.PayoutService;

import jakarta.validation.Valid;

@RequestMapping("/payout")
@RestController
public class PayoutController {
	
	@Autowired
	private PayoutService payoutService;
	
	@GetMapping
	public String status () {
		return "Application Running!!";
	}

	@PostMapping
	public PayoutEntity payout (final @Valid @RequestBody PayoutDTO request) {
		return this.payoutService.savePayout(request);
	}
}
