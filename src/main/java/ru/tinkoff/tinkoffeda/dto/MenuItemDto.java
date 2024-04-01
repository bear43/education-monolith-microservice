package ru.tinkoff.tinkoffeda.dto;

public record MenuItemDto(Long id, Long restaurantId, String name, int quantity, double price) {

}
