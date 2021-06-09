package com.app.bank.model.response;

import java.util.List;

import com.app.bank.model.dto.AccountStatementDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountStatementResponse extends AccountResponse{
	
	
	private List<AccountStatementDTO> accountStatementDTO;
	
	public AccountStatementResponse(int responseCode, boolean success,String message
 ) {
		
		super(responseCode,success,message);
	}
	
	public AccountStatementResponse(int responseCode, boolean success,String message, List<AccountStatementDTO> accountStatementDTO
 ) {
		
		super(responseCode,success,message);
		this.accountStatementDTO=accountStatementDTO;
	}

}
