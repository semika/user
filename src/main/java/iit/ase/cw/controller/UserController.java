package iit.ase.cw.controller;

import iit.ase.cw.model.entity.User;
import iit.ase.cw.model.model.UserDto;
import iit.ase.cw.platform.common.controller.BaseThaproController;
import iit.ase.cw.platform.common.type.ThaproResponse;
import iit.ase.cw.platform.common.context.model.SearchFilter;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.service.UserJpaServiceCustom;
import iit.ase.cw.service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user") 
public class UserController extends BaseThaproController<UserDto, Long> {
	private UserService userService;

	private UserJpaServiceCustom userJpaServiceCustom;

	public UserController(UserService userService, UserJpaServiceCustom userJpaServiceCustom) {
		super(userService);
		this.userJpaServiceCustom = userJpaServiceCustom;
		this.userService = userService;
	}

	@PutMapping()
	public void updateUser(@RequestBody UserDto userResource,
						   @SearchFilter ThaproSearchFilter searchFilter) {
		userService.save(userResource, searchFilter);
	}

	@GetMapping("/byFirstName")
	public ThaproResponse<UserDto> findUserByFirstName(@SearchFilter ThaproSearchFilter searchFilter,
													   @RequestParam String firstName) {
		return userService.findUserByFirstName(searchFilter, firstName);
	}

	@GetMapping("/byLastName")
	public ThaproResponse<UserDto> findUserByLastName(@SearchFilter ThaproSearchFilter searchFilter,
													  @RequestParam String lastName) {
		return userService.findUserByLastName(searchFilter, lastName);
	}

	@GetMapping("/search")
	public ThaproResponse<UserDto> search(@SearchFilter ThaproSearchFilter searchFilter,
										  @RequestParam Map<String, String> requestParam) {
		String firstName = requestParam.get("firstName");
		String lastName = requestParam.get("lastName");
		int pageNumber = Integer.parseInt(requestParam.get("pageNumber"));
		searchFilter.setPageNo(pageNumber);

		UserDto userResource = UserDto.builder().firstName(firstName).lastName(lastName).build();

		return userService.findBySpecification(userResource, searchFilter);
	}

	@GetMapping("/findAll")
	public ThaproResponse<UserDto> findAllUsers(@SearchFilter ThaproSearchFilter searchFilter) {
		return userService.findAll(searchFilter);
	}

	@GetMapping("/findAllRowEntitiesBySpecification")
	public ThaproResponse<UserDto> findAllRowEntitiesBySpecification(@SearchFilter ThaproSearchFilter searchFilter,
																	 @RequestParam Map<String, String> requestParam) {

		String firstName = requestParam.get("firstName");
		String lastName = requestParam.get("lastName");
		UserDto userResource = UserDto.builder()
			.firstName(firstName)
			.lastName(lastName)
			.build();
		return userService.findAllRowEntitiesBySpecification(userResource, searchFilter);
	}

	@GetMapping("/findById")
	public ThaproResponse<UserDto> findById(@SearchFilter ThaproSearchFilter searchFilter, @RequestParam Long id) {
		return userService.findById(id, searchFilter);
	}

	@GetMapping("/findAllCustom")
	public List<User> findAllCustom() {
		return userJpaServiceCustom.findAll();
	}
}
