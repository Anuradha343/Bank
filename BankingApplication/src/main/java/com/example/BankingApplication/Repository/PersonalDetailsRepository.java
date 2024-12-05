package com.example.BankingApplication.Repository;

import com.example.BankingApplication.Domain.PersonalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, String> {

    Optional<PersonalDetails> findByAccountNumber(String accountNumber);

}


