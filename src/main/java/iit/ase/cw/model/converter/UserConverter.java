package iit.ase.cw.model.converter;

import iit.ase.cw.model.model.UserResource;
import iit.ase.cw.model.entity.UserEntity;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.common.converter.BiConverterService;

import org.springframework.stereotype.Service;

@Service
public class UserConverter implements BiConverterService<UserEntity, UserResource> {

    @Override
    public UserEntity from(UserResource to, ThaproSearchFilter searchFilter) {
        return UserEntity.builder()
            .firstName(to.getFirstName())
            .lastName(to.getLastName())
                .password(to.getPassword())
            .build();
    }

    @Override
    public UserResource to(UserEntity from, ThaproSearchFilter searchFilter) {
        UserResource userResource = UserResource.builder()
            .id(from.getId())
            .firstName(from.getFirstName())
            .lastName(from.getLastName())
                .password(from.getPassword())
            .build();
        userResource.setOrganizationId(from.getOrganizationId());
        return userResource;
    }
}
