package tfdhs;

import java.net.Authenticator;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tfdhs.api.HttpBuilder;
import tfdhs.core.BasicHttpBuilder;
import tfdhs.core.ClientController;
import tfdhs.core.ClientModel;
import tfdhs.core.ClientWindowController;
import tfdhs.core.HttpsHostnameVerifier;
import tfdhs.core.TrustManager;
import tfdhs.core.ui.DialogAuthenticatorResolver;
import tfdhs.core.ui.HttpClientWindow;
import static java.lang.System.getProperty;

public class HttpClient extends JFrame {

    private static HttpBuilder builder = new BasicHttpBuilder();
    private static final Logger logger = Logger.getLogger(HttpClient.class
	    .getName());

    /**
     * @param window
     *            the root window panel that will be added to the frame.
     */
    public HttpClient(JPanel window) {
	add(window);
	pack();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Setup the HTTPClientService to use TrustManager and HostnameVerifier,
     * default is to setup the service to ignore all certificates and accept all
     * hostname given a SSLSession.
     * 
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    protected static void loadSslConfigurations()
	    throws KeyManagementException, NoSuchAlgorithmException {

	String trustManager = null;
	try {
	    trustManager = getProperty(TrustManager.class.getName(),
		    TrustManager.trustAllCerts.name());
	    HttpsURLConnection.setDefaultSSLSocketFactory(TrustManager
		    .newSocketFactory(TrustManager.valueOf(trustManager)));
	} catch (Exception e) {
	    logger.info("Not a valid trustManager: " + trustManager
		    + " accepts: " + TrustManager.values());
	}

	String hostnameVerifier = null;
	try {
	    hostnameVerifier = getProperty(
		    HttpsHostnameVerifier.class.getName(),
		    HttpsHostnameVerifier.allHostsValid.name());

	    HttpsURLConnection.setDefaultHostnameVerifier(HttpsHostnameVerifier
		    .valueOf(hostnameVerifier));
	} catch (Exception e) {
	    logger.info("Not a valid trustManager: " + trustManager
		    + " accepts: " + HttpsHostnameVerifier.values());
	}

    }

    public static void main(String[] args) throws KeyManagementException,
	    NoSuchAlgorithmException {

	loadSslConfigurations();

	final HttpClientWindow window = new HttpClientWindow();
	final ClientModel model = new ClientModel();
	final ClientController controller = new ClientWindowController(model,
		window, builder);

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		JFrame frame = new HttpClient(window.build());
		Authenticator
			.setDefault(new DialogAuthenticatorResolver(frame));
		window.bind(controller);
		frame.setVisible(true);

	    }
	});

    }
}
