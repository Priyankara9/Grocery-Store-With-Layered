package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public JFXButton backButtonId;
    List<CustomerDto> dbclist =new ArrayList<>();
    public void initialize(){
        ResultSet rs= null;
        try {
            rs = DbConnection.getInstance().getConnection()
                    .createStatement().executeQuery("select * from customer");
            while (rs.next()){
                dbclist.add(new CustomerDto(
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
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory("btn"));



        loadCustomerTable();

    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> ctm= FXCollections.observableArrayList();
        for (CustomerDto dto:dbclist) {
            JFXButton btn= new JFXButton("delete");
            CustomerTm tm=new CustomerTm(dto.getId(),dto.getName(), dto.getAddress(), dto.getSalary(), btn);
            ctm.add(tm);

        }
        tblCustomer.setItems(null);
        tblCustomer.setItems(ctm);

    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
    }

    public void reloadButtonOnAction(ActionEvent actionEvent) {
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
    }

    public void backButtonOnAction(ActionEvent actionEvent) {
    }

    public void reportButtonOnAction(ActionEvent actionEvent) {
    }
}
