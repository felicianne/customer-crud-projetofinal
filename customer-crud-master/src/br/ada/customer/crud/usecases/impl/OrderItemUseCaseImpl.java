package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderItem;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.model.Product;
import br.ada.customer.crud.usecases.IOrderItemUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;


public class OrderItemUseCaseImpl implements IOrderItemUseCase {

    private OrderRepository repository;

    public OrderItemUseCaseImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderItem addItem(Order order, Product product, BigDecimal price, Integer amount) {

        //Validação de condição de Open
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new OrderException("Seu pedido não pode ser alterado!");
        }
        OrderItem addedItem = new OrderItem();

        List<OrderItem> orderItemList = order.getItems();
        addedItem.setProduct(product);
        addedItem.setSaleValue(price);
        addedItem.setAmount(amount);

        orderItemList.add(addedItem);
        order.setItems(orderItemList);

        repository.update(order);
        return addedItem;
    }

    @Override
    public OrderItem changeAmount(Order order, Product product, Integer amount) {

        //Validação de condição de Open
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new OrderException("Seu pedido não pode ser alterado!");
        }

        OrderItem itemToChange = null;
        for (OrderItem item : order.getItems()) {
            if (item.getProduct().equals(product)) {
                itemToChange = item;
                break;
            }
        }
        if (itemToChange != null) {
            itemToChange.setAmount(amount);
            repository.update(order);
        } else {
            throw new OrderException("Produto não encontrado");
        }

        return itemToChange;
    }

    @Override
    public void removeItem(Order order, Product product) {

        //Validação de condição de Open
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new OrderException("Seu pedido não pode ser alterado!");
        }

        OrderItem itemToRemove = null;
        for (OrderItem item : order.getItems()) {
            if (item.getProduct().equals(product)) {
                itemToRemove = item;
                break;
            }
        }

        order.getItems().remove(itemToRemove);


        repository.update(order);

    }
}

