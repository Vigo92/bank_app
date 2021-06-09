package com.app.bank.model.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

	@NotNull(message = "Name cannot be null")
	private String accountName;
	@NotNull(message = "Password cannot be null")
	private String accountPassword;

}
