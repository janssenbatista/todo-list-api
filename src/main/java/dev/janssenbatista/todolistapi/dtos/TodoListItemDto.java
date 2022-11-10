package dev.janssenbatista.todolistapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoListItemDto {

    @NotBlank
    private String description;
    private boolean isDone;
}
