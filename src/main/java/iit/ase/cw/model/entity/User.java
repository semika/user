package iit.ase.cw.model.entity;

import iit.ase.cw.platform.jpa.entity.BaseThaproOrganizationTenantEntity;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User extends BaseThaproOrganizationTenantEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PASSWORD")
	private String password;

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<UserAddress> addressEntityList;

	@Override
	public String defaultSortField() {
		return "firstName";
	}
}
