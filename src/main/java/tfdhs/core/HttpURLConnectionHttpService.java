package tfdhs.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tfdhs.api.Builder;
import tfdhs.api.HttpBuilder;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.api.HttpService;

/**
 * HttpService implementation that uses {@link HttpURLConnection}.
 * 
 * @author frode
 * 
 */
public class HttpURLConnectionHttpService implements HttpService {

    private final HttpBuilder builder;

    /**
     * @param builder
     *            the builder to use when building HttpResponse.
     */
    public HttpURLConnectionHttpService(HttpBuilder builder) {
	this.builder = builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse sendHttpRequest(HttpRequest request) {

	HttpURLConnection connection = null;
	int status = -1;
	String message = null;
	String response = null;
	Map<String, String> responseHeaders = Collections.emptyMap();
	try {
	    HttpURLConnection.setFollowRedirects(request.isFollowRedirects());
	    connection = newConnection(request);
	    prepareConnection(connection, request);

	    status = readStatus(connection);
	    message = readMessage(connection);
	    response = readResponse(connection);
	    responseHeaders = readHeaders(connection);
	} catch (IOException e) {
	    //
	} finally {
	    if (connection != null) {
		connection.disconnect();
	    }
	}

	return asHttpResponse(status, message, response, responseHeaders);
    }

    /**
     * Setup the connection with the properties from the request.
     * 
     * @param connection
     *            the connection to setup.
     * @param request
     *            the request to set on connection.
     * @throws IOException
     *             if any I/O.
     */
    protected void prepareConnection(HttpURLConnection connection,
	    HttpRequest request) throws IOException {

	connection.setRequestMethod(request.getMethod().name());

	String body = request.getBody();
	if (body != null) {
	    connection.setRequestProperty("Content-Length",
		    "" + Integer.toString(body.getBytes().length));
	    connection.setDoOutput(true);
	    write(request.getBody(), connection.getOutputStream());
	}
    }

    /**
     * Create a HttpResponse from parameters.
     * 
     * @param status
     *            the HTTP status code.
     * @param message
     *            the HTTP status message.
     * @param response
     *            the HTTP response content body.
     * @param responseHeaders
     *            the HTTP header fields.
     * @return new HttpResponse craeted from parameters.
     */
    protected HttpResponse asHttpResponse(int status, String message,
	    String response, Map<String, String> responseHeaders) {

	Builder.Response<HttpResponse> responseBuilder = builder
		.newResponse(status).message(message)
		.body(response == null ? "" : response)
		.headers(responseHeaders);

	return responseBuilder.build();
    }

    /**
     * Opens a new connection to the request URL.
     * 
     * @param request
     *            the request to create connection to.
     * @return new HttpURLConnection.
     * @throws IOException
     *             if any I/O errors.
     */
    protected HttpURLConnection newConnection(HttpRequest request)
	    throws IOException {
	URL url = newUrl(request);
	return (HttpURLConnection) url.openConnection();
    }

    /**
     * Create a new URL from request.
     * 
     * @param request
     *            the request to create URL from..
     * @return new request URL..
     * @throws MalformedURLException
     *             if the request URL specify a uknown protocol.
     */
    protected URL newUrl(HttpRequest request) throws MalformedURLException {
	return new URL(request.getUrl());
    }

    protected static int readStatus(HttpURLConnection connection)
	    throws IOException {
	return connection.getResponseCode();
    }

    protected static String readMessage(HttpURLConnection connection)
	    throws IOException {
	return connection.getResponseMessage();
    }

    protected static String readResponse(HttpURLConnection connection)
	    throws IOException {
	InputStream is = connection.getInputStream();
	return readInput(is);
    }

    protected static Map<String, String> readHeaders(
	    HttpURLConnection connection) {

	final Map<String, List<String>> responseHeaders = connection
		.getHeaderFields();

	final Map<String, String> headers = new HashMap<String, String>();
	for (Entry<String, List<String>> header : responseHeaders.entrySet()) {
	    headers.put(header.getKey(), flattenList(header.getValue()));
	}

	return headers;
    }

    /**
     * Consumes an input stream.
     * 
     * @param is
     *            the input to read.
     * @return the content read from the input stream.
     * @throws IOException
     *             if any I/O error.
     */
    protected static String readInput(InputStream is) throws IOException {

	if (is == null) {
	    return "";
	}

	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	StringBuffer dataRead = new StringBuffer();

	for (String line = rd.readLine(); line != null; line = rd.readLine()) {
	    dataRead.append(line);
	    dataRead.append('\n');
	}

	return dataRead.toString();
    }

    protected static void write(String text, OutputStream out)
	    throws IOException {

	DataOutputStream wr = null;
	try {
	    wr = new DataOutputStream(out);
	    wr.writeBytes(text == null ? "" : text);
	    wr.flush();
	} catch (IOException e) {
	} finally {
	    wr.close();
	}

    }

    protected static String flattenList(List<String> values) {
	StringBuilder builder = new StringBuilder();
	for (String value : values) {
	    builder.append(value);
	    builder.append(' ');
	}

	return builder.toString().trim();
    }

}