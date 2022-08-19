package com.example.todolist.service.repository;

import com.example.todolist.model.TodolistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodolistRepository extends JpaRepository<TodolistModel, Long> {
}
