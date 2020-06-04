package com.se.exceptions;

public class ProductCategoryOperationException extends RuntimeException {


	private static final long serialVersionUID = -7385147354025201340L;

	public ProductCategoryOperationException(String msg) {
		super(msg);
	}
}
