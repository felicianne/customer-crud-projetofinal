package br.ada.customer.crud.integration.email;

import br.ada.customer.crud.model.Order;
import br.ada.customer.crud.usecases.INotifierdOrderUseCase;


public class OrderEmailNotifierImpl implements INotifierdOrderUseCase {

    private SendEmail sendEmail;

    public OrderEmailNotifierImpl(SendEmail sendemail) {
        this.sendEmail = sendEmail;
    }


    @Override
    public void paid(Order order) {
        SendEmail.send("comunicacao@ecommerce.com", order.getCustomer().getEmail(), "Seu pedido foi pago!");
    }

    @Override
    public void shipping(Order order) {
        SendEmail.send("comunicacao@ecommerce.com", order.getCustomer().getEmail(), "Seu pedido foi despachado, logo logo ele chega aí!!");
    }


    @Override
    public void arrived(Order order) {
        SendEmail.send("comunicacao@ecommerce.com", order.getCustomer().getEmail(), "Seu pedido chegou!");

    }

}
