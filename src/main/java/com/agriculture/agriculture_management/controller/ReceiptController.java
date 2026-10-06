package com.agriculture.agriculture_management.controller;

import org.springframework.web.bind.annotation.RestController;
import com.agriculture.agriculture_management.service.ReceiptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RequestMapping("/receipts")
@RestController
public class ReceiptController {
	
	 private final ReceiptService receiptService;

	    public ReceiptController(ReceiptService receiptService) {
	        this.receiptService = receiptService;
	    }
	    
	    @GetMapping("/{orderId}")
	    public byte[] downloadReceipt(@PathVariable Long orderId) throws Exception {

	        return receiptService.generateReceipt(orderId);
	    }

}
