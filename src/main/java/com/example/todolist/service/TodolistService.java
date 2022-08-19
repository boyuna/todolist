package com.example.todolist.service;

import com.example.todolist.model.TodolistModel;
import com.example.todolist.model.TodolistRequest;
import com.example.todolist.service.repository.TodolistRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodolistService {

    private final TodolistRepository todolistRepository;

    // 1. 항목 추가
    public TodolistModel add(TodolistRequest request) {
        TodolistModel todolistModel = new TodolistModel();
        todolistModel.setTitle(request.getTitle());
        todolistModel.setOrder(request.getOrder());
        todolistModel.setCompleted(request.getCompleted());

        // save는 제네릭으로 받은 타입(T)으로 값을 반환
        return this.todolistRepository.save(todolistModel);
    }
    // 2. 특정 항목 조회
    public TodolistModel searchById(Long id) {
        return  this.todolistRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    // 3. 전체 항목 조회
    public List<TodolistModel> searchAll() {
        return this.todolistRepository.findAll();
    }

    // 4. 항목 수정
    public TodolistModel updateById(Long id, TodolistRequest request) {
        TodolistModel todoEntity = this.searchById(id);
        if(request.getTitle() != null) {
            todoEntity.setTitle(request.getTitle());
        }
        if(request.getOrder() != null) {
            todoEntity.setOrder(request.getOrder());
        }
        if(request.getCompleted() != null) {
            todoEntity.setCompleted(request.getCompleted());
        }
        return this.todolistRepository.save(todoEntity);

    }
    // 5. 특정 항목 삭제
    public void deleteById(Long id) {
        this.todolistRepository.deleteById(id);
    }
    // 6. 전체 항목 삭제
    public void deleteAll() {
        this.todolistRepository.deleteAll();
    }
}
