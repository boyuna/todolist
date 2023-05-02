package com.example.todolist.repository;

import com.example.todolist.domain.TodolistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodolistRepository extends JpaRepository<TodolistEntity, Long> {
}
