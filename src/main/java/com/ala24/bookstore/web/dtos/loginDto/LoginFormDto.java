package com.ala24.bookstore.web.dtos.loginDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class LoginFormDto {

	@NotBlank
	private String loginId;

	@NotBlank
	private String password;

}
