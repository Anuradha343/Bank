package com.example.BankingApplication.Repository;

import com.example.BankingApplication.Domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
    Account findByAccountNumber(String accountNumber);

    // Custom JPQL query for fetching account with personal details
    @Query("SELECT a FROM Account a JOIN FETCH a.personalDetails WHERE a.accountNumber = :accountNumber")
    Optional<Account> findAccountWithPersonalDetails(@Param("accountNumber") String accountNumber);
}
