package dev.janssenbatista.todolistapi.services;

import dev.janssenbatista.todolistapi.models.TodoList;
import dev.janssenbatista.todolistapi.repositories.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {

    @Autowired
    private TodoListRepository todoListRepository;

    @Transactional
    public TodoList saveTodoList(TodoList todoList) {
        return todoListRepository.save(todoList);
    }

    public List<TodoList> findAllByUserId(Long userId) {
        return todoListRepository.findAllByUserId(userId);
    }

    public Optional<TodoList> findTodoListById(Long todoListId) {
        return todoListRepository.findById(todoListId);
    }

    @Transactional
    public void deleteTodoListById(Long todoListId) {
        todoListRepository.deleteById(todoListId);
    }

    public boolean existsById(Long todoListId) {
        return todoListRepository.existsById(todoListId);
    }
}
