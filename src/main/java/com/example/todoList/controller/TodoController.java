package com.example.todoList.controller;

import com.example.todoList.entity.TodoItem;
import com.example.todoList.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todos")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping
    public List<TodoItem> getAllTodoItems() {
        return todoService.findAll();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public TodoItem addTodoItem(@RequestBody TodoItem newTodoItem) {
        return todoService.create(newTodoItem);
    }

    @PutMapping("/{id}")
    public TodoItem editEmployee(@PathVariable String id, @RequestBody TodoItem updatedTodoItem) {
        return todoService.edit(id, updatedTodoItem);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTodoItem(@PathVariable String id) {
         todoService.delete(id);
    }
}
