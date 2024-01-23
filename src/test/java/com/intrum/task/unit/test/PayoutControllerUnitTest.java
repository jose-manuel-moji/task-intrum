package com.intrum.task.unit.test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.intrum.task.api.controller.PayoutController;
import com.intrum.task.api.dto.PayoutDTO;
import com.intrum.task.data.entities.PayoutEntity;
import com.intrum.task.data.service.PayoutService;

@ExtendWith(MockitoExtension.class)
public class PayoutControllerUnitTest {

    @InjectMocks
    private PayoutController controller;
    
    @Mock
    private PayoutService payoutService;

    @BeforeEach
    public void setUp() {
    	Mockito.when(payoutService.savePayout(Mockito.any())).thenReturn(getTestEntity());
    }
    
    @Test
    public void payout_thenStatus200() {
    	PayoutEntity entity = controller.payout(new PayoutDTO());
    	
    	Assert.assertEquals(entity, getTestEntity());
    }
    
    private PayoutEntity getTestEntity () {
    	return new PayoutEntity("test-tax-number-3562662", "text-company-name", "TESTING", null, null);
    }
    
}