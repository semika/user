package iit.ase.cw.model.converter;

import iit.ase.cw.model.entity.UserAddress;
import iit.ase.cw.model.entity.User;
import iit.ase.cw.model.model.UserAddressDto;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.common.converter.BiConverterService;
import org.springframework.stereotype.Service;

@Service
public class UserAddressConverter implements BiConverterService<UserAddress, UserAddressDto> {

    @Override
    public UserAddress from(UserAddressDto to, ThaproSearchFilter searchFilter) {

        UserAddress userAddressEntity = UserAddress.builder()
                .street(to.getState())
                .city(to.getCity())
                .state(to.getState())
                .zipCode(to.getZipCode()).build();

        User userEntity = User.builder().id(to.getUserId()).build();

        userAddressEntity.setUserEntity(userEntity);
        return userAddressEntity;
    }

    @Override
    public UserAddressDto to(UserAddress from, ThaproSearchFilter searchFilter) {

        return UserAddressDto.builder()
                .street(from.getStreet())
                .city(from.getCity())
                .state(from.getState())
                .zipCode(from.getZipCode())
                .userId(from.getUserEntity().getId()).id(from.getId()).build();
    }
}
