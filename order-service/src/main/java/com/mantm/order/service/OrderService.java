package com.mantm.order.service;

import com.mantm.order.dto.InventoryResponse;
import com.mantm.order.dto.OrderItemsDto;
import com.mantm.order.dto.OrderRequest;
import com.mantm.order.entity.Order;
import com.mantm.order.entity.OrderItems;
import com.mantm.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItems =
                orderRequest.getOrderItemDtos().stream().map(this::mapToEntity).toList();
        order.setOrderItems(orderItems);

        List<String> skuCodeInOrder = order.getOrderItems().stream()
                .map(OrderItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api" +
                                "/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeInOrder).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


        boolean allProductsIsInStock =
                inventoryResponses.length != 0 && Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsIsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }


    }

    private OrderItems mapToEntity(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setSkuCode(orderItemsDto.getSkuCode());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        orderItems.setPrice(orderItemsDto.getPrice());
        return orderItems;
    }

}
