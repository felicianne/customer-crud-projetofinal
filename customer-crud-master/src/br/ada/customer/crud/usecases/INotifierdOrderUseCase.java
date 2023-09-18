package br.ada.customer.crud.usecases;

import br.ada.customer.crud.model.Order;

public interface INotifierdOrderUseCase {

    void paid(Order order);

    void shipping(Order order);

    void arrived(Order order);


}
