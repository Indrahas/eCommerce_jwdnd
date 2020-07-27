package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemControllerTest {

    ItemController itemController;
    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void get_all_items(){
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

        when(itemRepository.findAll()).thenReturn(list);

        ResponseEntity<List<Item>> response = itemController.getItems();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        List<Item> new_list = response.getBody();
        Assertions.assertEquals(2 , new_list.size());
        Assertions.assertEquals(list , new_list);
    }

    @Test
    public void get_all_items_by_id(){
        Item item1 = new Item();
        item1.setDescription("Item 1 Description");
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setPrice(BigDecimal.TEN);

        when(itemRepository.findById(1l)).thenReturn(java.util.Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        Item new_item = response.getBody();
        Assertions.assertEquals(java.util.Optional.of(1L),java.util.Optional.of( new_item.getId()));
    }

    @Test
    public void get_all_items_by_name(){
        Item item1 = new Item();
        item1.setDescription("Item 1 Description");
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setPrice(BigDecimal.TEN);
        List<Item> list = new ArrayList<>();
        list.add(item1);
        when(itemRepository.findByName("Item 1")).thenReturn(list);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Item 1");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200 , response.getStatusCodeValue());
        List<Item> new_item = response.getBody();
        Assertions.assertEquals("Item 1", new_item.get(0).getName());
    }

}
