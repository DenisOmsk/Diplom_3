package data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String email; // Электронная почта пользователя
    private String password; // Пароль пользователя
    private String name; // Имя пользователя
}