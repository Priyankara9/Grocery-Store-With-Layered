package controller;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DbConnection;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {
    public JFXComboBox cmbCustId;
    public JFXComboBox cmbCode;
    public JFXTextField txtCustName;
    public JFXTextField txtDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTreeTableView tblItem;
    public TreeTableColumn colCode;
    public TreeTableColumn colDesc;
    public TreeTableColumn colQty;
    public TreeTableColumn colAmount;
    public TreeTableColumn colOption;
    public Label lblTotal;
    public Label lblOrderId;
    private List<CustomerDto> customers=new ArrayList<>();
    private List<ItemDto> items=new ArrayList<>();
    private double total=0;
    private ObservableList<OrderTm> tmList=FXCollections.observableArrayList();
    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        try {
            try (ResultSet rs = DbConnection.getInstance().getConnection()
                    .createStatement().executeQuery("select * from item")) {
                while (rs.next()) {
                    items.add(new ItemDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getInt(4)));

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            ResultSet rs= DbConnection.getInstance().getConnection()
                    .createStatement().executeQuery("select * from customer");
            while (rs.next()){
                customers.add(new CustomerDto(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4)));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        loadCustomerIds();
        loadItemCodes();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (CustomerDto dto:customers) {
                if (dto.getId().equals(newValue.toString())){
                    txtCustName.setText(dto.getName());
                }
            }
        });

        cmbCode.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (ItemDto dto:items) {
                if (dto.getItemCode().equals(newValue.toString())){
                    txtDesc.setText(dto.getDescription());
                    txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        });

    }
    private void loadItemCodes() {
        ObservableList list = FXCollections.observableArrayList();

        for (ItemDto dto:items) {
            list.add(dto.getItemCode());
        }

        cmbCode.setItems(list);
    }

    private void loadCustomerIds() {
        ObservableList list = FXCollections.observableArrayList();

        for (CustomerDto dto:customers) {
            list.add(dto.getId());
        }

        cmbCustId.setItems(list);
    }

    public void addToCartButtonOnAction(ActionEvent actionEvent) {
        JFXButton btn =new JFXButton("delete");
        OrderTm orderTm=new OrderTm(
                cmbCode.getValue().toString(),
                txtDesc.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtUnitPrice.getText())*Integer.parseInt(txtQty.getText()),
                btn
        );
        btn.setOnAction(actionevent -> {
            tmList.remove(orderTm);
            total-=orderTm.getAmount();
            lblTotal.setText(String.format("%.2f",total));
            tblItem.refresh();
        });
        boolean isExist = false;
        for (OrderTm order:tmList) {
            if (order.getCode().equals(orderTm.getCode())){
                order.setQty(order.getQty()+orderTm.getQty());
                order.setAmount(order.getAmount()+orderTm.getAmount());
                isExist = true;
                total+= orderTm.getAmount();
            }
        }
        if (!isExist){
            tmList.add(orderTm);
            total+=orderTm.getAmount();
        }

        lblTotal.setText(String.format("%.2f",total));

        TreeItem treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblItem.setRoot(treeItem);
        tblItem.setShowRoot(false);
    }






    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
    }

    public void BackButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cmbCustId.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
