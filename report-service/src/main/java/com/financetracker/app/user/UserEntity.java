package com.financetracker.app.user;

import com.financetracker.app.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {
    @Id
    private String id;

    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private List<Role> roles = new ArrayList<>();
}
