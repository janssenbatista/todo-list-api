package dev.janssenbatista.todolistapi.repositories;

import dev.janssenbatista.todolistapi.models.TodoListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListItemRepository extends JpaRepository<TodoListItem, Long> {
    List<TodoListItem> findAllByTodoListId(Long todoListId);
}
