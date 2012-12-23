/**
 * Build-Id: EMPTY
 *
 * Spring Security custom Remember-Me cookie provider
 * to authenticate users with UserDetailsServiceImpl
 */
package com.vraidsys.server.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @author jzerbe
 */
public class RememberMeProvider extends TokenBasedRememberMeServices implements
                UserDetailsService {

    private static final String[] kLocalDomain = { "127.0.0.1", "localhost" };
    private static final String kProductionDomain = "example.com";
    private static final String kRememberMe = "appRememberMeKey";

    private static Cookie createCookie(final String theKey,
                    final String theValue, final String theDomain,
                    final int theMaxAgeInSeconds, final String thePath,
                    final boolean isSecureOnly) {
        final Cookie aCookie = new Cookie(theKey, theValue);
        aCookie.setDomain(theDomain);
        aCookie.setMaxAge(theMaxAgeInSeconds);
        aCookie.setPath(thePath);
        aCookie.setSecure(isSecureOnly);

        return aCookie;
    }

    private static String getCookieDomain(final HttpServletRequest request) {
        String aCookieDomain = null;
        try {
            aCookieDomain = new URL(request.getRequestURL().toString())
                            .getHost();
            if (aCookieDomain.contains(RememberMeProvider.kLocalDomain[0])) {
                aCookieDomain = RememberMeProvider.kLocalDomain[0];
            } else if (aCookieDomain
                            .contains(RememberMeProvider.kLocalDomain[1])) {
                aCookieDomain = RememberMeProvider.kLocalDomain[1];
            } else {
                aCookieDomain = RememberMeProvider.kProductionDomain;
            }
        } catch (final MalformedURLException ex) {
            Logger.getLogger(RememberMeProvider.class.getName()).log(
                            Level.WARNING, null, ex);

            aCookieDomain = RememberMeProvider.kProductionDomain;
        }

        return aCookieDomain;
    }

    public RememberMeProvider() {
        super(RememberMeProvider.kRememberMe, new UserDetailsServiceImpl());
    }

    @Override
    protected void cancelCookie(final HttpServletRequest request,
                    final HttpServletResponse response) {
        response.addCookie(RememberMeProvider.createCookie(
                        super.getCookieName(), "",
                        RememberMeProvider.getCookieDomain(request), 0, "/",
                        request.isSecure()));

        LogFactory.getLog(this.getClass()).debug(
                        "cancel " + super.getCookieName());
    }

    @Override
    public UserDetails loadUserByUsername(final String theUserName)
                    throws UsernameNotFoundException {
        return UserDetailsServiceImpl.loadUserByUsernameImpl(theUserName);
    }

    @Override
    protected void setCookie(final String[] theTokenArray, final int theMaxAge,
                    final HttpServletRequest request,
                    final HttpServletResponse response) {
        response.addCookie(RememberMeProvider.createCookie(
                        super.getCookieName(),
                        super.encodeCookie(theTokenArray),
                        RememberMeProvider.getCookieDomain(request), theMaxAge,
                        "/", request.isSecure()));

        LogFactory.getLog(this.getClass())
                        .debug("set " + super.getCookieName());
    }
}
