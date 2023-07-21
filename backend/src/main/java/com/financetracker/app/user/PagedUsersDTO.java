package com.financetracker.app.user;

import java.util.List;

public record PagedUsersDTO(

    int totalPages,

    int currentPage,

    List<UserDTO> users

) { }
