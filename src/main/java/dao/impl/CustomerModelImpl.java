package dao.impl;

import dto.CustomerDto;
import dao.CustomerModel;

import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public List<CustomerDto> allCustomers() {
        return null;
    }

    @Override
    public CustomerDto search() {
        return null;
    }
}
