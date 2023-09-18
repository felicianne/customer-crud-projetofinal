package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderItem;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.IOrderPlaceUseCase;
import br.ada.customer.crud.usecases.INotifierdOrderUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

import java.math.BigDecimal;

public class OrderPlaceUseCaseImpl implements IOrderPlaceUseCase {

    private OrderRepository repository;
    private INotifierdOrderUseCase notifierEmail;
    private  INotifierdOrderUseCase notifierSms;

    public OrderPlaceUseCaseImpl(OrderRepository repository, INotifierdOrderUseCase notifierEmail, INotifierdOrderUseCase notifierSms) {
        this.repository = repository;
        this.notifierEmail = notifierEmail;
        this.notifierSms = notifierSms;
    }

    @Override
    public void placeOrder(Order order) {
        if (order.getStatus() != OrderStatus.OPEN) {
            throw new OrderException("Aguardando não está aberto");
        }

        BigDecimal sum = BigDecimal.ZERO;

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new OrderException("Não tem item selecionado");
        }
        for (OrderItem item : order.getItems()) {
            sum = sum.add(item.getSaleValue());
        }
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderException("Você deve selecionar algum produto");
        }

        order.setStatus(OrderStatus.PENDING_PAYMENT);
        notifierEmail.paid(order);
        notifierSms.paid(order);

    }
}
