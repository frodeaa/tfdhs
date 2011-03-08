package tfdhs.core;

import java.util.List;
import java.util.Map;

import tfdhs.api.HttpMethod;

/**
 * Client model, holds all properties needed to create a HttpRequest
 * 
 * @author frode
 * 
 */
public class ClientModel {

    private String url;
    private HttpMethod method;
    private boolean followRedirects;
    private boolean bodySet;
    private String body;
    private Map<String, List<String>> headerFields;

    /**
     * Create a new Model, ready for GET request.
     */
    public ClientModel() {
	setUrl(null);
	setFollowRedirects(false);
	setMethod(HttpMethod.GET);
	setBody(null);
	setBodySet(false);
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public HttpMethod getMethod() {
	return method;
    }

    public void setMethod(HttpMethod method) {
	this.method = method;
    }

    public boolean isFollowRedirects() {
	return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
	this.followRedirects = followRedirects;
    }

    public String getBody() {
	return body;
    }

    public void setBody(String body) {
	this.body = body;
    }

    public boolean isBodySet() {
	return bodySet;
    }

    public void setBodySet(boolean bodySet) {
	this.bodySet = bodySet;
    }

    public Map<String, List<String>> getHeaderFields() {
	return headerFields;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields) {
	this.headerFields = headerFields;
    }

    @Override
    public String toString() {
	return "ClientModel [url=" + url + ", method=" + method
		+ ", followRedirects=" + followRedirects + "]";
    }

}
