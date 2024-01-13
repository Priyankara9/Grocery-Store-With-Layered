package dao;

import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    boolean saveItem(ItemDto dto);
    boolean updateItem(ItemDto dto);
    boolean deleteItem(String id);
    List<ItemDto> allItem() throws SQLException, ClassNotFoundException;
}
