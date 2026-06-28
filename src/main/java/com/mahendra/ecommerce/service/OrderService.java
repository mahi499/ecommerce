package com.mahendra.ecommerce.service;

import com.mahendra.ecommerce.model.Cart;
import com.mahendra.ecommerce.model.Order;
import com.mahendra.ecommerce.model.User;
import com.mahendra.ecommerce.repository.CartRepository;
import com.mahendra.ecommerce.repository.OrderRepository;
import com.mahendra.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public String placeOrder(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "User not found!";

        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) return "Cart is empty!";

        double total = cartItems.stream()
                .mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(total);
        orderRepository.save(order);

        // Clear cart after order
        cartRepository.deleteAll(cartItems);

        return "Order placed successfully! Total: ₹" + total;
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}