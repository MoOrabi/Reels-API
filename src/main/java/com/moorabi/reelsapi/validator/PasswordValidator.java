package com.moorabi.reelsapi.validator;

import java.util.regex.Pattern;

public class PasswordValidator {
	
	/*
    *
    * Must contain at least one digit
    * Must contain at least one lower case letter
    * Must contain at least one upper case letter
    * Must contain at least one special character
    * Must be of length l where 8 <= l <= 20 (Minimum 8 characters & maximum 20 characters)
    * */
    private static final String PASSWORD_REGEX = 
    		"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static boolean isValid(String password){
		return  Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }

}
