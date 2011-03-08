package tfdhs.core;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import tfdhs.api.AuthenticatorResolver;

/**
 * Authenticator that uses a AuthenticatorResolver to resolve
 * PasswordAuthentication request.
 * 
 * @author frode
 * 
 */
public class AuthenticatorWrapper extends Authenticator {

    private AuthenticatorResolver resolver;

    /**
     * @param resolver
     *            the resolver to use when calling
     *            {@link #getPasswordAuthentication()}.
     */
    public AuthenticatorWrapper(AuthenticatorResolver resolver) {
	this.resolver = resolver;
    }

    /**
     * @return credentials entered by user.
     */
    protected PasswordAuthentication getPasswordAuthentication() {
	return resolver.authenticate(this);
    }

}
