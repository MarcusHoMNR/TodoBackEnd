package com.example.todoList.repository;

import com.example.todoList.entity.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoItem, String> {
}
