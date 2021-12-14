package ru.penza.forms;

import lombok.Data;

@Data
public class UserForm {
    private String name;
    private String lastname;
    private String login;
    private String password;  // с формы приходит именно пароль в чистом виде
    private String role;
}
