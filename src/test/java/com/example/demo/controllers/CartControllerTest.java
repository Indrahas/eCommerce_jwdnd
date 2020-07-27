package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTest {

    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void add_to_cart(){
        User user = new User();
        Cart cart = new Cart();
        user.setUsername("Indra");
        user.setId(0L);
        user.setCart(cart);
        when(userRepository.findByUsername("Indra")).thenReturn(user);

        Item item = new Item();
        item.setDescription("Description");
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(BigDecimal.TEN);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));


        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("Indra");
        request.setItemId(1L);
        request.setQuantity(4);
        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        Cart new_cart = response.getBody();
        User new_user = new_cart.getUser();
        List<Item> item1 = new_cart.getItems();
        System.out.println(new_user.getUsername());
        Assertions.assertEquals("Indra" , new_user.getUsername());
        Assertions.assertEquals(4 , item1.size());
    }

    @Test
    public void invalid_add_to_cart(){
        User user = new User();
        Cart cart = new Cart();
        user.setUsername("Indra");
        user.setId(0L);
        user.setCart(cart);
        when(userRepository.findByUsername("Indra")).thenReturn(user);

        Item item = new Item();
        item.setDescription("Description");
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(BigDecimal.TEN);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("Indrahas");
        request.setItemId(1L);
        request.setQuantity(4);
        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(404 , response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart(){
        User user = new User();
        Cart cart = new Cart();
        user.setUsername("Indra");
        user.setId(0L);
        user.setCart(cart);
        when(userRepository.findByUsername("Indra")).thenReturn(user);

        Item item = new Item();
        item.setDescription("Description");
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(BigDecimal.TEN);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("Indra");
        request.setItemId(1L);
        request.setQuantity(4);
        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
    }





}
