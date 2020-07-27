package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

    private OrderController orderController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;


    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void order_submit(){
        User user = new User();
        Cart cart = new Cart();
        user.setUsername("Indra");
        user.setId(0L);

        Item item1 = new Item();
        item1.setDescription("Item 1 Description");
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setPrice(BigDecimal.TEN);

        Item item2 = new Item();
        item2.setDescription("Item 2 Description");
        item2.setId(2L);
        item2.setName("Item 2");
        item2.setPrice(BigDecimal.ONE);
        List<Item> list  = new ArrayList<>();
        list.add(item1);
        list.add(item2);

        cart.setItems(list);

        user.setCart(cart);

        when(userRepository.findByUsername("Indra")).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit("Indra");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        UserOrder order = response.getBody();
        User new_user = order.getUser();
        List<Item> new_items = order.getItems();
        Assertions.assertEquals("Indra" , new_user.getUsername());
        Assertions.assertEquals(new_items , list);

    }

    @Test
    public void get_history_order_valid_username() {
        User user = new User();
        user.setUsername("Indra");
        user.setId(0L);

        Item item1 = new Item();
        item1.setDescription("Item 1 Description");
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setPrice(BigDecimal.TEN);

        UserOrder userOrder1 = new UserOrder();
        userOrder1.setUser(user);
        userOrder1.setItems(Arrays.asList(item1));
        userOrder1.setTotal(BigDecimal.TEN);


        when(userRepository.findByUsername("Indra")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(Arrays.asList(userOrder1));

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Indra");
        assertNotNull(response);
        List<UserOrder> responseBody = response.getBody();
        assertEquals(Arrays.asList(userOrder1), responseBody);
        assertEquals(200, response.getStatusCodeValue());

    }

}
