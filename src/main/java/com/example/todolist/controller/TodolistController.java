package com.example.todolist.controller;

import com.example.todolist.domain.TodolistEntity;
import com.example.todolist.dto.TodolistRequest;
import com.example.todolist.dto.TodolistResponse;
import com.example.todolist.service.TodolistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;




@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodolistController {

    private final TodolistService service;

    @PostMapping
    public ResponseEntity<TodolistResponse> create(@RequestBody TodolistRequest request) {

        if (ObjectUtils.isEmpty(request.getTitle()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);

        if (ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodolistEntity result = this.service.add(request);
        return ResponseEntity.ok(new TodolistResponse(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodolistResponse> readOne(@PathVariable Long id) {

        TodolistEntity result = this.service.searchById(id);
        return ResponseEntity.ok(new TodolistResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodolistResponse>> readAll() {

        List<TodolistEntity> list = this.service.searchAll();
        List<TodolistResponse> response = list.stream().map(TodolistResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodolistEntity> update(@PathVariable Long id, @RequestBody TodolistRequest request) {

        TodolistEntity result = this.service.updateById(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {

        this.service.deleteById(id);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {

        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
