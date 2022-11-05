package dev.janssenbatista.todolistapi.repositories;

import dev.janssenbatista.todolistapi.models.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    List<TodoList> findAllByUserId(Long userId);
}
