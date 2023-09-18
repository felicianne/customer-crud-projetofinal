package br.ada.customer.crud.integration.sms;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.usecases.INotifierdOrderUseCase;


public class OrderSmsNotifierImpl implements INotifierdOrderUseCase {

    private SendSms sendSms;

    public OrderSmsNotifierImpl(SendSms sendSms) {
    }

    @Override
    public void paid(Order order) {
        sendSms.send("69642563", order.getCustomer().getTelephone(), "O seu pedido foi pago!");

    }

    @Override
    public void shipping(Order order) {
        sendSms.send("69642563", order.getCustomer().getTelephone(), "O seu pedido foi despachado, logo logo ele chega aí!!");

    }

    @Override
    public void arrived(Order order) {
        sendSms.send("69642563", order.getCustomer().getTelephone(), "O seu pedido chegou!");

    }
}
