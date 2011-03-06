package tfdhs.api;

import java.util.Map;

/**
 * Inputs for HTTP request.
 * 
 * @author frode
 * 
 */
public interface HttpRequest {

    /**
     * @return <code>true</code> if HTTP redirects (request with response code
     *         3xx) should be automatically followed.
     */
    boolean isFollowRedirects();

    /**
     * @return the body to include in the request.
     */
    String getBody();

    /**
     * 
     * @return the headers fields to include in the request.
     */
    Map<String, String> getHeaders();

    /**
     * @return HTTP method.
     */
    HttpMethod getMethod();

    /**
     * @return the request URL.
     */
    String getUrl();

}
