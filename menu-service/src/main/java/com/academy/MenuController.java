package com.academy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuRepository menuRepository;

    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
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