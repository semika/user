package iit.ase.cw.model.model;

import iit.ase.cw.platform.common.type.BaseThaproResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResource extends BaseThaproResource<Long> {

    private Long id;

    private String firstName;

    private String lastName;

    private String password;
}
