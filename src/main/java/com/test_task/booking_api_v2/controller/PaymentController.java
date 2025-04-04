package com.test_task.booking_api_v2.controller;

import com.test_task.booking_api_v2.dto.request.PaymentRequestDto;
import com.test_task.booking_api_v2.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping
  @Operation(summary = "Create a payment transaction")
  public void createTransaction(@RequestBody PaymentRequestDto dto){
    this.paymentService.createTransaction(dto);
  }
}
