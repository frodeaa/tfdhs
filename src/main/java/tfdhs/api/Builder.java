package tfdhs.api;

import java.util.Map;

/**
 * Builder API for building HttpRequest and HttpResponse.
 * 
 * @author frode
 * 
 * @param <E>
 *            the type to build.
 */
public interface Builder<E> {

    /**
     * @return new instance.
     */
    E build();

    /**
     * Create request.
     * 
     * @author frode
     * 
     * @param <E>
     *            the type to build.
     */
    interface Request<E> extends Builder<E> {

	/**
	 * @param url
	 *            the URL to set.
	 * @return the builder.
	 */
	Request<E> url(String url);

	/**
	 * @param follow
	 *            follow HTTP redirects (3xx).
	 * @return the builder.
	 */
	Request<E> followRedirects(boolean follow);

	/**
	 * @param body
	 *            the HTTP request body.
	 * @return the builder.
	 */
	Request<E> body(String body);

	/**
	 * @param headers
	 *            the HTTP header fields.
	 * @return the builder.
	 */
	Request<E> headers(Map<String, String> headers);

    }

    /**
     * Create response.
     * 
     * @author frode
     * 
     * @param <E>
     *            the type to produce.
     */
    interface Response<E> extends Builder<E> {

	/**
	 * @param status
	 *            the HTTP status code.
	 * @return the builder.
	 */
	Response<E> status(int status);

	/**
	 * @param message
	 *            the HTTP message.
	 * @return the builder.
	 */
	Response<E> message(String message);

	/**
	 * @param body
	 *            the HTTP response body.
	 * @return the builder.
	 */
	Response<E> body(String body);

	/**
	 * @param headers
	 *            the HTTP header fields.
	 * @return the builder.
	 */
	Response<E> headers(Map<String, String> headers);
    }

}
