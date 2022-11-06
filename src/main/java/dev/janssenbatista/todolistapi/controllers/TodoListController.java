package dev.janssenbatista.todolistapi.controllers;

import dev.janssenbatista.todolistapi.dtos.TodoListDto;
import dev.janssenbatista.todolistapi.models.TodoList;
import dev.janssenbatista.todolistapi.models.User;
import dev.janssenbatista.todolistapi.services.TodoListService;
import dev.janssenbatista.todolistapi.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}/todo-lists")
public class TodoListController {

    @Autowired
    private UserService userService;
    @Autowired
    private TodoListService todoListService;

    @GetMapping
    public ResponseEntity<Object> getAllByUserId(@PathVariable Long userId) {
        Optional<User> user = userService.findUserById(userId);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        return ResponseEntity.ok(todoListService.findAllByUserId(userId));
    }

    @GetMapping("/{todoListId}")
    public ResponseEntity<Object> getTodoListById(@PathVariable Long userId,
                                                  @PathVariable Long todoListId) {
        if (userService.findUserById(userId).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        var todoList = todoListService.findTodoListById(todoListId);
        if (todoList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TodoList not found");
        return ResponseEntity.ok(todoList.get());
    }

    @PostMapping
    public ResponseEntity<Object> createTodoList(@PathVariable Long userId,
                                                 @RequestBody @Valid TodoListDto todoListDto) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        var todoList = new TodoList();
        BeanUtils.copyProperties(todoListDto, todoList);
        todoList.setCreatedAt(LocalDateTime.now());
        todoList.setUser(optionalUser.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(todoListService.saveTodoList(todoList));
    }

    @PutMapping("/{todoListId}")
    public ResponseEntity<Object> updateTodoList(@PathVariable Long userId,
                                                 @PathVariable Long todoListId,
                                                 @RequestBody @Valid TodoListDto todoListDto) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        Optional<TodoList> optionalTodoList = todoListService.findTodoListById(todoListId);
        if (optionalTodoList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Todolist with id = " + todoListId + " not exists");
        TodoList todoList = optionalTodoList.get();
        BeanUtils.copyProperties(todoListDto, todoList);
        todoList.setId(todoListId);
        todoList.setUser(optionalUser.get());
        todoList.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(todoListService.saveTodoList(todoList));
    }

    @DeleteMapping("/{todoListId}")
    public ResponseEntity<Object> deleteTodoList(@PathVariable Long userId,
                                                 @PathVariable Long todoListId) {
        if (userService.findUserById(userId).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        Optional<TodoList> optionalTodoList = todoListService.findTodoListById(todoListId);
        if (optionalTodoList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Todolist with id = " + todoListId + " not exists");
        todoListService.deleteTodoListById(todoListId);
        return ResponseEntity.noContent().build();
    }

}
