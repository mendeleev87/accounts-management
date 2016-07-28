package com.westernacher.account.repository;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.westernacher.account.model.Account;

@Validated
public interface AccountsRepository extends JpaRepository<Account, Integer> {

	@Modifying
	@Transactional
	@Query("update Account acc set acc.firstName = :newValue where acc.id = :id")

	int updateFirstName(@Param("id") Integer id,
			@Param("newValue") @Pattern(regexp = Account.NAME_PATTERN) @NotBlank String newValue);

	@Modifying
	@Transactional
	@Query("update Account acc set acc.lastName = :newValue where acc.id = :id")
	int updateLastName(@Param("id") Integer id,
			@Param("newValue") @Pattern(regexp = Account.NAME_PATTERN) @NotBlank String newValue);

	@Modifying
	@Transactional
	@Query("update Account acc set acc.email = :newValue where acc.id = :id")
	int updateEmail(@Param("id") Integer id, @Param("newValue") @Email @NotBlank String newValue);

	@Modifying
	@Transactional
	@Query("update Account acc set acc.dateOfBirth = :newValue where acc.id = :id")
	int updateDateOfBirth(@Param("id") Integer id, @Param("newValue") @Past Date newValue);

}
