/**
 * Build-Id: EMPTY
 *
 * Spring Security authentication provider
 */
package com.vraidsys.server.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.vraidsys.shared.helpers.StringExt;

/**
 * @author jzerbe
 */
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {

    protected static List<GrantedAuthority> getUserLevelAuthorities() {
        final ArrayList<GrantedAuthority> aRetAuthList = new ArrayList<GrantedAuthority>();
        aRetAuthList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return aRetAuthList;
    }

    @Override
    protected void additionalAuthenticationChecks(
                    final UserDetails theUserDetails,
                    final UsernamePasswordAuthenticationToken theAuthToken)
                    throws AuthenticationException {
        if (!theUserDetails.getPassword().equals(
                        theAuthToken.getCredentials().toString())) {
            throw new BadCredentialsException("password does not match");
        }
    }

    private boolean isValidAuth(final String theUserName,
                    final String thePassword) {
        boolean isValidAuthRetVal = false;

        // TODO: change for your application
        final Connection aSqlConnection = new DbConnector("foo.db.properties")
                        .getSqlConnection();

        try {
            final CallableStatement aCStmt = aSqlConnection
                            .prepareCall("{call Login_Count(?, ?, ?)}");

            try {
                aCStmt.setString("in_UserName", theUserName);
                aCStmt.setString("in_Password", thePassword);
                aCStmt.registerOutParameter("out_AuthCount",
                                java.sql.Types.INTEGER);

                aCStmt.execute();

                final int aValidCount = aCStmt.getInt("out_AuthCount");
                if (aValidCount == 1) {
                    isValidAuthRetVal = true;
                }
            } catch (final SQLException ex) {
                Logger.getLogger(AuthProvider.class.getName()).log(
                                Level.WARNING, null, ex);
                isValidAuthRetVal = false;
            } finally {
                if (aCStmt != null) {
                    aCStmt.close();
                }
            }
        } catch (final SQLException ex) {
            Logger.getLogger(AuthProvider.class.getName()).log(Level.SEVERE,
                            null, ex);
            isValidAuthRetVal = false;
        } finally {
            if (aSqlConnection != null) {
                try {
                    aSqlConnection.close();
                } catch (final SQLException ex) {
                    Logger.getLogger(AuthProvider.class.getName()).log(
                                    Level.SEVERE, null, ex);
                }
            }
        }

        return isValidAuthRetVal;
    }

    @Override
    protected UserDetails retrieveUser(final String theUserName,
                    final UsernamePasswordAuthenticationToken theAuthToken)
                    throws AuthenticationException {
        if (StringExt.isEmpty(theUserName)) {
            throw new BadCredentialsException("empty username/password");
        } else {
            final String aPasswordStr = theAuthToken.getCredentials()
                            .toString();
            if (this.isValidAuth(theUserName, aPasswordStr)) {
                return new User(theUserName, theAuthToken.getCredentials()
                                .toString(), true, true, true, true,
                                AuthProvider.getUserLevelAuthorities());
            } else {
                throw new BadCredentialsException("invalid username/password");
            }
        }
    }
}
