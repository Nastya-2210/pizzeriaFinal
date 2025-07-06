package com.academy;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuRepository menuRepository;

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    @Transactional
    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItem menuItem) {
        return menuRepository.save(menuItem);
    }
}