package tfdhs.core;

import java.util.Collections;
import java.util.Map;

import tfdhs.api.HttpResponse;

/**
 * Immutable HttpResponse implementation.
 * 
 * @author frode
 * 
 */
public class ImmutableResponse implements HttpResponse {

    private final int status;
    private final String message;
    private final Map<String, String> headers;
    private final String body;

    public ImmutableResponse(int status, String message,
	    Map<String, String> headers, String body) {
	super();
	this.status = status;
	this.message = message;
	this.headers = Collections.unmodifiableMap(headers);
	this.body = body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatus() {
	return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
	return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getHeaders() {
	return headers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBody() {
	return body;
    }

    @Override
    public String toString() {
	return "Response [status=" + status + ", message=" + message
		+ ", headers=" + headers + ", body=" + body + "]";
    }

}
