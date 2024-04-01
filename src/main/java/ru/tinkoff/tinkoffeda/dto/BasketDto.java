package ru.tinkoff.tinkoffeda.dto;

import java.util.Set;

public record BasketDto(Set<BasketItemDto> items, String address) {

}
