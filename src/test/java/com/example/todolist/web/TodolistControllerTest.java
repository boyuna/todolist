package com.example.todolist.web;

import com.example.todolist.model.TodolistModel;
import com.example.todolist.model.TodolistRequest;
import com.example.todolist.service.TodolistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodolistController.class)
class TodolistControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodolistService todoService;

    private TodolistModel expected;

    // 테스트 진행 전 expected의 값 초기화
    @BeforeEach
    void setup() {
        this.expected = new TodolistModel();
        this.expected.setId(123L);
        this.expected.setTitle("TEST TITLE");
        this.expected.setOrder(0L);
        this.expected.setCompleted(false);
    }

    @Test
    void create() throws Exception{
        // title만 request로 받아오고 나머지는 expected에 설정한 값 사용
        when(this.todoService.add(any(TodolistRequest.class))).then((i) -> {
                    TodolistRequest request = i.getArgument(0, TodolistRequest.class);
                    return new TodolistModel(
                            this.expected.getId(),
                            request.getTitle(),
                            this.expected.getOrder(),
                            this.expected.getCompleted()
                    );
                });

        TodolistRequest request = new TodolistRequest();
        request.setTitle("ANY TITLE");

        // 작성한 request를 body에 넣어줘야 하는데 object type 자체만으로는 넣어줄 수 없기에
        // objectmapper을 이용해 body에 넣어준다
        ObjectMapper mapper = new ObjectMapper();
        // request가 string 형태로 바뀜
        String content = mapper.writeValueAsString(request);

        this.mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ANY TITLE"));
    }

    @Test
    void readOne() {
    }
}