package tfdhs.core.ui;

import javax.swing.JPanel;

import tfdhs.api.Builder;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ClientController;
import tfdhs.core.ClientModel;

/**
 * Client panel.
 * 
 * @author frode
 * 
 */
public class HttpClientWindow implements ClientWindow,
	Builder<javax.swing.JPanel> {

    @Override
    public JPanel build() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void bind(ClientController controller) {
	// TODO Auto-generated method stub

    }

    @Override
    public void resetView(ClientModel model) {
	// TODO Auto-generated method stub

    }

    @Override
    public void viewRequest(HttpRequest request) {
	// TODO Auto-generated method stub

    }

    @Override
    public void viewResponse(HttpResponse response) {
	// TODO Auto-generated method stub

    }

}
