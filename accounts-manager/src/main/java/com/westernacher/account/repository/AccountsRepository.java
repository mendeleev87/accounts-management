package com.westernacher.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.westernacher.account.model.Account;

public interface AccountsRepository extends JpaRepository<Account, Integer>{

}
