package tfdhs.api;

import java.util.Map;

/**
 * Result from a HTTP request.
 * 
 * @author frode
 * 
 */
public interface HttpResponse {

    /**
     * @return HTTP status code.
     */
    int getStatus();

    /**
     * @return the HTTP response message.
     */
    String getMessage();

    /**
     * 
     * @return Map over the header fields.
     */
    Map<String, String> getHeaders();

    /**
     * @return the data returned in the response.
     */
    String getBody();

}
