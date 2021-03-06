package tfdhs.core;

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
    private Viewstate viewstate = Viewstate.request;

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
	view(viewstate);
    }

    protected HttpRequest createRequest() {
	return request = builder.newRequest(model.getUrl(), model.getMethod())
		.body(model.getBody() == null ? "" : model.getBody())
		.followRedirects(model.isFollowRedirects())
		.headers(model.getHeaderFields()).build();

    }

    @Override
    public void newRequest() {
	this.request = builder.newRequest(null, model.getMethod()).build();
    }

    @Override
    public void clearRequest() {
	viewstate = Viewstate.request;
	window.resetView(model);
    }

    @Override
    public void view(Viewstate view) {
	viewstate = view;

	if (Viewstate.request.equals(view)) {
	    window.viewRequest(request);
	} else {
	    window.viewResponse(response);
	}

    }

    @Override
    public ClientModel getModel() {
	return model;
    }

}
