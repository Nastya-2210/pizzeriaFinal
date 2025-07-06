package com.academy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "kitchen_orders")
public class KitchenOrder {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private KitchenOrderStatus status = KitchenOrderStatus.NEW;
    private LocalDateTime acceptedAt;
    private LocalDateTime completedAt;
}