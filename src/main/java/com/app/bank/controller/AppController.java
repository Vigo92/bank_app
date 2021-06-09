package com.app.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bank.model.request.AccountDepositRequest;
import com.app.bank.model.request.AccountInfoRequest;
import com.app.bank.model.request.AccountStatementRequest;
import com.app.bank.model.request.AccountWithdrawalRequest;
import com.app.bank.model.request.CreateAccountRequest;
import com.app.bank.model.response.AccountInfoResponse;
import com.app.bank.model.response.AccountResponse;
import com.app.bank.model.response.AccountStatementResponse;
import com.app.bank.service.AccountService;

@RestController
@RequestMapping("api/v1")
public class AppController {

	@Autowired
	AccountService accountService;

	@PostMapping(path = "account_info", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AccountInfoResponse> getAccountInfo(
			final @RequestBody AccountInfoRequest accountInfoRequest) {

		AccountInfoResponse accountInfoResponse = accountService.getAccountInformation(accountInfoRequest);

		return new ResponseEntity<AccountInfoResponse>(accountInfoResponse, HttpStatus.OK);
	}

	@PostMapping(path = "account_statement", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AccountStatementResponse> getAccountStatement(
			final @RequestBody AccountStatementRequest accountStatementRequest) {

		AccountStatementResponse accountStatementResponse = accountService.getAccountStatement(accountStatementRequest);

		return new ResponseEntity<AccountStatementResponse>(accountStatementResponse, HttpStatus.OK);
	}

	@PostMapping(path = "create_account", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AccountResponse> createAccount(
			final @RequestBody CreateAccountRequest createAccountRequest) {

		AccountResponse createAccountResponse = accountService.createAccount(createAccountRequest);

		return new ResponseEntity<AccountResponse>(createAccountResponse, HttpStatus.OK);
	}

	@PostMapping(path = "withdrawal", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AccountResponse> withdrawFromAccount(
			final @RequestBody AccountWithdrawalRequest accountWithdrawalRequest) {

		AccountResponse accountWithdrawalResponse = accountService.withdrawFromAccount(accountWithdrawalRequest);

		return new ResponseEntity<AccountResponse>(accountWithdrawalResponse, HttpStatus.OK);
	}

	@PostMapping(path = "deposit", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AccountResponse> depositIntoAccount(
			final @RequestBody AccountDepositRequest accountDepositRequest) {

		AccountResponse accountDepositResponse = accountService.depositIntoAccount(accountDepositRequest);

		return new ResponseEntity<AccountResponse>(accountDepositResponse, HttpStatus.OK);
	}

}
