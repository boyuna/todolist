package com.example.todolist.web;

import com.example.todolist.model.TodolistModel;
import com.example.todolist.model.TodolistRequest;
import com.example.todolist.model.TodolistResponse;
import com.example.todolist.service.TodolistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodolistController {

    private final TodolistService service;

    @PostMapping
    public ResponseEntity<TodolistResponse> create(@RequestBody TodolistRequest request) {
        log.info("CREATE");
        if (ObjectUtils.isEmpty(request.getTitle()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);

        if (ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodolistModel result = this.service.add(request);
        return ResponseEntity.ok(new TodolistResponse(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodolistResponse> readOne(@PathVariable Long id) {
        log.info("READ ONE");
        TodolistModel result = this.service.searchById(id);
        return ResponseEntity.ok(new TodolistResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodolistResponse>> readAll() {
        log.info("READ ALL");
        List<TodolistModel> list = this.service.searchAll();
        List<TodolistResponse> response = list.stream().map(TodolistResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodolistModel> update(@PathVariable Long id, @RequestBody TodolistRequest request) {
        log.info("UPDATE");
        TodolistModel result = this.service.updateById(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        log.info("DELETE ONE");
        this.service.deleteById(id);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        log.info("DELETE ALL");
        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
