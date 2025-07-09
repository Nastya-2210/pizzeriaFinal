package com.academy;


import dto.MenuItemDTO;
import dto.OrderRequestDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private  final OrderRepository orderRepository;
    private final KitchenServiceClient kitchenServiceClient;
    private final MenuServiceClient menuServiceClient;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody OrderRequestDTO request) {
        // Создаем новый заказ
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setItems(new ArrayList<>());


        // Для каждого элемента заказа получаем цену из меню
        request.getItems().forEach(itemRequest -> {
            try {
                MenuItemDTO menuItem = menuServiceClient.getMenuItem(itemRequest.getMenuItemId());

                if (menuItem == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Menu item not found with id: " + itemRequest.getMenuItemId()
                    );
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setMenuItemId(itemRequest.getMenuItemId());
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(menuItem.getPrice());
                orderItem.setOrder(order); // Устанавливаем связь

                order.getItems().add(orderItem);

            } catch (FeignException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Failed to fetch menu item: " + e.getMessage()
                );
            }
        });

        // Сохраняем заказ
        Order savedOrder = orderRepository.save(order);
        // Отправляем уведомление кухне

        try {
            kitchenServiceClient.createKitchenOrder(request);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Kitchen service unavailable",
                    e
            ); // Транзакция откатится в случае недоступности сервиса кухни
        }
        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
