package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.model.OrderStatus;
import br.ada.customer.crud.usecases.INotifierdOrderUseCase;
import br.ada.customer.crud.usecases.IOrderPayUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderPayUseCaseImpl implements IOrderPayUseCase {

    private OrderRepository repository;
    private INotifierdOrderUseCase notifierEmail;
    private INotifierdOrderUseCase notifierSms;

    private IOrderPayUseCase paidOrder;

    public OrderPayUseCaseImpl(OrderRepository repository, INotifierdOrderUseCase notifierEmail, INotifierdOrderUseCase notifierSms) {
        this.repository = repository;
        this.notifierEmail = notifierEmail;
        this.notifierSms = notifierSms;
        this.paidOrder = paidOrder;
    }

    @Override
    public void pay(Order order) {
        if(order.getStatus()!= OrderStatus.PENDING_PAYMENT){
            throw new OrderException("Aguardando pagamento");
        }

        order.setStatus(OrderStatus.PAID);
        repository.update(order);
        notifierEmail.paid(order);
        notifierSms.paid(order);
        paidOrder.pay(order);

    }

}

