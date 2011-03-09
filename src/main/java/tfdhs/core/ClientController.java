package tfdhs.core;

/**
 * Client controller API.
 * 
 * @author frode
 * 
 */
public interface ClientController {

    enum Viewstate {
	request, response;
    }

    /**
     * Send a request.
     */
    void sendRequest();

    /**
     * Create new Request..
     */
    void newRequest();

    /**
     * Clears the current request from view.
     */
    void clearRequest();

    /**
     * Update the view to show the state.
     */
    void view(Viewstate state);

//    /**
//     * Update the view to show the response.
//     */
//    void viewResponse();

    /**
     * 
     * @return the model controlled by this controller.
     */
    ClientModel getModel();
}
