package tfdhs.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import tfdhs.api.Builder;
import tfdhs.api.HttpBuilder;
import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.api.HttpService;

/**
 * Default {@link HttpBuilder} implementation.
 * 
 * @author frode
 * 
 */
public class BasicHttpBuilder implements HttpBuilder {

    /**
     * {@inheritDoc}
     */
    public HttpService newHttpService() {
	return new HttpURLConnectionHttpService(this);
    }

    /**
     * {@inheritDoc}
     */
    public Builder.Request<HttpRequest> newRequest(String url, HttpMethod method) {
	return new HttpRequestBuilder(url, method);
    }

    private class HttpRequestBuilder implements Builder.Request<HttpRequest> {

	String url;
	HttpMethod method;
	Map<String, List<String>> headers = Collections.emptyMap();
	String body = null;
	boolean followRedirects = false;

	HttpRequestBuilder(String url, HttpMethod method) {
	    this.url = url;
	    this.method = method;
	}

	@Override
	public tfdhs.api.Builder.Request<HttpRequest> url(String url) {
	    this.url = url;
	    return this;
	}

	public tfdhs.api.Builder.Request<HttpRequest> followRedirects(
		boolean follow) {
	    this.followRedirects = follow;
	    return this;
	}

	public tfdhs.api.Builder.Request<HttpRequest> body(String body) {
	    this.body = body;
	    return this;

	}

	public tfdhs.api.Builder.Request<HttpRequest> headers(
		Map<String, List<String>> headers) {
	    this.headers = headers;
	    return this;
	}

	@Override
	public HttpRequest build() {
	    return new tfdhs.core.ImmutableRequest(url, method, headers, body,
		    followRedirects);
	}

    }

    /**
     * {@inheritDoc}
     */
    public Builder.Response<HttpResponse> newResponse(int status) {
	return new HttpResponseBuilder(status);
    }

    private class HttpResponseBuilder implements Builder.Response<HttpResponse> {

	private int statusCode;
	private String message = null;
	private Map<String, List<String>> headers = Collections.emptyMap();
	private String body;

	HttpResponseBuilder(int status) {
	    this.statusCode = status;
	}

	public tfdhs.api.Builder.Response<HttpResponse> message(String message) {
	    this.message = message;
	    return this;
	}

	public tfdhs.api.Builder.Response<HttpResponse> headers(
		Map<String, List<String>> headers) {
	    this.headers = headers;
	    return this;
	}

	public tfdhs.api.Builder.Response<HttpResponse> body(String body) {
	    this.body = body;
	    return this;
	}

	@Override
	public tfdhs.api.Builder.Response<HttpResponse> status(int statusCode) {
	    this.statusCode = statusCode;
	    return this;
	}

	@Override
	public HttpResponse build() {
	    return new tfdhs.core.ImmutableResponse(statusCode, message,
		    headers, body);
	}

    }

}
