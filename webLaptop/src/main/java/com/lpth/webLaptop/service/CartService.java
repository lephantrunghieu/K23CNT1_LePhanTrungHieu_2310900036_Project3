package com.lpth.webLaptop.service;

import com.lpth.webLaptop.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private Map<Integer, CartItem> cart = new HashMap<>();

    public void add(CartItem item) {
        if (cart.containsKey(item.getId())) {
            cart.get(item.getId()).setSoluong(cart.get(item.getId()).getSoluong() + 1);
        } else {
            cart.put(item.getId(), item);
        }
    }

    public void update(Integer id, int qty) {
        if (cart.containsKey(id)) {
            cart.get(id).setSoluong(qty);
        }
    }

    public void remove(Integer id) {
        cart.remove(id);
    }

    public void clear() {
        cart.clear();
    }

    public Collection<CartItem> getItems() {
        return cart.values();
    }

    public int getTotal() {
        return cart.values().stream().mapToInt(CartItem::getThanhTien).sum();
    }

    public int getCount() {
        return cart.size();
    }
}
