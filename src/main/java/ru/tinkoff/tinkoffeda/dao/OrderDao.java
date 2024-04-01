package ru.tinkoff.tinkoffeda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.tinkoffeda.model.Order;

public interface OrderDao extends JpaRepository<Order, Long> {

}
