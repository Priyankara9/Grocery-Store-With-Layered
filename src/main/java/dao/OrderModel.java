package dao;

import dto.OrderDto;

public interface OrderModel {
    boolean saveOrder(OrderDto dto);
    boolean updateOrder(OrderDto dto);
}
