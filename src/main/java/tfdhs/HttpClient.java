package tfdhs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tfdhs.api.HttpBuilder;
import tfdhs.core.BasicHttpBuilder;
import tfdhs.core.ClientController;
import tfdhs.core.ClientWindowController;
import tfdhs.core.ClientModel;
import tfdhs.core.ui.HttpClientWindow;

public class HttpClient extends JFrame {

    private static HttpBuilder builder = new BasicHttpBuilder();

    public HttpClient(JPanel window) {
	add(window);
	pack();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

	final HttpClientWindow window = new HttpClientWindow();
	final ClientModel model = new ClientModel();
	final ClientController controller = new ClientWindowController(model, window,
		builder);

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		JFrame frame = new HttpClient(window.build());
		window.bind(controller);

		frame.setVisible(true);

	    }
	});

    }
}
