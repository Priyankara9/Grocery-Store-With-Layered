package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
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
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setTexts((CustomerTm) newValue);
        });

    }

    private void setTexts(CustomerTm newValue) {
        if (newValue != null) {
            txtId.setEditable(false);
            txtId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> ctm= FXCollections.observableArrayList();
        for (CustomerDto dto:dbclist) {
            JFXButton btn= new JFXButton("delete");
            CustomerTm tm=new CustomerTm(
                    dto.getId(),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getSalary(),
                    btn
            );
            ctm.add(tm);

        }
        tblCustomer.setItems(null);
        tblCustomer.setItems(ctm);

    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (!isDuplicate(txtId.getText())){
            if (!isMissing()) {
                CustomerDto dtoSave = new CustomerDto(
                        txtId.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                );

                dbclist.add(dtoSave);
                loadCustomerTable();
                clearFields();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        }

    }

    private boolean isMissing() {
        if (txtId.getText().isEmpty()||(txtId.getText()==null)){
            new Alert(Alert.AlertType.ERROR,"CustomerId Required").show();
            return true;
        }
        if (txtName.getText().isEmpty()||(txtName.getText()==null)){
            new Alert(Alert.AlertType.ERROR,"Name Required").show();
            return true;
        }
        if (txtAddress.getText().isEmpty()||(txtId.getText()==null)){
            new Alert(Alert.AlertType.ERROR,"Address Required").show();
            return true;
        }
        if (txtSalary.getText().isEmpty()||(txtId.getText()==null)){
            new Alert(Alert.AlertType.ERROR,"Salary Required").show();
            return true;
        }
        return false;
    }

    private boolean isDuplicate(String id) {
        for (CustomerDto dto:dbclist) {
            if (dto.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    public void reloadButtonOnAction(ActionEvent actionEvent) {
        loadCustomerTable();
        clearFields();
        tblCustomer.refresh();
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        CustomerDto dto =new CustomerDto(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())

        );
        for (int i=0;i<dbclist.size();i++) {
            if(dbclist.get(i).getId().equals(dto.getId())){
                dbclist.set(i,dto);
            }
        }
        loadCustomerTable();
    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtId.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void reportButtonOnAction(ActionEvent actionEvent) {
    }
}
