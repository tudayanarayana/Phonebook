package com.ibm.app.ws.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "PhonebookEntry Not Found")
public class PhonebookEntryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8610198915757870729L;

	public PhonebookEntryNotFoundException(String message) {
		super(message);
	}
}
