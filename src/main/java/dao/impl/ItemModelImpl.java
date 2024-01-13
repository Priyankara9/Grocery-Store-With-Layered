package dao.impl;

import db.DbConnection;
import dto.ItemDto;
import dao.ItemModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    String ItemCode;
    String description;
    double unitPrice;
    int qty;
    @Override
    public boolean saveItem(ItemDto dto) {
        return false;
    }

    @Override
    public boolean updateItem(ItemDto dto) {
        return false;
    }

    @Override
    public boolean deleteItem(String id) {
        return false;
    }

    @Override
    public List<ItemDto> allItem() throws SQLException, ClassNotFoundException {
        List<ItemDto> itmList= new ArrayList<>();

        String sql ="Select * from item";
        Connection connection= DbConnection.getInstance().getConnection();
        PreparedStatement stm=connection.prepareStatement(sql);
        ResultSet rs= stm.executeQuery();
        while (rs.next()){


            itmList.add(new ItemDto(rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4)));
        }

        return itmList;
    }
}
