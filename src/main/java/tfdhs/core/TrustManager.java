package tfdhs.core;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * A TrustManager implementations.
 * 
 * @author frode
 * 
 */
public enum TrustManager implements X509TrustManager {

    /**
     * A manager that will accept any certificate, including self signed.
     */
    trustAllCerts {

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
		throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
		throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
	    return null;
	}
    };

    /**
     * Create a SS
     * @param managers
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLSocketFactory newSocketFactory(X509TrustManager... managers)
	    throws NoSuchAlgorithmException, KeyManagementException {
	SSLContext sc = SSLContext.getInstance("SSL");
	sc.init(null, managers, new java.security.SecureRandom());
	return sc.getSocketFactory();
    }

}
