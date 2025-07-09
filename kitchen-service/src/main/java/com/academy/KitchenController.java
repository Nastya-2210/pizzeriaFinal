package com.academy;

import dto.OrderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/kitchen/orders")
@RequiredArgsConstructor
public class KitchenController {
    private final KitchenOrderRepository repository;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOrder createKitchenOrder(@RequestBody OrderRequestDTO request) {
        KitchenOrder kitchenOrder = new KitchenOrder();
        kitchenOrder.setStatus(KitchenOrderStatus.NEW);
        kitchenOrder.setAcceptedAt(LocalDateTime.now());
        return repository.save(kitchenOrder);
    }

    @Transactional
    @PutMapping("/{id}/accept")
    public KitchenOrder acceptOrder(@PathVariable Long id) {
        KitchenOrder order = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getStatus() != KitchenOrderStatus.NEW) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order cannot be accepted");
        }

        order.setStatus(KitchenOrderStatus.PREPARING);
        order.setAcceptedAt(LocalDateTime.now());
        return repository.save(order);
    }

    @Transactional
    @PutMapping("/{id}/complete")
    public KitchenOrder completeOrder(@PathVariable Long id) {
        KitchenOrder order = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.setStatus(KitchenOrderStatus.valueOf("COMPLETED"));
        order.setCompletedAt(LocalDateTime.now());
        return repository.save(order);
    }

    @GetMapping
    public List<KitchenOrder> getAllOrders() {
        return repository.findAll();
    }
}