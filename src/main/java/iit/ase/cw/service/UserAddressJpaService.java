package iit.ase.cw.service;

import iit.ase.cw.model.entity.UserAddress;
import iit.ase.cw.model.entity.User;
import iit.ase.cw.platform.common.exception.ThaproError;
import iit.ase.cw.platform.common.exception.ThaproErrorBuilder;
import iit.ase.cw.platform.common.exception.ThaproNoDataFoundException;
import iit.ase.cw.platform.jpa.service.BaseThaproJpaService;
import iit.ase.cw.repository.UserAddressRepository;
import iit.ase.cw.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class UserAddressJpaService extends BaseThaproJpaService<UserAddress, Long> {

    private UserAddressRepository userAddressRepository;

    private UserRepository userRepository;

    public UserAddressJpaService(UserAddressRepository userAddressRepository, UserRepository userRepository)
            throws InstantiationException, IllegalAccessException {
        super(UserAddress.class);
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserAddressRepository get() {
        return userAddressRepository;
    }

    public List<UserAddress> findAllAddressOfUser(Long userId) {
        Optional<User> userEntityOptional =  userRepository.findById(userId);
        return userEntityOptional
                .map(userEntity -> userEntity.getAddressEntityList())
                .orElseThrow(() -> {
                    Function<Integer, ThaproError> function = x ->
                            ThaproError.builder()
                                    .errorCode(x.toString())
                                    .errorMessage("Test Error").build();

                    ThaproErrorBuilder eb = ThaproErrorBuilder.builder()
                            .addError(100, function);
                    List<ThaproError> errorMsgList = eb.getThaproErrorList();
                    return new ThaproNoDataFoundException("NO DATA FOUND", errorMsgList);
                });
    }
}
