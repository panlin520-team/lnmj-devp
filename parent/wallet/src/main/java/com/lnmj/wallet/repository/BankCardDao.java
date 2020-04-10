package com.lnmj.wallet.repository;

public interface BankCardDao {
    Long selectBankCardId(String cardNumber);
}
