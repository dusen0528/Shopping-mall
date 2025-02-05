package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getAddress(String addressId) {
        return addressRepository.findById(addressId).orElseThrow(()-> new RuntimeException("Address not found"));
    }

    @Override
    public List<Address> getUserAddresses(String userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address getDefaultAddress(String userId) {
        return addressRepository.findDefaultAddress(userId).orElseThrow(()->new RuntimeException("Default address not found"));
    }

    @Override
    public void saveAddress(Address address) {
        if(addressRepository.countByUserId(address.getUserId()) == 0) {
            address.setDefault(true);
        }
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressRepository.update(address);
    }

    @Override
    public void deleteAddress(String addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public void setDefaultAddress(String addressId, String userId) {
        List<Address>addresses = addressRepository.findByUserId(userId);
        for(Address addr : addresses){
            if(addr.isDefault()){
                addr.setDefault(false);
                addressRepository.update(addr);
            }
        }

        Address address = getAddress(addressId);
        address.setDefault(true);
        addressRepository.update(address);

    }
}
