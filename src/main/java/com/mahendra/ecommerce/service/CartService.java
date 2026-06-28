package com.mahendra.ecommerce.service;

import com.mahendra.ecommerce.model.Cart;
import com.mahendra.ecommerce.model.Product;
import com.mahendra.ecommerce.model.User;
import com.mahendra.ecommerce.repository.CartRepository;
import com.mahendra.ecommerce.repository.ProductRepository;
import com.mahendra.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if (user == null || product == null) return null;

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public List<Cart> getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public String removeFromCart(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            return "Cart item not found!";
        }
        cartRepository.deleteById(cartId);
        return "Item removed from cart!";
    }
}