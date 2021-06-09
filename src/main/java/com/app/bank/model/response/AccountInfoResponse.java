package com.app.bank.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountInfoResponse extends AccountResponse {

	private Accounts account;

	public AccountInfoResponse(int responseCode, boolean success, String message, Accounts account) {
		super(responseCode, success, message);
		this.account = account;
	}

	public AccountInfoResponse(int responseCode, boolean success, String message) {

		super(responseCode, success, message);
	}
}
