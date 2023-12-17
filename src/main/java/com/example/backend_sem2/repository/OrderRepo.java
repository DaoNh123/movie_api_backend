package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query(value = "FROM Order o JOIN FETCH o.orderDetailList odl " +
            "WHERE o.id = :id")
    Order getOrderByIdJoinFetchOrderDetail(Long id);

    @Query(value = "SELECT o.customerEmail FROM Order o WHERE o.id = :id ")
    String getCustomerEmailById(Long id);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.orderDetailList odl " +
            "JOIN FETCH odl.seat s JOIN FETCH o.slot sl " +
            "JOIN FETCH sl.movie m JOIN FETCH sl.theaterRoom tr " +
            "WHERE o.id = :id")
    Order getOrderCustomById(Long id);
}
