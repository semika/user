package iit.ase.cw.repository;

import iit.ase.cw.model.entity.User;
import iit.ase.cw.platform.jpa.repository.ThaproJpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends ThaproJpaRepository<User, Long> {

    //Method query example
    User findUserEntityByFirstName(String firstName);

    //Named query example
    User findByLastName(String lastName);

    //JPQL example
    @Query("SELECT user FROM User user WHERE user.firstName = :firstName AND user.lastName = :lastName")
    User findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
