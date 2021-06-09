package com.app.bank.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatementDTO {
	private LocalDate transactionDate;
	private String transactionType;
	private String narration;
	private Double amount;
	private Double accountBalance;
}
