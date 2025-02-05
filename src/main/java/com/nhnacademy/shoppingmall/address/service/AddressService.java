package com.nhnacademy.shoppingmall.address.service;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;

public interface AddressService {
    Address getAddress(String addressId);
    List<Address> getUserAddresses(String userId);
    Address getDefaultAddress(String userId);

    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(String addressId);
    void setDefaultAddress(String addressId, String userId);

}
