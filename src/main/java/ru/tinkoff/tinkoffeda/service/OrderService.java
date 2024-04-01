package ru.tinkoff.tinkoffeda.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tinkoffeda.dao.OrderDao;
import ru.tinkoff.tinkoffeda.dto.BasketDto;
import ru.tinkoff.tinkoffeda.model.Order;
import ru.tinkoff.tinkoffeda.model.Restaurant;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final DeliveryService deliveryService;
    private final RestaurantService restaurantService;
    private final PaymentGateService paymentGate;
    private final ObjectMapper objectMapper;
    private final OrderDao orderDao;

    public double calculatePrice(BasketDto basketDto) {
        double itemsPrice = restaurantService.calculatePrice(basketDto.items());
        double deliveryPrice = deliveryService.calculatePrice("", basketDto.address());
        return itemsPrice + deliveryPrice;
    }

    @Transactional
    public void create(String client, Long restaurantId,  BasketDto basketDto) {
        Order order = new Order()
                .setClient(client)
                .setRestaurant(new Restaurant().setId(restaurantId))
                .setAddress(basketDto.address());

        Map<String, Object> basketMap = objectMapper.convertValue(basketDto, Map.class);

        order.setBasket(basketMap);
        double finalPrice = calculatePrice(basketDto);

        String trxId = paymentGate.doPayment(client, finalPrice);
        order.setTrxId(trxId);

        orderDao.saveAndFlush(order);

        deliveryService.sendNotification(order);
        restaurantService.sendNotification(order);
    }

}
