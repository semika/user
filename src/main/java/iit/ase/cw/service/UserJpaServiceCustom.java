package iit.ase.cw.service;

import iit.ase.cw.model.entity.User;
import iit.ase.cw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserJpaServiceCustom {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {

        List<User> userList = userRepository.findAll();

        //Long running transaction
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
