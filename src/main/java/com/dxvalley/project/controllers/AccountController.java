package com.dxvalley.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxvalley.project.models.Account;
import com.dxvalley.project.models.CreateResponse;
import com.dxvalley.project.services.AccountService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private final AccountService accountService;
    
    @GetMapping("/getaccounts")
    List<Account> getAccounts() {
        return this.accountService.getAccounts();
    }

    @GetMapping("/{accountId}")
    Account getAccount(@PathVariable Long accountId) {
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/add")
    public ResponseEntity<CreateResponse> addAccount(@RequestBody Account account) {
        Account accountToBeChecked=accountService.getAccountByAccountNumber(account.getAccountNumber());
        if(accountToBeChecked!=null)
        {
            CreateResponse response = new CreateResponse("error", "the account is in our record associated with a pc or union");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else{
            accountService.addAccount(account);
            CreateResponse response = new CreateResponse("success", "account added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/edit/{accountId}")
    Account editMember(@RequestBody Account tempAccount, @PathVariable Long accountId) {
        Account account = this.accountService.getAccountById(accountId);
        account.setAccountNumber(tempAccount.getAccountNumber());
        account.setAccountType(tempAccount.getAccountType());
        account.setBranch(tempAccount.getBranch());
        account.setDistrict(tempAccount.getDistrict());
        account.setPrCooperative(tempAccount.getPrCooperative());
        account.setUnion(tempAccount.getUnion());
        return accountService.editAccount(account);
    }

    // @DeleteMapping("/delete/{sectorId}")
    // void deleteSector(@PathVariable Long sectorId) {
    // sectorService.deleteSector(sectorId);
    // }
}