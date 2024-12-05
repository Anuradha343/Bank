package com.example.BankingApplication.Controller;

import com.example.BankingApplication.Domain.PersonalDetails;
import com.example.BankingApplication.Service.PersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personalDetails")
public class PersonalDetailsController {
    @Autowired
    private PersonalDetailsService personalDetailsService;

    @GetMapping
    public List<PersonalDetails> getAllPersonalDetails(){
        return personalDetailsService.getAllPersonalDetails();
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<PersonalDetails>getAccountDetailsByAccountNumber(@PathVariable String accountNumber){
        Optional<PersonalDetails> personalDetails=personalDetailsService.getAccountDetailsByAccountNumber(accountNumber);
        return personalDetails.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonalDetails> createDetails(@RequestBody PersonalDetails personalDetails) {
        if (personalDetails == null) {
            return ResponseEntity.badRequest().build();
        }
        PersonalDetails createdDetails = personalDetailsService.createDetails(personalDetails);
        return new ResponseEntity<>(createdDetails, HttpStatus.CREATED);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<PersonalDetails>updateDetails(@PathVariable String accountNumber, @RequestBody PersonalDetails personalDetails){
        PersonalDetails updatedDetails=personalDetailsService.updateDetails(accountNumber,personalDetails);
        return ResponseEntity.ok(updatedDetails);
    }
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void>deleteDetails(@PathVariable String accountNumber){
        personalDetailsService.deleteDetails(accountNumber);
        return ResponseEntity.noContent().build();
    }

}