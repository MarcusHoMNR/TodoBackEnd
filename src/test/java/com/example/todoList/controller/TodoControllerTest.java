package com.example.todoList.controller;

import com.example.todoList.entity.TodoItem;
import com.example.todoList.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void cleanRepository() {
        todoRepository.deleteAll();
    }

    @Test
    void should_get_all_items_when_perform_get_given_items() throws Exception {
        //given
        insert_items_to_repo();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].text").value("Spring"))
                .andExpect(jsonPath("$[0].done").value("false"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].text").value("Spring2"))
                .andExpect(jsonPath("$[1].done").value("true"));
    }

    @Test
    void should_add_item_when_perform_post_given_item() throws Exception {
        //given
        String item = "{\n" +
                "    \"text\": \"OOCL\",\n" +
                "    \"done\": false\n" +
                "}";


        //when
        //then
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(item))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.text").value("OOCL"))
                .andExpect(jsonPath("$.done").value("false"));
    }

    @Test
    void should_delete_item_when_perform_delete_given_item() throws Exception {
        //given
        insert_items_to_repo();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", "61b1c0ca8093f31e20c3c451"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void should_update_item_Done_when_perform_put_given_item() throws Exception {
        //given
        insert_items_to_repo();
        String item = "{\n" +
                "    \"text\": \"Spring\",\n" +
                "    \"done\": \"true\"\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/todos/{id}", "61b1c0ca8093f31e20c3c451")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(item))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.text").value("Spring"))
                .andExpect(jsonPath("$.done").value("true"));
    }

    @Test
    void should_update_item_text_when_perform_put_given_item() throws Exception {
        //given
        insert_items_to_repo();
        String item = "{\n" +
                "    \"text\": \"Spring3\",\n" +
                "    \"done\": \"false\"\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/todos/{id}", "61b1c0ca8093f31e20c3c451")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(item))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.text").value("Spring3"))
                .andExpect(jsonPath("$.done").value("false"));
    }


    private void insert_items_to_repo() {
        TodoItem todoItem1 = new TodoItem("61b1c0ca8093f31e20c3c451", "Spring", false);
        TodoItem todoItem2 = new TodoItem("61b1c0ca8093f31e20c3c452", "Spring2", true);

        todoRepository.insert(todoItem1);
        todoRepository.insert(todoItem2);
    }
}
