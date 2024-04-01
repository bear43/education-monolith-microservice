package ru.tinkoff.tinkoffeda.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentGateService {

    public String doPayment(String client, double finalPrice) {
        return UUID.randomUUID().toString();
    }
}
