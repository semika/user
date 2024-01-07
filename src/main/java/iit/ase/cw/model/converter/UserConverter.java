package iit.ase.cw.model.converter;

import iit.ase.cw.model.model.UserDto;
import iit.ase.cw.model.entity.User;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.common.converter.BiConverterService;

import org.springframework.stereotype.Service;

@Service
public class UserConverter implements BiConverterService<User, UserDto> {

    @Override
    public User from(UserDto to, ThaproSearchFilter searchFilter) {
        return User.builder()
            .firstName(to.getFirstName())
            .lastName(to.getLastName())
                .password(to.getPassword())
            .build();
    }

    @Override
    public UserDto to(User from, ThaproSearchFilter searchFilter) {
        UserDto userResource = UserDto.builder()
            .id(from.getId())
            .firstName(from.getFirstName())
            .lastName(from.getLastName())
                .password(from.getPassword())
            .build();
        userResource.setOrganizationId(from.getOrganizationId());
        return userResource;
    }
}
