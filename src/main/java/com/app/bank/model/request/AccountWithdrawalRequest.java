package com.app.bank.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountWithdrawalRequest {
	
	private String accountNumber;
	private String accountPassword;
	private Double withdrawnAmount;

}
