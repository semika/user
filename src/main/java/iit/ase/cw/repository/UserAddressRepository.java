package iit.ase.cw.repository;

import iit.ase.cw.model.entity.UserAddress;
import iit.ase.cw.model.entity.User;
import iit.ase.cw.platform.jpa.repository.ThaproJpaRepository;

import java.util.List;

public interface UserAddressRepository extends ThaproJpaRepository<UserAddress, Long> {
    List<UserAddress> findUserAddressEntitiesByUserEntity(User userEntity);
}
