package iit.ase.cw.repository;

import iit.ase.cw.model.entity.UserEntity;
import iit.ase.cw.platform.jpa.repository.ThaproJpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends ThaproJpaRepository<UserEntity, Long> {

    //Method query example
    UserEntity findUserEntityByFirstName(String firstName);

    //Named query example
    UserEntity findByLastName(String lastName);

    //JPQL example
    @Query("SELECT user FROM UserEntity user WHERE user.firstName = :firstName AND user.lastName = :lastName")
    UserEntity findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
