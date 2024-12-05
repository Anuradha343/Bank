package com.example.BankingApplication.Service;

import com.example.BankingApplication.Domain.PersonalDetails;
import com.example.BankingApplication.Repository.PersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonalDetailsService {
    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    public List<PersonalDetails>getAllPersonalDetails() {
        return personalDetailsRepository.findAll();
    }
    public Optional<PersonalDetails> getAccountDetailsByAccountNumber(String accountNumber){
        return personalDetailsRepository.findByAccountNumber(accountNumber);
    }
    public PersonalDetails createDetails(PersonalDetails personalDetails){
        if (personalDetails.getAccountNumber() == null) {
            personalDetails.setAccountNumber(UUID.randomUUID().toString()); // auto-generate account number
        }
        return personalDetailsRepository.save(personalDetails);
    }

    public PersonalDetails updateDetails(String accountNumber, PersonalDetails personalDetails){
        PersonalDetails personalDetails1=personalDetailsRepository.findById(accountNumber).orElseThrow(() -> new RuntimeException("Account Details not found"));

        personalDetails1.setName(personalDetails.getName());
        //personalDetails1.setDOB(personalDetails.getDOB());
        //personalDetails1.setPhone(personalDetails.getPhone());
        personalDetails1.setAddress(personalDetails.getAddress());
        return personalDetailsRepository.save(personalDetails1);
    }
    public void deleteDetails(String accountNumber){
        personalDetailsRepository.deleteById(accountNumber);
    }
}
