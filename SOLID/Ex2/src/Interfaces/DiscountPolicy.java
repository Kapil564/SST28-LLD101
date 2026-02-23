package Interfaces;

public interface DiscountPolicy {
    double getDiscountAmount(String customerType, double subtotal, int distinctLines);
}
