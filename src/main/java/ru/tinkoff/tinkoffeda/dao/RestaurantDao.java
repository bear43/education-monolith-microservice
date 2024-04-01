package ru.tinkoff.tinkoffeda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.tinkoffeda.model.Restaurant;

public interface RestaurantDao extends JpaRepository<Restaurant, Long> {

}
