package iit.ase.cw.controller;

import iit.ase.cw.model.model.UserAddressDto;
import iit.ase.cw.platform.common.context.model.SearchFilter;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.platform.common.controller.BaseThaproController;
import iit.ase.cw.platform.common.type.ThaproResponse;
import iit.ase.cw.service.UserAddressJpaService;
import iit.ase.cw.service.UserAddressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/{userId}/address")
public class UserAddressController extends BaseThaproController<UserAddressDto, Long> {

	private UserAddressService userAddressService;

	private UserAddressJpaService userAddressJpaService;

	public UserAddressController(UserAddressService userAddressService, UserAddressJpaService userAddressJpaService) {
		super(userAddressService);
		this.userAddressJpaService = userAddressJpaService;
		this.userAddressService = userAddressService;
	}

	@PutMapping()
	public void updateUser(@RequestBody UserAddressDto userAddressResource,
						   @SearchFilter ThaproSearchFilter searchFilter) {
		userAddressService.save(userAddressResource, searchFilter);
	}

	@GetMapping("/all")
	public ThaproResponse<UserAddressDto> findAllAddressByUser(@SearchFilter ThaproSearchFilter searchFilter,
															   @PathVariable Long userId) {
		//return userService.findUserByFirstName(searchFilter, firstName);
		return userAddressService.findAllAddressByUser(userId, searchFilter);
	}
//
//	@GetMapping("/byLastName")
//	public ThaproResponse<UserResource> findUserByLastName(@SearchFilter ThaproSearchFilter searchFilter,
//															@RequestParam String lastName) {
//		//return userService.findUserByLastName(searchFilter, lastName);
//		return null;
//	}

//	@GetMapping("/search")
//	public ThaproResponse<UserResource> search(@SearchFilter ThaproSearchFilter searchFilter,
//											   @RequestParam Map<String, String> requestParam) {
//		String firstName = requestParam.get("firstName");
//		String lastName = requestParam.get("lastName");
//		int pageNumber = Integer.parseInt(requestParam.get("pageNumber"));
//		searchFilter.setPageNo(pageNumber);
//
//		UserResource userResource = UserResource.builder().firstName(firstName).lastName(lastName).build();
//
//		//return userService.findBySpecification(userResource, searchFilter);
//		return null;
//	}
//
//	@GetMapping("/findAll")
//	public ThaproResponse<UserResource> findAllUsers(@SearchFilter ThaproSearchFilter searchFilter) {
//		return null;
//		//return userService.findAll(searchFilter);
//	}

//	@GetMapping("/findAllRowEntitiesBySpecification")
//	public ThaproResponse<UserResource> findAllRowEntitiesBySpecification(@SearchFilter ThaproSearchFilter searchFilter,
//																		  @RequestParam Map<String, String> requestParam) {
//
//		String firstName = requestParam.get("firstName");
//		String lastName = requestParam.get("lastName");
//		UserResource userResource = UserResource.builder()
//			.firstName(firstName)
//			.lastName(lastName)
//			.build();
//		//return userService.findAllRowEntitiesBySpecification(userResource, searchFilter);
//		return null;
//	}

//	@GetMapping("/findById")
//	public ThaproResponse<UserResource> findById(@SearchFilter ThaproSearchFilter searchFilter, @RequestParam Long id) {
//		//return userService.findById(id, searchFilter);
//		return null;
//	}

//	@GetMapping("/findAllCustom")
//	public List<UserEntity> findAllCustom() {
//		return userJpaServiceCustom.findAll();
//	}
}
