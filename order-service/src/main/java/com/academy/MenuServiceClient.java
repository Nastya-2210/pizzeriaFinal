package com.academy;

import dto.MenuItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu-service", url = "${feign.client.config.menu-service.url}")
public interface MenuServiceClient {
    @GetMapping("/menu/{id}")
    MenuItemDTO getMenuItem(@PathVariable Long id);
}
