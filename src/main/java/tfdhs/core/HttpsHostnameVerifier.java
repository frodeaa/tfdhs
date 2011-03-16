package tfdhs.core;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 
 * @author frode
 * 
 */
public enum HttpsHostnameVerifier implements HostnameVerifier {

    /**
     * Setup the connection to allow any hostname given a SSLSession.
     */
    allHostsValid {
	public boolean verify(String hostname, SSLSession session) {
	    return true;
	}
    };

}
