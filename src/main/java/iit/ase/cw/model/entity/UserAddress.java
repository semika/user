package iit.ase.cw.model.entity;

import iit.ase.cw.platform.jpa.entity.BaseThaproEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserAddress extends BaseThaproEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="STREET")
    private String street;

    @Column(name="CITY")
    private String city;

    @Column(name="STATE")
    private String state;

    @Column(name="ZIP_CODE")
    private String zipCode;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User userEntity;

    @Override
    public String defaultSortField() {
        return "street";
    }
}
