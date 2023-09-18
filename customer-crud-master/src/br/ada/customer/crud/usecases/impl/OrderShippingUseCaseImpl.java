package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.IOrderShippingUseCase;
import br.ada.customer.crud.usecases.IShippingNotifierUseCase;
import br.ada.customer.crud.usecases.INotifierdOrderUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderShippingUseCaseImpl implements IOrderShippingUseCase {

    private OrderRepository repository;
    private IShippingNotifierUseCase notifierUseCase;

    public OrderShippingUseCaseImpl(
            OrderRepository orderRepository,
            IShippingNotifierUseCase notifierUseCase
    ) {
        this.repository = orderRepository;
        this.notifierUseCase = notifierUseCase;
    }

    public OrderShippingUseCaseImpl(OrderRepository repository, INotifierdOrderUseCase notifierOrderSendEmail, INotifierdOrderUseCase notifierOrderSendSms) {
    }

    @Override
    public void shipping(Order order) {
        if (order.getStatus() != OrderStatus.PAID) {
            throw new OrderException("Pedido ainda não pago.");
        }
        order.setStatus(OrderStatus.FINISH);
        repository.save(order);
        notifierUseCase.notify(order);
    }

}
