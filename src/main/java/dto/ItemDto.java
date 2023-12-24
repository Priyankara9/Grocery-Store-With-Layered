package dto;

import com.jfoenix.controls.JFXButton;

public class ItemDto {
    String itemCode;
    String description;
    double unitPrice;
    int qty;


    public ItemDto(String itemCode, String description, double unitPrice, int qty) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;

    }

    public ItemDto() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getDescription() {
        return description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public int getQty() {
        return qty;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
