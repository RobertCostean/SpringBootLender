package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.peerlender.lendingengine.domain.model.Users;

import java.time.Duration;
import java.util.Optional;

@Component
public class LoanApplicationAdapter {
    private final UserRepository userRepository;

    @Autowired
    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanApplication transform(LoanRequest req, Users borrower) {
        Optional<Users> userOptional = userRepository.findById(borrower.getUsername());

        if(userOptional.isPresent()){
            return new LoanApplication(req.getAmount(), userOptional.get(), req.getDaysToRepay(), req.getInterestRate());
        }
        else {
            throw new UserNotFoundException(borrower.getUsername());
        }
    }
}
