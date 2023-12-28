package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DbConnection;
import dto.ItemDto;
import dto.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {


    public BorderPane bdPane;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTextField txtSearch;
    public JFXTreeTableView<ItemTm> tblItem;
    public TreeTableColumn<ItemTm,String> colCode;
    public TreeTableColumn<ItemTm,String> colDesc;
    public TreeTableColumn<ItemTm,Double> colUnitPrice;
    public TreeTableColumn<ItemTm,Integer> colQty;
    public TreeTableColumn<ItemTm,JFXButton> colOption;
    private List<ItemDto> dbItemList=new ArrayList();
    ObservableList<ItemTm> iTmList= FXCollections.observableArrayList();

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        try {
            ResultSet rs= DbConnection.getInstance().getConnection()
                    .createStatement().executeQuery("select * from item");
            while (rs.next()){
                dbItemList.add(new ItemDto(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4)));

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadItemTable();
        //System.out.println("Retrieving data"+dbItemList);

        /*tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTexts((CustomerTm) newValue);
        });*/
    }

    /*private void setTexts(CustomerTm newValue) {
        if (newValue != null) {
            txtId.setEditable(false);
            txtId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }*/

    private void loadItemTable(){

        for (ItemDto dto:dbItemList) {
            JFXButton btn= new JFXButton("delete");
            ItemTm tm=new ItemTm(
                    dto.getItemCode(),
                    dto.getDescription(),
                    dto.getUnitPrice(),
                    dto.getQty(),
                    btn
            );
            btn.setOnAction(actionEvent -> {
                deleteItrm(tm);
            });
            iTmList.add(tm);

        }
        System.out.println(iTmList);
        TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(iTmList, RecursiveTreeObject::getChildren);
        tblItem.setRoot(treeItem);
        tblItem.setShowRoot(false);

    }

    private void deleteItrm(ItemTm tm) {
        boolean isDeleted=iTmList.remove(tm);
        if (isDeleted){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted").show();
            loadItemTable();
        }



    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) bdPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
