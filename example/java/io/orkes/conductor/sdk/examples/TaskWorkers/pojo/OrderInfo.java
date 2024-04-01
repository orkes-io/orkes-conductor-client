package io.orkes.conductor.sdk.examples.TaskWorkers.pojo;

public class OrderInfo {
    private int quantity;
    private int skuPrice;


    public int getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(int skuPrice) {
        this.skuPrice = skuPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
