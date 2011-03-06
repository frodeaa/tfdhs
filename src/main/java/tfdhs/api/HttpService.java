package tfdhs.api;

/**
 * Execute HTTP service request.
 * 
 * @author frode
 * 
 */
public interface HttpService {

    /**
     * Execute a HTTP request.
     * 
     * @param request
     *            the request to execute.
     * @return the result response from the request.
     */
    HttpResponse sendHttpRequest(HttpRequest request);

}
