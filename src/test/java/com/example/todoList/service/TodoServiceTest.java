package com.example.todoList.service;

import com.example.todoList.entity.TodoItem;
import com.example.todoList.exception.NoTodoItemFoundException;
import com.example.todoList.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class TodoServiceTest {
    @Mock
    TodoRepository mockTodoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void should_return_all_item_when_findAll_given_TodoItems() {
        //given
        List<TodoItem> todoItems = createTodoItems();
        given(mockTodoRepository.findAll())
                .willReturn(todoItems);

        //when
        List<TodoItem> actual = todoService.findAll();

        //then
        assertEquals(todoItems, actual);
    }

    @Test
    void should_return_empty_list_when_findAll_given_No_TodoItem() {
        //given
        List<TodoItem> todoItems = new ArrayList<>();
        given(mockTodoRepository.findAll())
                .willReturn(todoItems);

        //when
        List<TodoItem> actual = todoService.findAll();

        //then
        assertEquals(todoItems, actual);
    }

    @Test
    void should_return_created_item_when_create_given_newTodoItem() {
        //given
        TodoItem newTodoItem = new TodoItem("Text 3", false);

        given(mockTodoRepository.insert(newTodoItem))
                .willReturn(newTodoItem);

        //when
        TodoItem actual = todoService.create(newTodoItem);

        //then
        assertEquals(newTodoItem, actual);
    }

    @Test
    void should_called_repo_deleteById_once_when_delete_given_id_and_TodoItems() {
        //when
        todoService.delete("61b1c0ca8093f31e20c3c451");

        //then
        verify(mockTodoRepository).deleteById("61b1c0ca8093f31e20c3c451");
    }

    @Test
    void should_return_updated_done_item_when_update_given_id_and_TodoItem() {
        //given
        TodoItem existingTodoItem = new TodoItem("Text 1", false);

        TodoItem updatedTodoItem = existingTodoItem;
        updatedTodoItem.setDone(true);


        given(mockTodoRepository.findById("61b1c0ca8093f31e20c3c451"))
                .willReturn(Optional.of(existingTodoItem));

        given(mockTodoRepository.save(updatedTodoItem))
                .willReturn(updatedTodoItem);

        //when
        TodoItem actual = todoService.edit("61b1c0ca8093f31e20c3c451", existingTodoItem);

        //then
        assertEquals(updatedTodoItem, actual);
    }

    @Test
    void should_return_updated_text_item_when_update_given_id_and_TodoItem() {
        //given
        TodoItem updatedTodoItem = new TodoItem("Text 2", true);
        TodoItem existingTodoItem = new TodoItem( "Text 1", true);


        given(mockTodoRepository.findById("61b1c0ca8093f31e20c3c451"))
                .willReturn(Optional.of(existingTodoItem));
        existingTodoItem.setText(updatedTodoItem.getText());

        given(mockTodoRepository.save(any(TodoItem.class)))
                .willReturn(updatedTodoItem);

        //when
        TodoItem actual = todoService.edit("61b1c0ca8093f31e20c3c451", existingTodoItem);

        //then
        assertEquals(updatedTodoItem, actual);
    }

    @Test
    void should_throw_exception_when_update_given_id() {
        //given

        given(mockTodoRepository.findById("61b1c0ca8093f31e20c3c451"))
                .willThrow(NoTodoItemFoundException.class);

        //when
        //then
        assertThrows(NoTodoItemFoundException.class, () -> todoService.edit("61b1c0ca8093f31e20c3c451", new TodoItem("Text 1", false)));
    }


    private List<TodoItem> createTodoItems() {
        return new ArrayList<>(Arrays.asList(
                new TodoItem("Text 1", false)
                , new TodoItem("Text 2", true)
        ));
    }
}
