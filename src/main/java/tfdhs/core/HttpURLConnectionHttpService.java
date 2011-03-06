package tfdhs.core;

import java.net.HttpURLConnection;

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

    @Override
    public HttpResponse sendHttpRequest(HttpRequest request) {
	return null;
    }

}
