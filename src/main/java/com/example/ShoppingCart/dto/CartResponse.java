package com.example.ShoppingCart.dto;

import java.util.List;

import com.example.ShoppingCart.service.Cart;

public record CartResponse(
                String cartId,
                List<CartItemResponse> items,
                int subtotal,
                double discountPercentage,
                double finalTotal) {
        public static CartResponse from(String cartId, Cart cart) {
                List<CartItemResponse> items = cart.getItems().stream()
                                .map(CartItemResponse::from)
                                .toList();
                return new CartResponse(
                                cartId,
                                items,
                                cart.getSubtotal(),
                                cart.getDiscountPercentage(),
                                cart.getFinalTotal());
        }
}
