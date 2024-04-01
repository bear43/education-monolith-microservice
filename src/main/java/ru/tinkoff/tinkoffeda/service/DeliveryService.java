package ru.tinkoff.tinkoffeda.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tinkoffeda.model.Order;

@Service
@RequiredArgsConstructor
public class DeliveryService {


    public double calculatePrice(String restaurantAddress, String destinationAddress) {
        return 1337.0;
    }

    public void sendNotification(Order order) {

    }
}
