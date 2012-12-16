/**
 * Build-Id: EMPTY
 *
 * Spring Security custom implementation of UserDetailsService
 */
package com.vraidsys.server.data;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author jzerbe
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    public static UserDetails loadUserByUsernameImpl(final String theUserName)
                    throws UsernameNotFoundException {
        final boolean theUserExists = false;
        // TODO check db for username
        final String thePassword = "";
        if (theUserExists) {
            return new User(theUserName, thePassword, true, true, true, true,
                            AuthProvider.getUserLevelAuthorities());
        } else {
            throw new UsernameNotFoundException(theUserName + " was not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String theUserName)
                    throws UsernameNotFoundException {
        return UserDetailsServiceImpl.loadUserByUsernameImpl(theUserName);
    }
}
