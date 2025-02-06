package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public Optional<Address> findById(String addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM addresses WHERE address_id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, addressId);
            try(ResultSet rs = preparedStatement.executeQuery()
                    ){
                if(rs.next()){
                    return Optional.of(new Address(rs.getString("address_id"),
                            rs.getString("user_id"),
                            rs.getString("recipient_name"),
                            rs.getString("recipient_phone"),
                            rs.getString("address"),
                            rs.getBoolean("is_default")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    // 특정 userId를 가진 모든 배송지 목록을 List로 반환
    @Override
    public List<Address> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM addresses WHERE user_id=?";
        List<Address> addresses = new ArrayList<>();

        log.debug("sql: {}", sql);
        log.debug("userId: {}", userId);
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, userId);
            try(
                    ResultSet rs = preparedStatement.executeQuery()
                    ){
                while(rs.next()){
                    addresses.add(new Address(
                            rs.getString("address_id"),
                            rs.getString("user_id"),
                            rs.getString("recipient_name"),
                            rs.getString("recipient_phone"),
                            rs.getString("address"),
                            rs.getBoolean("is_default")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addresses;
    }

    @Override
    public Optional<Address> findDefaultAddress(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM addresses WEHRE user_id=? AND is_default=true";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    return Optional.of(new Address(
                            rs.getString("address_id"),
                            rs.getString("user_id"),
                            rs.getString("recipient_name"),
                            rs.getString("recipient_phone"),
                            rs.getString("address"),
                            rs.getBoolean("is_default")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO addresses (address_id, user_id, recipient_name, recipient_phone, address, is_default) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        log.debug("sql:{}", sql);
        log.debug("parameters: addressId={}, userId={}, recipientName={}, recipientPhone={}, address={}, isDefault={}",
                address.getAddressId(), address.getUserId(), address.getRecipientName(),
                address.getRecipientPhone(), address.getAddress(), address.isDefault());

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, address.getAddressId());
            preparedStatement.setString(2, address.getUserId());
            preparedStatement.setString(3, address.getRecipientName());
            preparedStatement.setString(4, address.getRecipientPhone());
            preparedStatement.setString(5, address.getAddress());
            preparedStatement.setBoolean(6, address.isDefault());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE addresses SET recipient_name=?, recipient_phone=?, address=?, is_default=? WHERE address_id=?";
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setString(1, address.getRecipientName());
            preparedStatement.setString(2, address.getRecipientPhone());
            preparedStatement.setString(3, address.getAddress());
            preparedStatement.setBoolean(4, address.isDefault());
            preparedStatement.setString(5, address.getAddressId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String addressId) {

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM addresses WHERE address_id=?";

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setString(1, addressId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM addresses WHERE user_id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, userId);

            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
