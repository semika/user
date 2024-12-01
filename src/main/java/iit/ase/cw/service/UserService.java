package iit.ase.cw.service;

import iit.ase.cw.model.converter.UserConverter;
import iit.ase.cw.model.entity.User;
import iit.ase.cw.model.model.UserDto;
import iit.ase.cw.platform.common.service.ThaproResponseHelper;
import iit.ase.cw.platform.common.type.ThaproResponse;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.common.converter.ToCollectionConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import iit.ase.cw.platform.jpa.service.BaseThaproService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseThaproService<UserDto, User, Long> {

	private UserJpaService jpaService;
	private UserConverter converter;

	public UserService(UserConverter converter,
					   UserJpaService jpaService) {
		super(converter, jpaService, true);
		this.jpaService = jpaService;
		this.converter = converter;
	}

	public ThaproResponse<UserDto> findUserByFirstName(ThaproSearchFilter thaproSearchFilter, String firstName) {
		User userEntity = jpaService.findUserByFirstName(firstName);
		UserDto userResource = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResourceResponse(userResource);
	}

	public ThaproResponse<UserDto> findUserByLastName(ThaproSearchFilter thaproSearchFilter, String lastName) {
		User userEntity = jpaService.findUserByLastName(lastName);
		UserDto userResource = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResourceResponse(userResource);
	}

	public ThaproResponse<UserDto> findByCriteria(UserDto userResource, ThaproSearchFilter thaproSearchFilter) {
		User userEntity = jpaService.findByCriteria(userResource, thaproSearchFilter);
		UserDto userResourceResponse = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResourceResponse(userResourceResponse);
	}

	public ThaproResponse<UserDto> findBySpecification(UserDto userResource, ThaproSearchFilter thaproSearchFilter) {
		Page<User> userEntityList = jpaService.findByCriteriaSpecification(userResource, thaproSearchFilter);

		List<UserDto> userResources = userEntityList.stream().map((User userEntity) ->
			this.converter.to(userEntity, thaproSearchFilter)).collect(Collectors.toList());

		//UserResource userResourceResponse = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResponse(userResources);
	}

	public ThaproResponse<UserDto> findAllRowEntitiesBySpecification(UserDto userResource, ThaproSearchFilter thaproSearchFilter) {
		List<User> userEntityList = jpaService.findAllRowEntitiesBySpecification(userResource, thaproSearchFilter);

		ToCollectionConverter<User, UserDto> toCollectionConverter = ToCollectionConverter.of(this.converter);
		List<UserDto> userResourceList = toCollectionConverter.to(userEntityList, thaproSearchFilter);
		return ThaproResponseHelper.createResponseByResourceList(userResourceList);

		// using stream API.
		//userEntityList.stream().map(userEntity -> this.converter.to(userEntity, thaproSearchFilter)).collect(Collectors.toList());
	}

	public ThaproResponse<UserDto> findById(Long id, ThaproSearchFilter searchFilter) {
		Optional<UserDto> userResourceOptional = jpaService.findById(id, this.converter, searchFilter);
		return ThaproResponseHelper.createResponse(userResourceOptional, searchFilter);
	}
}
