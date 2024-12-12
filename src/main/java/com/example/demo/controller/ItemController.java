package com.example.demo.controller;

import com.example.demo.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {

    @GetMapping("/items")
    public List<ItemDto> getItemsPage(@RequestParam Integer page, @RequestParam Integer size) {
        List<ItemDto> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(ItemDto.builder().name("item" + i).price((long) (i * 10)).date(LocalDate.now().plusDays(i)).build());
        }
        return createPageFromList(list, page, size).getContent();
    }

    private Page<ItemDto> createPageFromList(List<ItemDto> items, int page, int size) {
        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + size), items.size());
        List<ItemDto> subList = items.subList(start, end);
        return new PageImpl<>(subList, PageRequest.of(page, size), items.size());
    }
}