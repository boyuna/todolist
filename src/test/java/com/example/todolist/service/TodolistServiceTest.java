package com.example.todolist.service;

import com.example.todolist.domain.TodolistEntity;
import com.example.todolist.dto.TodolistRequest;
import com.example.todolist.repository.TodolistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodolistServiceTest {

    @Mock
    private TodolistRepository todolistRepository;

    @InjectMocks
    private TodolistService todolistService;

    @Test
    void add() {

        // TodolistRepository가 save 메소드를 호출해서 TodolistEntity의 값을 받으면, 받은 Entity 값 반환
        when(this.todolistRepository.save(any(TodolistEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodolistRequest expected = new TodolistRequest();
        expected.setTitle("Test Title");

        TodolistEntity actual = this.todolistService.add(expected);

        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    void searchById() {
        // findById는 optional을 반환하기에 옵션널로 리턴값 넣기
        TodolistEntity entity = new TodolistEntity();
        entity.setId(123L);
        entity.setTitle("TITLE");
        entity.setOrder(0L);
        entity.setCompleted(false);
        Optional<TodolistEntity> optional = Optional.of(entity);

        // 어떠한 id값이 주어졌을 때 optional 값을 리턴
        given(this.todolistRepository.findById(anyLong())).willReturn(optional);

        // service에서 searchById를 했을 때 받은 값과 optional 값을 비교
        TodolistEntity actual = this.todolistService.searchById(123L);
        TodolistEntity expected =optional.get();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getOrder(), actual.getOrder());
        assertEquals(expected.getCompleted(), actual.getCompleted());
    }

    @Test
    public void searchByIdFailed() {
        given(this.todolistRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            this.todolistService.searchById(123L);
        });
    }
}