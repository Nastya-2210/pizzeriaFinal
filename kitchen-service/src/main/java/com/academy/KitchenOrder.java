package com.academy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "kitchen_orders")
@Getter
@Setter
@NoArgsConstructor
public class KitchenOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Добавьте эту аннотацию
    private Long id;

    @Enumerated(EnumType.STRING)
    private KitchenOrderStatus status = KitchenOrderStatus.NEW;

    private LocalDateTime acceptedAt;
    private LocalDateTime completedAt;
}