package com.example.payments;

public class SafeCashAdapter implements  PaymentGateway {
    public SafeCashClient safeCashClient;
    public SafeCashAdapter(SafeCashClient safeCashClient) {
        this.safeCashClient=safeCashClient;
    }
    @Override
    public String charge(String customerId, int amountCents) {
        safeCashClient.createPayment(amountCents, customerId);
        return "SC#" + customerId + ":" + amountCents;
    }
}
