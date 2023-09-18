package br.ada.customer.crud.usecases.impl;

import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderException extends RuntimeException {

    public OrderException(String message){

        super (message);
    }
}
