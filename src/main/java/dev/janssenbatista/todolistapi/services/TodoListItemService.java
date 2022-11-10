package dev.janssenbatista.todolistapi.services;

import dev.janssenbatista.todolistapi.models.TodoListItem;
import dev.janssenbatista.todolistapi.repositories.TodoListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListItemService {

    @Autowired
    private TodoListItemRepository todoListItemRepository;

    public TodoListItem saveTodoListItem(TodoListItem todoListItem) {
        return todoListItemRepository.save(todoListItem);
    }

    public List<TodoListItem> getAllTodoListItemsByTodoLisId(Long todoListId) {
        return todoListItemRepository.findAllByTodoListId(todoListId);
    }

    public void deleteTodoListItem(Long todoListItemId) {
        todoListItemRepository.deleteById(todoListItemId);
    }

    public boolean existsById(Long todoListItemId) {
        return todoListItemRepository.existsById(todoListItemId);
    }

    public Optional<TodoListItem> findTodoListById(Long todoListItemId) {
        return todoListItemRepository.findById(todoListItemId);
    }
}
