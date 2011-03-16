package tfdhs;

import java.net.Authenticator;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

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

public class HttpClient extends JFrame {

    private static HttpBuilder builder = new BasicHttpBuilder();

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
     * Setup the HTTPClientService to ignore all Certificates.
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    protected static void ignoreAllSecureCertificates()
	    throws KeyManagementException, NoSuchAlgorithmException {

	HttpsURLConnection.setDefaultSSLSocketFactory(TrustManager
		.newSocketFactory(TrustManager.trustAllCerts));
	HttpsURLConnection
		.setDefaultHostnameVerifier(HttpsHostnameVerifier.allHostsValid);

    }

    public static void main(String[] args) throws KeyManagementException,
	    NoSuchAlgorithmException {
	
	ignoreAllSecureCertificates();

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
