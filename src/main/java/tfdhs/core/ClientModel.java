package tfdhs.core;

import java.util.Collections;
import java.util.HashMap;
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
	setHeaderFields(new HashMap<String, List<String>>());
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
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((body == null) ? 0 : body.hashCode());
	result = prime * result + (bodySet ? 1231 : 1237);
	result = prime * result + (followRedirects ? 1231 : 1237);
	result = prime * result
		+ ((headerFields == null) ? 0 : headerFields.hashCode());
	result = prime * result + ((method == null) ? 0 : method.hashCode());
	result = prime * result + ((url == null) ? 0 : url.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ClientModel other = (ClientModel) obj;
	if (body == null) {
	    if (other.body != null)
		return false;
	} else if (!body.equals(other.body))
	    return false;
	if (bodySet != other.bodySet)
	    return false;
	if (followRedirects != other.followRedirects)
	    return false;
	if (headerFields == null) {
	    if (other.headerFields != null)
		return false;
	} else if (!headerFields.equals(other.headerFields))
	    return false;
	if (method != other.method)
	    return false;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ClientModel [url=" + url + ", method=" + method
		+ ", followRedirects=" + followRedirects + "]";
    }

}
