package tfdhs.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
	return null;
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

	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	StringBuffer dataRead = new StringBuffer();

	for (String line = rd.readLine(); line != null; line = rd.readLine()) {
	    dataRead.append(line);
	    dataRead.append('\n');
	}

	return dataRead.toString();
    }

}
