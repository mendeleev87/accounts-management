package com.westernacher.account.dto;

public class UpdateFieldDTO<T> {
	
	private T newValue;

	public T getNewValue() {
		return newValue;
	}

	public void setNewValue(T newValue) {
		this.newValue = newValue;
	}

}
