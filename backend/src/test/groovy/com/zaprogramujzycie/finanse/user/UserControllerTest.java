package com.zaprogramujzycie.finanse.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerTest {

    private static final String ID = "123";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUsers() throws Exception {
        UserDTO userDTO = new UserDTO();
        List<UserDTO> users = Arrays.asList(userDTO);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetUser() throws Exception {
        UserDTO userDTO = new UserDTO();

        when(userService.getUserById(ID)).thenReturn(userDTO);

        mockMvc.perform(get("/users/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, times(1)).getUserById(ID);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO();

        when(userService.updateUser(anyString(), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/users/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test\"}"))  // Ensure you serialize a valid UserDTO object here
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(anyString(), any(UserDTO.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).delete(ID);

        mockMvc.perform(delete("/users/{id}", ID))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(ID);
        verifyNoMoreInteractions(userService);
    }
}
