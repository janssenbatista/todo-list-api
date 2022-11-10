package dev.janssenbatista.todolistapi.controllers;

import dev.janssenbatista.todolistapi.dtos.TodoListItemDto;
import dev.janssenbatista.todolistapi.models.TodoListItem;
import dev.janssenbatista.todolistapi.services.TodoListItemService;
import dev.janssenbatista.todolistapi.services.TodoListService;
import dev.janssenbatista.todolistapi.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/users/{userId}/todo-lists/{todoListId}/todo-list-item")
public class TodoListItemController {

    @Autowired
    private UserService userService;
    @Autowired
    private TodoListService todoListService;
    @Autowired
    private TodoListItemService todoListItemService;

    @GetMapping
    public ResponseEntity<Object> getAllTodoListItems(@PathVariable Long userId,
                                                      @PathVariable Long todoListId) {
        if (!todoListService.existsById(todoListId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo List with id = " + todoListId + " not found");
        if (!userService.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + userId + " not found");
        }
        return ResponseEntity.ok(todoListItemService.getAllTodoListItemsByTodoLisId(todoListId));
    }

    @PostMapping
    public ResponseEntity<Object> createTodoListItem(@PathVariable Long userId,
                                                     @PathVariable Long todoListId,
                                                     @Valid @RequestBody TodoListItemDto todoListItemDto) {
        var optionalTodoList = todoListService.findTodoListById(todoListId);
        if (optionalTodoList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo List with id = " + todoListId + " not found");
        if (!userService.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + userId + " not found");
        }
        var todoListItem = new TodoListItem();
        BeanUtils.copyProperties(todoListItemDto, todoListItem);
        todoListItem.setCreatedAt(LocalDateTime.now());
        todoListItem.setTodoList(optionalTodoList.get());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoListItemService.saveTodoListItem(todoListItem));
    }

    @PutMapping("/{todoListItemId}")
    public ResponseEntity<Object> updateTodoListItem(@PathVariable Long userId,
                                                     @PathVariable Long todoListId,
                                                     @PathVariable Long todoListItemId,
                                                     @Valid @RequestBody TodoListItemDto todoListItemDto) {
        var optionalTodoList = todoListService.findTodoListById(todoListId);
        var optionalTodoListItem = todoListItemService.findTodoListById(todoListItemId);
        if (optionalTodoListItem.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Todo list item with id = " + todoListItemId + " not found");
        if (optionalTodoList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Todo List with id = " + todoListId + " not found");
        if (!userService.existsById(userId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + userId + " not found");
        var todoListItem = optionalTodoListItem.get();
        todoListItem.setId(todoListItemId);
        todoListItem.setDescription(todoListItemDto.getDescription());
        todoListItem.setDone(todoListItemDto.isDone());
        todoListItem.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        todoListItem.setTodoList(optionalTodoList.get());
        return ResponseEntity.ok(todoListItemService.saveTodoListItem(todoListItem));
    }

    @DeleteMapping("/{todoListItemId}")
    public ResponseEntity<Object> deleteTodoListItem(@PathVariable Long userId,
                                                     @PathVariable Long todoListId,
                                                     @PathVariable Long todoListItemId) {
        if (!todoListItemService.existsById(todoListItemId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Todo list item with id = " + todoListItemId + " not found");
        if (!todoListService.existsById(todoListId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo List with id = " + todoListId + " not found");
        if (!userService.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + userId + " not found");
        }
        todoListItemService.deleteTodoListItem(todoListItemId);
        return ResponseEntity.noContent().build();
    }

}
