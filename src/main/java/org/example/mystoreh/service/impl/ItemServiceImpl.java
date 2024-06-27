package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.Item;
import org.example.mystoreh.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public int countTotalProductQuantity(List<Item> itemList) {
        int total = 0;
        for(Item i: itemList){
            total += i.getQuantity();
        }
        return total;
    }

    @Override
    public double totalProductAmount(List<Item> items) {
        return items.stream()
                .mapToDouble(Item::getAmount)
                .sum();
    }
}
