package tfdhs.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;

/**
 * Immutable HTTPRequest implementation.
 * 
 * @author frode
 * 
 */
public class ImmutableRequest implements HttpRequest {

    private final boolean followRedirects;
    private final String body;
    private final Map<String, List<String>> headers;
    private final HttpMethod method;
    private final String url;

    public ImmutableRequest(String url, HttpMethod method,
	    Map<String, List<String>> headers, String body, boolean followRedirects) {
	super();
	this.url = url;
	this.method = method;
	this.headers = Collections.unmodifiableMap(headers);
	this.body = body;
	this.followRedirects = followRedirects;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isFollowRedirects() {
	return followRedirects;
    }

    /**
     * {@inheritDoc}
     */
    public String getBody() {
	return body;
    }

    public Map<String, List<String>> getHeaders() {
	return headers;
    }

    /**
     * {@inheritDoc}
     */
    public HttpMethod getMethod() {
	return method;
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
	return url;
    }

    @Override
    public String toString() {
	return "ImmutableRequest [url=" + url + ", method=" + method
		+ ", headers=" + headers + ", followRedirects="
		+ followRedirects + ", body=" + body + "]";
    }

}
