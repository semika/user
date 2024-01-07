package iit.ase.cw.service;

import iit.ase.cw.model.entity.User;
import iit.ase.cw.model.model.UserDto;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.jpa.service.BaseThaproJpaService;
import iit.ase.cw.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserJpaService extends BaseThaproJpaService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    public UserJpaService() throws InstantiationException, IllegalAccessException {
        super(User.class);
    }

    @Override
    public UserRepository get() {
        return userRepository;
    }

    public User findUserByFirstName(String firstName) {
        return userRepository.findUserEntityByFirstName(firstName);
    }

    public User findUserByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public User findByCriteria(UserDto userResource, ThaproSearchFilter searchFilter) {
        User userEntity = userRepository.findByFirstNameAndLastName(userResource.getFirstName(),
            userResource.getLastName());
        return userEntity;
    }

    public Page<User> findByCriteriaSpecification(UserDto userResource, ThaproSearchFilter searchFilter) {

        Specification<User> firstNameSpecification =
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("firstName"), userResource.getFirstName());

        Specification<User> lastNameSpecification =
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("lastName"), userResource.getLastName());

        PageRequest pageRequest = PageRequest.of(searchFilter.getPageNo(), 10);

        return userRepository.findAll(firstNameSpecification.and(lastNameSpecification), pageRequest);
    }

    public List<User> findAllRowEntitiesBySpecification(UserDto userResource, ThaproSearchFilter searchFilter) {

        Specification<User> firstNameSpecification = ((root, criteriaQuery, criteriaBuilder)
            -> criteriaBuilder.equal(root.get("firstName"), userResource.getFirstName()));

        Specification<User> lastNameSpecification = ((root, criteriaQuery, criteriaBuilder)
            -> criteriaBuilder.equal(root.get("lastName"), userResource.getLastName()));

        Specification<User> filterSpecification = firstNameSpecification.and(lastNameSpecification);
        return findAll(filterSpecification);
    }
}
