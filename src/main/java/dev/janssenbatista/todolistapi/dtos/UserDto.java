package dev.janssenbatista.todolistapi.dtos;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {

    @NotBlank
    @Size(max = 50)
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Size(max = 70)
    @EqualsAndHashCode.Include
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
}
