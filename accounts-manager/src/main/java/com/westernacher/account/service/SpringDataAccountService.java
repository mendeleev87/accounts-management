package com.westernacher.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.westernacher.account.exception.InvalidIdException;
import com.westernacher.account.model.Account;
import com.westernacher.account.repository.AccountsRepository;

@Service
public class SpringDataAccountService implements AccountService {
	
	@Autowired
	private AccountsRepository crud;

	@Override
	public Account save(Account a) {
		return crud.save(a);
	}

	@Override
	public List<Account> findAll() {
		return crud.findAll();
	}

	@Override
	public void delete(Integer id) throws InvalidIdException {
		if (!crud.exists(id)) {
			throw new InvalidIdException("An account with this id does not exist in the database.");
		}
		crud.delete(id);
	}

}
