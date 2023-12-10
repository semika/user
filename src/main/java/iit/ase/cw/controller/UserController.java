package iit.ase.cw.controller;

import iit.ase.cw.model.model.UserResource;
import iit.ase.cw.platform.common.controller.BaseThaproController;
import iit.ase.cw.platform.common.type.ThaproResponse;
import iit.ase.cw.platform.common.context.model.SearchFilter;
import iit.ase.cw.platform.common.context.model.ThaproSearchFilter;
import iit.ase.cw.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user") 
public class UserController extends BaseThaproController<UserResource, Long> {

	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		super(userService);
	}

	@PutMapping()
	public void updateUser(@RequestBody UserResource userResource,
						   @SearchFilter ThaproSearchFilter searchFilter) {
		userService.save(userResource, searchFilter);
	}

	@GetMapping("/byFirstName")
	public ThaproResponse<UserResource> findUserByFirstName(@SearchFilter ThaproSearchFilter searchFilter,
															@RequestParam String firstName) {
		return userService.findUserByFirstName(searchFilter, firstName);
	}

	@GetMapping("/byLastName")
	public ThaproResponse<UserResource> findUserByLastName(@SearchFilter ThaproSearchFilter searchFilter,
															@RequestParam String lastName) {
		return userService.findUserByLastName(searchFilter, lastName);
	}

	@GetMapping("/search")
	public ThaproResponse<UserResource> search(@SearchFilter ThaproSearchFilter searchFilter,
											   @RequestParam Map<String, String> requestParam) {
		String firstName = requestParam.get("firstName");
		String lastName = requestParam.get("lastName");
		int pageNumber = Integer.parseInt(requestParam.get("pageNumber"));
		searchFilter.setPageNo(pageNumber);

		UserResource userResource = UserResource.builder().firstName(firstName).lastName(lastName).build();

		return userService.findBySpecification(userResource, searchFilter);
	}

	@GetMapping("/findAll")
	public ThaproResponse<UserResource> findAllUsers(@SearchFilter ThaproSearchFilter searchFilter) {
		return userService.findAll(searchFilter);
	}

	@GetMapping("/findAllRowEntitiesBySpecification")
	public ThaproResponse<UserResource> findAllRowEntitiesBySpecification(@SearchFilter ThaproSearchFilter searchFilter,
																		  @RequestParam Map<String, String> requestParam) {

		String firstName = requestParam.get("firstName");
		String lastName = requestParam.get("lastName");
		UserResource userResource = UserResource.builder()
			.firstName(firstName)
			.lastName(lastName)
			.build();
		return userService.findAllRowEntitiesBySpecification(userResource, searchFilter);
	}

	@GetMapping("/findById")
	public ThaproResponse<UserResource> findById(@SearchFilter ThaproSearchFilter searchFilter, @RequestParam Long id) {
		return userService.findById(id, searchFilter);
	}
}
