package tfdhs.api;

import java.util.List;
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
    Map<String, List<String>> getHeaders();

    /**
     * @return the data returned in the response.
     */
    String getBody();

}
