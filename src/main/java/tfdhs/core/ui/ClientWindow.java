package tfdhs.core.ui;

import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ClientController;
import tfdhs.core.ClientModel;

/**
 * View API.
 * 
 * @author frode
 * 
 */
public interface ClientWindow {

    /**
     * Bind the view to a controller.
     * 
     * @param controller
     *            the controller to bind to.
     */
    void bind(ClientController controller);

    /**
     * Reset the view to given model.
     * 
     * @param model
     *            the model to update view with.
     */
    void resetView(ClientModel model);

    /**
     * Show details about a request.
     * 
     * @param request
     *            the request to show details about.
     */
    void viewRequest(HttpRequest request);

    /**
     * Show details about a response.
     * 
     * @param response
     *            the response to show details about.
     */
    void viewResonse(HttpResponse response);

}
