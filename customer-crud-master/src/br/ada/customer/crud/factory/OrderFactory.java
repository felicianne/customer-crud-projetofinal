package br.ada.customer.crud.factory;


import br.ada.customer.crud.integration.database.MemoryDatabase;
import br.ada.customer.crud.integration.email.OrderEmailNotifierImpl;
import br.ada.customer.crud.integration.email.SendEmail;
import br.ada.customer.crud.integration.memoryrepository.OrderEntityMerge;
import br.ada.customer.crud.integration.memoryrepository.OrderMemoryRepositoryImpl;
import br.ada.customer.crud.integration.sms.OrderSmsNotifierImpl;
import br.ada.customer.crud.integration.sms.SendSms;
import br.ada.customer.crud.usecases.ICreateOrderUseCase;
import br.ada.customer.crud.usecases.IOrderItemUseCase;
import br.ada.customer.crud.usecases.IOrderPlaceUseCase;
import br.ada.customer.crud.usecases.IOrderShippingUseCase;
import br.ada.customer.crud.usecases.impl.*;
import br.ada.customer.crud.usecases.INotifierdOrderUseCase;
import br.ada.customer.crud.usecases.IOrderPayUseCase;
import br.ada.customer.crud.usecases.repository.OrderRepository;

public class OrderFactory {

    public static ICreateOrderUseCase createUseCase() {
        return new CreateOrderUseCaseImpl(
                createRepository(),
                CustomerFactory.createRepository()
        );
    }

    public static IOrderItemUseCase orderItemUseCase() {
        return new OrderItemUseCaseImpl(
                createRepository()
        );
    }

    public static IOrderPayUseCase payOrderUseCase() {
        return new OrderPayUseCaseImpl(
                createRepository(),
                OrderFactory.createNotifierOrderSendEmail(),
                OrderFactory.createNotifierOrderSendSms()
        );

    }

    public static IOrderPlaceUseCase placeOrderUseCase() {
        return new OrderPlaceUseCaseImpl(
                createRepository(),
                OrderFactory.createNotifierOrderSendEmail(),
                OrderFactory.createNotifierOrderSendSms()
        );
    }

    public static IOrderShippingUseCase shippingUseCase() {
        return new OrderShippingUseCaseImpl(
                createRepository(),
                OrderFactory.createNotifierOrderSendEmail(),
                OrderFactory.createNotifierOrderSendSms()
        );
    }

    public static OrderRepository createRepository() {
        return new OrderMemoryRepositoryImpl(
                MemoryDatabase.getInstance(),
                new OrderEntityMerge(MemoryDatabase.getInstance())
        );
    }

    public static INotifierdOrderUseCase createNotifierOrderSendEmail() {
        return new OrderEmailNotifierImpl(new SendEmail()
        );
    }

    public static INotifierdOrderUseCase createNotifierOrderSendSms() {
        return new OrderSmsNotifierImpl(new SendSms()
        );
    }
}








