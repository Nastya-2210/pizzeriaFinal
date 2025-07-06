package com.academy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrderRequest {
    private Long orderId;
    private LocalDateTime acceptedAt;
    private LocalDateTime completedAt;
}