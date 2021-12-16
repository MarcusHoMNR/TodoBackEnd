package com.example.todoList.service;

import com.example.todoList.entity.TodoItem;
import com.example.todoList.exception.NoTodoItemFoundException;
import com.example.todoList.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public List<TodoItem> findAll() {
        return todoRepository.findAll();
    }

    public TodoItem create(TodoItem newTodoItem) {
        return todoRepository.insert(newTodoItem);
    }

    public void delete(String id) {
        todoRepository.deleteById(id);
    }

    public TodoItem edit(String id, TodoItem updatedTodoItem) {
        TodoItem todoItem = todoRepository.findById(id).orElseThrow(NoTodoItemFoundException::new);
        if (updatedTodoItem.getText() != null && !updatedTodoItem.getText().equals(todoItem.getText())) {
            todoItem.setText(updatedTodoItem.getText());
        } else {
            todoItem.setDone(!todoItem.isDone());
        }

        return todoRepository.save(todoItem);
    }
}
