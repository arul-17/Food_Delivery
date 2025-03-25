package com.food.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.model.OrderDetails;
import com.food.model.OrderHistory;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Integer> {

	public List<OrderHistory> findByCustomerId(Integer customerId);
	@Query("SELECT o FROM OrderHistory o WHERE o.order = :order AND o.customerId = :customerId")
	Optional<OrderHistory> findByOrderAndCustomerId(OrderDetails order, Integer customerId);
	
	@Query("SELECT oh FROM OrderHistory oh JOIN FETCH oh.bill b JOIN FETCH b.order WHERE oh.id = :orderHisId")
	Optional<OrderHistory> findByIdWithDetails(@Param("orderHisId") Integer orderHisId);
	@Query("SELECT oh FROM OrderHistory oh JOIN FETCH oh.bill b JOIN FETCH b.order WHERE oh.customerId = :customerId")
	List<OrderHistory> findOrderHistoryWithDetailsByCustomerId(@Param("customerId") Integer customerId);
	public OrderDetails save(OrderDetails order);

}
