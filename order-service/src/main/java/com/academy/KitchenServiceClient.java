package com.academy;

import dto.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kitchen-service", url = "${feign.client.config.kitchen-service.url}")
public interface KitchenServiceClient {
    @PostMapping("/kitchen/orders")
    void createKitchenOrder(@RequestBody OrderRequestDTO request);
}