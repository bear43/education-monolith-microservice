package ru.tinkoff.tinkoffeda.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tinkoffeda.dao.MenuItemDao;
import ru.tinkoff.tinkoffeda.dao.RestaurantDao;
import ru.tinkoff.tinkoffeda.dto.BasketItemDto;
import ru.tinkoff.tinkoffeda.dto.MenuItemDto;
import ru.tinkoff.tinkoffeda.dto.RestaurantDto;
import ru.tinkoff.tinkoffeda.model.MenuItem;
import ru.tinkoff.tinkoffeda.model.Order;
import ru.tinkoff.tinkoffeda.model.Restaurant;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;
    private final MenuItemDao menuItemDao;

    @Transactional
    public Long register(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant()
                .setName(restaurantDto.name())
                .setAddress(restaurantDto.address());

        return restaurantDao.saveAndFlush(restaurant).getId();
    }

    @Transactional
    public void addItems(Collection<MenuItemDto> menuItemDto) {
        List<MenuItem> menuItems = menuItemDto.stream()
                .map(item -> new MenuItem()
                        .setName(item.name())
                        .setRestaurant(new Restaurant().setId(item.restaurantId()))
                        .setQuantity(item.quantity())
                        .setPrice(item.price()))
                .toList();

        menuItemDao.saveAllAndFlush(menuItems);
    }

    public Collection<RestaurantDto> getAll() {
        return restaurantDao.findAll()
                .stream()
                .map(restaurant -> new RestaurantDto(restaurant.getId(), restaurant.getName(),
                        restaurant.getAddress()))
                .toList();
    }

    public Collection<MenuItemDto> getAllItems(Long restaurantId) {
        return menuItemDao.findAllByRestaurant_Id(restaurantId)
                .stream()
                .map(item -> new MenuItemDto(item.getId(), item.getRestaurant().getId(),
                        item.getName(), item.getQuantity(), item.getPrice()))
                .toList();
    }

    @Transactional
    public double calculatePrice(Collection<BasketItemDto> items) {
        Map<Long, Integer> itemMap = items.stream()
                .collect(Collectors.toMap(BasketItemDto::menuItemId, BasketItemDto::quantity));
        return menuItemDao.findAllById(itemMap.keySet())
                .stream()
                .mapToDouble(item -> {
                    Long first = item.getId();
                    Integer firstQuantity = itemMap.get(first);
                    return firstQuantity * item.getPrice();
                })
                .sum();
    }

    public void sendNotification(Order order) {

    }
}
