package com.example.task02;

public class DiscountBill extends Bill {
    private final int discount;

    public DiscountBill(int discount) {
        this.discount = discount;
    }

    @Override
    public long getPrice() {
        long price = super.getPrice();

        return (price * (100 - discount)) / 100;
    }

    public int getDiscount() {
        return discount;
    }

    public long getAbsoluteDiscount() {
        return super.getPrice() - this.getPrice();
    }
}
