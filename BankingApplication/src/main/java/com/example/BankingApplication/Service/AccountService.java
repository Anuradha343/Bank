package com.example.BankingApplication.Service;

import com.example.BankingApplication.Domain.Account;
import com.example.BankingApplication.Domain.PersonalDetails;
import com.example.BankingApplication.Repository.AccountRepository;
import com.example.BankingApplication.Repository.PersonalDetailsRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Account>getAllAccounts(){
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber){
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber));
    }
    @Transactional
    public Account createAccount(Account account) {
        String accountNumber = account.getAccountNumber();

        // Check if an account already exists with this accountNumber
        if (accountRepository.findByAccountNumber(accountNumber) != null) {
            throw new RuntimeException("Account already exists with account number: " + accountNumber);
        }

        // Check if PersonalDetails exists
        Optional<PersonalDetails> personalDetails = personalDetailsRepository.findById(accountNumber);
        if (personalDetails.isEmpty()) {
            throw new RuntimeException("PersonalDetails not found for account number: " + accountNumber);
        }

        // Link the entities
        account.setPersonalDetails(personalDetails.get());

        // Save account without worrying about version
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Account account, String accountNumber) {
        // Fetch the Account entity from the database to ensure it is managed within the same transaction
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber);

        if (existingAccount == null) {
            throw new RuntimeException("Account not found with account number: " + accountNumber);
        }

        // Update fields of the existing managed entity
        existingAccount.setCustomerID(account.getCustomerID());
        existingAccount.setPanID(account.getPanID());

        // No need to call entityManager.merge() if the entity is already managed
        return existingAccount; // Return the updated entity
    }


    public void  deleteAccount(String accountNumber){
        accountRepository.deleteById(accountNumber);
    }
}
