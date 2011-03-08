package tfdhs.core;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.TableModel;

import tfdhs.api.HttpBuilder;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ui.ClientWindow;

/**
 * Control a ClientWindow.
 * 
 * @author frode
 * 
 */
public class ClientWindowController implements ClientController {

    private final ClientWindow window;
    private final HttpBuilder builder;
    private ClientModel model;
    private HttpRequest request;
    private HttpResponse response;

    public ClientWindowController(ClientModel model, ClientWindow window,
	    HttpBuilder builder) {
	this.window = window;
	this.builder = builder;
	this.model = model;
	this.request = builder.newRequest(null, model.getMethod()).build();
	this.response = builder.newResponse(-1).build();
    }

    @Override
    public void sendRequest() {

	response = builder.newHttpService().sendHttpRequest(createRequest());

	if (response.getStatus() == HTTP_UNAUTHORIZED) {
	    if (window.authenticate(getModel())) {
		response = builder.newHttpService().sendHttpRequest(
			createRequest());
	    }
	}

	System.out.println(response.getStatus());
	System.out.println(response.getHeaders());
	System.out.println(response.getMessage());
	System.out.println(response.getBody());

	viewRequest();
    }

    protected HttpRequest createRequest() {
//	Map<String, String> headers = createHeaderFields(model.getHeaders());
	return request = builder.newRequest(model.getUrl(), model.getMethod())
		.body(model.getBody() == null ? "" : model.getBody())
		.followRedirects(model.isFollowRedirects())/*.headers(headers)*/
		.build();

    }

    @Override
    public void newRequest() {
	this.request = builder.newRequest(null, model.getMethod()).build();
    }

    @Override
    public void clearRequest() {
	window.resetView(model);
    }

    @Override
    public void viewRequest() {
	window.viewRequest(request);
    }

    @Override
    public void viewResponse() {
	window.viewResponse(response);
    }

    @Override
    public ClientModel getModel() {
	return model;
    }

    protected static Map<String, String> createHeaderFields(TableModel table) {
	Map<String, String> headers = new HashMap<String, String>();
	for (int i = 0; i < table.getRowCount(); i++) {
	    headers.put((String) table.getValueAt(i, 0),
		    (String) table.getValueAt(i, 1));
	}
	return headers;
    }

}
