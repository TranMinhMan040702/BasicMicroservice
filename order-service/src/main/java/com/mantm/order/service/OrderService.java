package com.mantm.order.service;

import com.mantm.order.dto.OrderItemsDto;
import com.mantm.order.dto.OrderRequest;
import com.mantm.order.entity.Order;
import com.mantm.order.entity.OrderItems;
import com.mantm.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItems =
                orderRequest.getOrderItemDtos().stream().map(this::mapToEntity).toList();
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    private OrderItems mapToEntity(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setSkuCode(orderItemsDto.getSkuCode());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        orderItems.setPrice(orderItemsDto.getPrice());
        return orderItems;
    }

}
