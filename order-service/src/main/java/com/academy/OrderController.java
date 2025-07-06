package com.academy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final KitchenServiceClient kitchenServiceClient;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order) {
        // Сохраняем заказ
        Order savedOrder = orderRepository.save(order);

        // Создаем запрос для кухни (передаем только ID заказа)
        KitchenOrderRequest request = new KitchenOrderRequest();
        request.setOrderId(savedOrder.getId());

        // Отправляем уведомление кухне
        kitchenServiceClient.createKitchenOrder(request);

        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
