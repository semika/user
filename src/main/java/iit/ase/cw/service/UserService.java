package iit.ase.cw.service;

import iit.ase.cw.model.converter.UserConverter;
import iit.ase.cw.model.entity.UserEntity;
import iit.ase.cw.model.model.UserResource;
import iit.ase.cw.platform.common.service.ThaproResponseHelper;
import iit.ase.cw.platform.common.type.ThaproResponse;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.jpa.service.BaseThaproResourceService;
import iit.ase.cw.platform.common.converter.ToCollectionConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseThaproResourceService<UserResource, UserEntity, Long> {

	private UserJpaService jpaService;
	private UserConverter converter;

	public UserService(UserConverter converter, UserJpaService jpaService) {
		super(converter, jpaService, true);
		this.jpaService = jpaService;
		this.converter = converter;
	}

	public ThaproResponse<UserResource> findUserByFirstName(ThaproSearchFilter thaproSearchFilter, String firstName) {
		UserEntity userEntity = jpaService.findUserByFirstName(firstName);
		UserResource userResource = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResourceResponse(userResource);
	}

	public ThaproResponse<UserResource> findUserByLastName(ThaproSearchFilter thaproSearchFilter, String lastName) {
		UserEntity userEntity = jpaService.findUserByLastName(lastName);
		UserResource userResource = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResourceResponse(userResource);
	}

	public ThaproResponse<UserResource> findByCriteria(UserResource userResource, ThaproSearchFilter thaproSearchFilter) {
		UserEntity userEntity = jpaService.findByCriteria(userResource, thaproSearchFilter);
		UserResource userResourceResponse = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResourceResponse(userResourceResponse);
	}

	public ThaproResponse<UserResource> findBySpecification(UserResource userResource, ThaproSearchFilter thaproSearchFilter) {
		Page<UserEntity> userEntityList = jpaService.findByCriteriaSpecification(userResource, thaproSearchFilter);

		List<UserResource> userResources = userEntityList.stream().map((UserEntity userEntity) ->
			this.converter.to(userEntity, thaproSearchFilter)).collect(Collectors.toList());

		//UserResource userResourceResponse = this.converter.to(userEntity, thaproSearchFilter);
		return ThaproResponseHelper.createResponse(userResources);
	}

	public ThaproResponse<UserResource> findAllRowEntitiesBySpecification(UserResource userResource, ThaproSearchFilter thaproSearchFilter) {
		List<UserEntity> userEntityList = jpaService.findAllRowEntitiesBySpecification(userResource, thaproSearchFilter);

		ToCollectionConverter<UserEntity, UserResource> toCollectionConverter = ToCollectionConverter.of(this.converter);
		List<UserResource> userResourceList = toCollectionConverter.to(userEntityList, thaproSearchFilter);
		return ThaproResponseHelper.createResponseByResourceList(userResourceList);

		// using stream API.
		//userEntityList.stream().map(userEntity -> this.converter.to(userEntity, thaproSearchFilter)).collect(Collectors.toList());
	}

	public ThaproResponse<UserResource> findById(Long id, ThaproSearchFilter searchFilter) {
		Optional<UserResource> userResourceOptional = jpaService.findById(id, this.converter, searchFilter);
		return ThaproResponseHelper.createResponse(userResourceOptional, searchFilter);
	}
}
