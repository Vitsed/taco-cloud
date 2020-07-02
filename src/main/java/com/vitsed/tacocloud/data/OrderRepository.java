package com.vitsed.tacocloud.data;

import com.vitsed.tacocloud.Order;

public interface OrderRepository {
    Order save(Order order);
}
