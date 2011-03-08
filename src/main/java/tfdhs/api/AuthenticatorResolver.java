package tfdhs.api;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Resolve authentication request.
 * 
 * @author frode
 * 
 */
public interface AuthenticatorResolver {

    /**
     * Provides credentials for an Authenticator.
     * 
     * @param authenticator
     *            the authenticator to provide credentials.
     * @return <code>null</code> if no credentials are available.
     */
    PasswordAuthentication authenticate(Authenticator authenticator);

}
