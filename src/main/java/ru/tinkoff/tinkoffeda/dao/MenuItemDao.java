package ru.tinkoff.tinkoffeda.dao;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.tinkoffeda.dto.MenuItemDto;
import ru.tinkoff.tinkoffeda.model.MenuItem;

public interface MenuItemDao extends JpaRepository<MenuItem, Long> {

    Collection<MenuItem> findAllByRestaurant_Id(Long restaurantId);
}
