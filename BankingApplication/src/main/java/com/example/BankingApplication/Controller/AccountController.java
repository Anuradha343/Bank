package com.example.BankingApplication.Controller;

import com.example.BankingApplication.Domain.Account;
import com.example.BankingApplication.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber){
        Optional<Account> account=accountService.getAccountByAccountNumber(accountNumber);
        return account.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            // Log the request body for debugging
            System.out.println("Received account: " + account);

            Account createdAccount = accountService.createAccount(account);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            // Log the error message
            System.out.println("Error creating account: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }




    @PutMapping("/{accountNumber}")
    public ResponseEntity<Account>updateAccount(@RequestBody Account account,@PathVariable String accountNumber){
        Account updatedAccount= accountService.updateAccount(account,accountNumber);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber){
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }

}


