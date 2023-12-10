package iit.ase.cw.model.entity;

import iit.ase.cw.platform.jpa.entity.BaseThaproOrganizationTenantEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseThaproOrganizationTenantEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PASSWORD")
	private String password;

	@Override
	public String defaultSortField() {
		return "firstName";
	}
}
