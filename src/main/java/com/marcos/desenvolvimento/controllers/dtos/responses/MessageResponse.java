package com.marcos.desenvolvimento.controllers.dtos.responses;

import lombok.Data;

@Data
public class MessageResponse {
	private String message;

	public MessageResponse(final String message) {
	    this.message = message;
	  }
}
