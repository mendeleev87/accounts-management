package com.westernacher.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.westernacher.account.model.Account;

public interface AccountsRepository extends JpaRepository<Account, Integer> {

	@Modifying
	@Query("update Account acc set acc.:field = :newValue where acc.id = :id")
	int setAccountField(@Param("id") Integer id, @Param("field") String field, @Param("newValue") Object newValue);

}
