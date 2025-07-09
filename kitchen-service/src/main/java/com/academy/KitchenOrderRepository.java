package com.academy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenOrderRepository extends JpaRepository<KitchenOrder, Long> {
}