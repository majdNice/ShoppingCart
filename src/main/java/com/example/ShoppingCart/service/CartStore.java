package com.example.ShoppingCart.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
public class CartStore {
    private static final Logger logger = Logger.getLogger(CartStore.class.getName());
    private final Map<String, Cart> carts = new ConcurrentHashMap<>();

    public String createCart(double discountPercentage) {
        String cartId = UUID.randomUUID().toString();
        logger.info("Creating new cart with ID: " + cartId + " and discount " + discountPercentage + "%");
        carts.put(cartId, new Cart(discountPercentage));
        return cartId;
    }

    public Cart getCart(String cartId) {
        return carts.get(cartId);
    }

    public boolean cartExists(String cartId) {
        return carts.containsKey(cartId);
    }
}
