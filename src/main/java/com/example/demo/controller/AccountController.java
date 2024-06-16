package com.example.demo.controller;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.BalanceLowException;
import com.example.demo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountGetDto> addAccount(@RequestBody AccountPostDto AccountPostDto){
        return new ResponseEntity<>(accountService.createAccount(AccountPostDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountGetDto> getAccountById(@PathVariable Long id){
        AccountGetDto accountGetDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountGetDto);
    }

    @PostMapping("/{id}/deposit")
    public  ResponseEntity<AccountGetDto> deposit(@PathVariable Long id,
                                                   @RequestBody Map<String, Double> request){
        Double amount = request.get("amount");
        AccountGetDto accountGetDto = accountService.deposit(id, amount);
                return ResponseEntity.ok(accountGetDto);
    }

    @PostMapping("/{id}/withdraw")
    public  ResponseEntity<AccountGetDto> withdraw(@PathVariable Long id,
                                                    @RequestBody Map<String, Double> request){
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

    @ExceptionHandler(BalanceLowException.class)
    public ResponseEntity<String> handleBalanceLowException(BalanceLowException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
