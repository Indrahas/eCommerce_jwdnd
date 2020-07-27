package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {


    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void login(){
        when(bCryptPasswordEncoder.encode("test1234")).thenReturn("hashedpassword");
         CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Indra");
        createUserRequest.setPassword("test1234");
        createUserRequest.setConfirmPassword("test1234");
        final ResponseEntity<User> response =  userController.createUser(createUserRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        User user = response.getBody();
        Assertions.assertNotNull(user);
        Assertions.assertEquals("Indra" , user.getUsername());
        Assertions.assertEquals("hashedpassword" , user.getPassword());


    }
    @Test
    public void invalid_login1(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Indra");
        createUserRequest.setPassword("test");
        createUserRequest.setConfirmPassword("test");
        final ResponseEntity<User> response =  userController.createUser(createUserRequest);
        Assertions.assertEquals(400 , response.getStatusCodeValue());
    }
    @Test
    public void invalid_login2(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Indra");
        createUserRequest.setPassword("test12345");
        createUserRequest.setConfirmPassword("test1234");
        final ResponseEntity<User> response =  userController.createUser(createUserRequest);
        Assertions.assertEquals(400 , response.getStatusCodeValue());
    }
    @Test
    public void find_by_id(){
        User user = new User();
        user.setUsername("Indra");
        user.setId(0L);
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user));
        final ResponseEntity<User> response = userController.findById(0L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        User new_user = response.getBody();
        Assertions.assertEquals(0L , new_user.getId());
        Assertions.assertEquals("Indra" , new_user.getUsername());
    }
    @Test
    public void invalid_find_by_id(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Indra");
        createUserRequest.setPassword("test12345");
        createUserRequest.setConfirmPassword("test1234");
        final ResponseEntity<User> response =  userController.createUser(createUserRequest);
        final ResponseEntity<User> response2 = userController.findById(1000L);
        Assertions.assertEquals(404 , response2.getStatusCodeValue());
    }

    @Test
    public void find_by_username(){
        User user = new User();
        user.setUsername("Indra");
        user.setId(0L);
        when(userRepository.findByUsername("Indra")).thenReturn(user);
        final ResponseEntity<User> response = userController.findByUserName("Indra");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        User new_user = response.getBody();
        Assertions.assertEquals("Indra" , new_user.getUsername());
    }
    @Test
    public void invalid_find_by_username(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Indra");
        createUserRequest.setPassword("test12345");
        createUserRequest.setConfirmPassword("test1234");
        final ResponseEntity<User> response2 = userController.findByUserName("Test Username");
        Assertions.assertEquals(404 , response2.getStatusCodeValue());
    }


}
