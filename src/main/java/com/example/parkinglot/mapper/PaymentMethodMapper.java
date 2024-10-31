package com.example.parkinglot.mapper;

import com.example.parkinglot.entity.PaymentMethod;
import com.example.parkinglot.entity.User;
import com.example.parkinglot.dto.PaymentMethodDTO;
import com.example.parkinglot.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper extends EntityMapper<PaymentMethodDTO, PaymentMethod> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    PaymentMethodDTO toDto(PaymentMethod s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
