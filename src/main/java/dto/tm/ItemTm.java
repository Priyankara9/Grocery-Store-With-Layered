package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ItemTm extends RecursiveTreeObject<ItemTm> {
    String itemCode;
    String description;
    double unitPrice;
    int qty;
    JFXButton btn;

    public ItemTm(String itemCode, String description, double unitPrice, int qty, JFXButton btn) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.btn = btn;
    }

    public ItemTm() {


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

    public int getQty() {
        return qty;
    }

    public JFXButton getBtn() {
        return btn;
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

    public void setBtn(JFXButton btn) {
        this.btn = btn;
    }
}
