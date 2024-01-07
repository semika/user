package iit.ase.cw.service;

import iit.ase.cw.model.converter.UserAddressConverter;
import iit.ase.cw.model.entity.UserAddress;
import iit.ase.cw.model.model.UserAddressDto;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.common.converter.ToCollectionConverter;
import iit.ase.cw.platform.common.service.ThaproResponseHelper;
import iit.ase.cw.platform.common.type.ThaproResponse;
import iit.ase.cw.platform.jpa.service.BaseThaproDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressService extends BaseThaproDtoService<UserAddressDto, UserAddress, Long> {

	private UserAddressJpaService userAddressJpaService;
	private UserAddressConverter userAddressConverter;

	public UserAddressService(UserAddressJpaService userAddressJpaService,
                              UserAddressConverter userAddressConverter) {
		super(userAddressConverter, userAddressJpaService, true);
		this.userAddressJpaService = userAddressJpaService;
		this.userAddressConverter = userAddressConverter;
	}

	public ThaproResponse<UserAddressDto> findAllAddressByUser(Long userId, ThaproSearchFilter thaproSearchFilter) {

		List<UserAddress> userAddressEntities = userAddressJpaService.findAllAddressOfUser(userId);

		ToCollectionConverter<UserAddress, UserAddressDto> toCollectionConverter
				= ToCollectionConverter.of(this.userAddressConverter);
		List<UserAddressDto> userResourceList = toCollectionConverter.to(userAddressEntities, thaproSearchFilter);
		return ThaproResponseHelper.createResponseByResourceList(userResourceList);
	}
}
