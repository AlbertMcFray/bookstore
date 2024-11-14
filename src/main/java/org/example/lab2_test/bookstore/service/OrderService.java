package org.example.lab2_test.bookstore.service;

import org.example.lab2_test.bookstore.entity.Order;
import org.example.lab2_test.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Mono<Order> createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Mono<Order> updateOrder(Long id, Order order) {
        return orderRepository.findById(id)
                .flatMap(existingOrder -> {
                    existingOrder.setBookId(order.getBookId());
                    existingOrder.setClientId(order.getClientId());
                    existingOrder.setClientName(order.getClientName());
                    existingOrder.setOrderDate(order.getOrderDate());
                    return orderRepository.save(existingOrder);
                });
    }

    public Mono<Void> deleteOrder(Long orderId) {
        return orderRepository.deleteById(orderId);
    }
}
