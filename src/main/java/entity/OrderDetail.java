package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetail {
    private String orderId;
    private  String itemCode;
    private double unitPrice;
    private int qty;
}
