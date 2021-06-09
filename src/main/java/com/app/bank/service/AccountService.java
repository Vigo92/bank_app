package com.app.bank.service;

import com.app.bank.model.request.AccountDepositRequest;
import com.app.bank.model.request.AccountInfoRequest;
import com.app.bank.model.request.AccountStatementRequest;
import com.app.bank.model.request.AccountWithdrawalRequest;
import com.app.bank.model.request.CreateAccountRequest;
import com.app.bank.model.response.AccountInfoResponse;
import com.app.bank.model.response.AccountResponse;
import com.app.bank.model.response.AccountStatementResponse;

public interface AccountService {
	
	
	AccountResponse createAccount(CreateAccountRequest createAccountRequest);

	 AccountResponse withdrawFromAccount(AccountWithdrawalRequest accountWithdrawalRequest);
	
	 AccountResponse depositIntoAccount(AccountDepositRequest accountDepositRequest);
	
	AccountInfoResponse getAccountInformation(AccountInfoRequest accountInfoRequest);
	
	AccountStatementResponse getAccountStatement(AccountStatementRequest accountStatementRequest);
}
