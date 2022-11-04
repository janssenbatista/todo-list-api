package dev.janssenbatista.todolistapi.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank
    @Max(value = 50)
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Max(value = 70)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
}
