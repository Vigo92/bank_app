package com.app.bank.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accounts {
	

	private String accountName;
	private String accountNumber;
	private double balance;

}
