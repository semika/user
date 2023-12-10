package iit.ase.cw.service;

import iit.ase.cw.model.entity.UserEntity;
import iit.ase.cw.model.model.UserResource;
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
public class UserJpaService extends BaseThaproJpaService<UserEntity, Long> {

    @Autowired
    private UserRepository userRepository;

    public UserJpaService() throws InstantiationException, IllegalAccessException {
        super(UserEntity.class);
    }

    @Override
    public UserRepository get() {
        return userRepository;
    }

    public UserEntity findUserByFirstName(String firstName) {
        return userRepository.findUserEntityByFirstName(firstName);
    }

    public UserEntity findUserByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public UserEntity findByCriteria(UserResource userResource, ThaproSearchFilter searchFilter) {
        UserEntity userEntity = userRepository.findByFirstNameAndLastName(userResource.getFirstName(),
            userResource.getLastName());
        return userEntity;
    }

    public Page<UserEntity> findByCriteriaSpecification(UserResource userResource, ThaproSearchFilter searchFilter) {

        Specification<UserEntity> firstNameSpecification =
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("firstName"), userResource.getFirstName());

        Specification<UserEntity> lastNameSpecification =
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("lastName"), userResource.getLastName());

        PageRequest pageRequest = PageRequest.of(searchFilter.getPageNo(), 10);

        return userRepository.findAll(firstNameSpecification.and(lastNameSpecification), pageRequest);
    }

    public List<UserEntity> findAllRowEntitiesBySpecification(UserResource userResource, ThaproSearchFilter searchFilter) {

        Specification<UserEntity> firstNameSpecification = ((root, criteriaQuery, criteriaBuilder)
            -> criteriaBuilder.equal(root.get("firstName"), userResource.getFirstName()));

        Specification<UserEntity> lastNameSpecification = ((root, criteriaQuery, criteriaBuilder)
            -> criteriaBuilder.equal(root.get("lastName"), userResource.getLastName()));

        Specification<UserEntity> filterSpecification = firstNameSpecification.and(lastNameSpecification);
        return findAll(filterSpecification);
    }
}
