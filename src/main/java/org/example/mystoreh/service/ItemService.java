package org.example.mystoreh.service;

import org.example.mystoreh.entity.Item;

import java.util.List;

public interface ItemService {
    int countTotalProductQuantity(List<Item> itemList);
    double totalProductAmount(List<Item> items);
}
