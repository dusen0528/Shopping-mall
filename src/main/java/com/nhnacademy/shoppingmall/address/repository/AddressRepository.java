package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findById(String addressId);
    List<Address> findByUserId(String userId);
    Optional<Address> findDefaultAddress(String userId); // 사용자 기본 배송지 조회

    int save(Address address);
    int update(Address address);
    int deleteById(String addressId);
    int countByUserId(String userId); // 사용자당 배송지 수 제한

}
