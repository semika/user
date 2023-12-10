package iit.ase.cw.config;

import org.springframework.security.core.GrantedAuthority;

/*
GrantedAuthoritys are high level permissions the user is granted. A few examples are roles or scopes.

GrantedAuthoritys can be obtained from the Authentication.getAuthorities() method. This method provides a
Collection of GrantedAuthority objects. A GrantedAuthority is, not surprisingly, an authority that is granted to the principal.
Such authorities are usually "roles", such as ROLE_ADMINISTRATOR or ROLE_HR_SUPERVISOR.
These roles are later on configured for web authorization, method authorization and domain object authorization.
Other parts of Spring Security are capable of interpreting these authorities, and expect them to be present.
When using username/password based authentication GrantedAuthoritys are usually loaded by the UserDetailsService.

Usually the GrantedAuthority objects are application-wide permissions. They are not specific to a given domain object.
Thus, you wouldn’t likely have a GrantedAuthority to represent a permission to Employee object number 54,
because if there are thousands of such authorities you would quickly run out of memory (or, at the very least, cause
the application to take a long time to authenticate a user). Of course, Spring Security is expressly designed to
handle this common requirement, but you’d instead use the project’s domain object security capabilities for this purpose.
 */
public class Authority implements GrantedAuthority {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
