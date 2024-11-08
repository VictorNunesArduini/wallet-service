package com.github.wallet_service.application.port;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.github.wallet_service.application.model.OrderDTO;
import com.github.wallet_service.application.model.WalletDTO;

public interface WalletApplication {

    WalletDTO create(WalletDTO wallet);

    void executeOrder(WalletDTO wallet, OrderDTO order);

    BigDecimal retrieveBalance(WalletDTO wallet, LocalDate date);
}
