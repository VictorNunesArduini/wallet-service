package com.github.wallet_service.application.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderDTO {

    private final BigDecimal value;
    private final OrderTypeEnum type;
    
}
