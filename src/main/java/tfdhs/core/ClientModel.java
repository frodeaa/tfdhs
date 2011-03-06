package tfdhs.core;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import tfdhs.api.HttpMethod;

public class ClientModel {

    private static final Class<?>[] HEADER_TYPES = new Class[] {
	    java.lang.String.class, java.lang.String.class };
    private String url;
    private TableModel headers;
    private HttpMethod method;
    private boolean followRedirects;
    private boolean bodySet;
    private String body;

    public ClientModel() {
	headers = new DefaultTableModel(new String[] { "Header Name",
		"Header Value" }, 0) {

	    public Class<?> getColumnClass(int columnIndex) {
		return HEADER_TYPES[columnIndex];
	    }
	};
	this.method = HttpMethod.GET;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public TableModel getHeaders() {
	return headers;
    }

    public void setHeaders(TableModel headers) {
	this.headers = headers;
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

}
