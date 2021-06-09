package com.app.bank.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.app.bank.model.dto.Account;
import com.app.bank.model.dto.AccountStatementDTO;
import com.app.bank.model.request.AccountDepositRequest;
import com.app.bank.model.request.AccountInfoRequest;
import com.app.bank.model.request.AccountStatementRequest;
import com.app.bank.model.request.AccountWithdrawalRequest;
import com.app.bank.model.request.CreateAccountRequest;
import com.app.bank.model.response.AccountInfoResponse;
import com.app.bank.model.response.AccountResponse;
import com.app.bank.model.response.AccountStatementResponse;
import com.app.bank.model.response.Accounts;


@Service
public class AccountServiceImpl implements AccountService {

	private Map<String, Account> account;
	private Map<String, List<AccountStatementDTO>> accountStatement;
	

	{
		account = new HashMap<>();
		accountStatement = new HashMap<>();
	}

	@Override
	public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {

		String accountName = createAccountRequest.getAccountName();
		String password = createAccountRequest.getAccountPassword();
		String accountNumber = generateAccountNumber();
		Account accountCreationDTO = Account.builder().accountName(accountName).accountNumber(accountNumber)
				.password(password).build();
		account.put(accountNumber, accountCreationDTO);
		String message = "Account Successfuly Created, account number - "+ accountNumber;

		AccountResponse createAccountResponse = AccountResponse.builder().responseCode(200).success(true)
				.message(message).build();
		return createAccountResponse;
	}

	@Override
	public AccountResponse withdrawFromAccount(AccountWithdrawalRequest accountWithdrawalRequest) {
		String accountNumber = accountWithdrawalRequest.getAccountNumber();

		boolean accountExists = checkAccountExists(accountNumber);

		if (!accountExists) {

			AccountResponse response = AccountResponse.builder().responseCode(400).success(false)
					.message("No account found with the specified number " + accountNumber).build();
			return response;
		}
		String accountPassword = accountWithdrawalRequest.getAccountPassword();
		boolean isValidPassword = isValidPassword(accountPassword, accountNumber);
		if (!isValidPassword) {

			AccountResponse response = AccountResponse.builder().responseCode(403).success(false)
					.message("Invalid Password!").build();
			return response;
		}
		Double amountToWithdraw = accountWithdrawalRequest.getWithdrawnAmount();
		Account accounts = account.get(accountNumber);
		String message = null;
		Double availableBalance = accounts.getBalance();

		Double balance = availableBalance - amountToWithdraw;

		if (balance < 500 && amountToWithdraw < 1) {
			message = "Available balance must be above #500 and withdrawal amount should be above #1";
			AccountResponse response = AccountResponse.builder().responseCode(400).success(false).message(message)
					.build();
			return response;
		}

		accounts.setBalance(balance);
		account.replace(accountNumber, accounts);
		message = "You have successfuly withdrawn " + amountToWithdraw + " from your account";
		

		AccountStatementDTO accountStatementDTO = AccountStatementDTO
				.builder().accountBalance(balance).transactionType("Withdrawal").narration("")
				.amount(amountToWithdraw).transactionDate(LocalDate.now()).build();
		

		List<AccountStatementDTO> accountStatementDTOs = new ArrayList<>();
		accountStatementDTOs.add(accountStatementDTO);
		if(!(accountStatement.containsKey(accountNumber))) {
		accountStatement.put(accountNumber, accountStatementDTOs);
		}
		else {
		accountStatement.get(accountNumber).add(accountStatementDTO);	
		}
		AccountResponse response = AccountResponse.builder().responseCode(200).success(true).message(message).build();

		return response;
	}

	@Override
	public AccountResponse depositIntoAccount(AccountDepositRequest accountDepositRequest) {

		String accountNumber = accountDepositRequest.getAccountNumber();
		boolean accountExists = checkAccountExists(accountNumber);

		if (!accountExists) {

			AccountResponse response = AccountResponse.builder().responseCode(400).success(false)
					.message("No account found with the specified number " + accountNumber).build();
			return response;
		}
		
		Double amount = accountDepositRequest.getAmount();
		String message = null;
		if (amount < 1 && amount > 1000000) {
			message = "Deposit amount should be greater than #1 and not more than #1000000";
			AccountResponse response = AccountResponse.builder().message(message).responseCode(400).success(false)
					.build();
			return response;
		}
		Account accounts = account.get(accountNumber);
		Double balance = accounts.getBalance();

		Double newBalance = balance + amount;
		accounts.setBalance(newBalance);
		
		AccountStatementDTO accountStatementDTO = AccountStatementDTO
				.builder().accountBalance(newBalance).transactionType("Deposit").narration("")
				.amount(amount).transactionDate(LocalDate.now()).build();
		
		List<AccountStatementDTO> accountStatementDTOs = new ArrayList<>();
		accountStatementDTOs.add(accountStatementDTO);
		if(!(accountStatement.containsKey(accountNumber))) {
		accountStatement.put(accountNumber, accountStatementDTOs);
		}
		else {
		accountStatement.get(accountNumber).add(accountStatementDTO);
		}
		message = "Amount Deposit is successful";
		AccountResponse response = AccountResponse.builder().message(message).responseCode(200).success(true).build();
		return response;
	}

	@Override
	public AccountInfoResponse getAccountInformation(AccountInfoRequest accountInfoRequest) {

		String accountNumber = accountInfoRequest.getAccountNumber();
		boolean accountExists = checkAccountExists(accountNumber);

		if (!accountExists) {

			AccountInfoResponse response = new AccountInfoResponse(400, false,
					"No account found with the specified number " + accountNumber);
			return response;
		}
		String accountPassword = accountInfoRequest.getAccountPassword();
		 boolean isValidPassword = isValidPassword(accountPassword, accountNumber);
			if (!isValidPassword) {

				AccountInfoResponse response = new AccountInfoResponse(403, false,
						"Invalid Password " );
				return response;
			}

		Account accountInfo = account.get(accountNumber);
		
		Accounts accounts = Accounts.builder().accountName(accountInfo.getAccountName())
				.accountNumber(accountInfo.getAccountNumber()).balance(accountInfo.getBalance()).build();

		String message = "Account information successfuly retreived";
		AccountInfoResponse accountInfoResponse = new AccountInfoResponse(200, true, message, accounts);

		return accountInfoResponse;

	}

	@Override
	public AccountStatementResponse getAccountStatement(AccountStatementRequest accountStatementRequest) {

		String accountNumber = accountStatementRequest.getAccountNumber();
		boolean accountExists = checkAccountExists(accountNumber);

		if (!accountExists) {

			AccountStatementResponse response = new AccountStatementResponse(400, false,
					"No account found with the specified number " + accountNumber);
			return response;
		}
		String accountPassword = accountStatementRequest.getAccountPassword();
        boolean isValidPassword = isValidPassword(accountPassword, accountNumber);
		if (!isValidPassword) {

			AccountStatementResponse response = new AccountStatementResponse(403, false,
					"Invalid Password " );
			return response;
		}

		List<AccountStatementDTO> accountStatementDTO = accountStatement.get(accountNumber);

		AccountStatementResponse accountStatementResponse = new AccountStatementResponse(200,true, "Account Statement succesfully retrived",
				accountStatementDTO);

		return accountStatementResponse;
	}

	private static String generateAccountNumber() {

		long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

		String accountNumber = String.valueOf(number);
		return accountNumber;
	}

	private boolean checkAccountExists(String accountNumber) {

		return account.containsKey(accountNumber);
	}

	private boolean isValidPassword(String accountPassword, String accountNumber) {

		Account accounts = account.get(accountNumber);

		return accounts.getPassword().equals(accountPassword);

	}

}
