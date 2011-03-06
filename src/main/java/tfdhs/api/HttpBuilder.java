package tfdhs.api;

/**
 * Build HTTP resources.
 * 
 * @author frode
 * 
 */
public interface HttpBuilder {

    /**
     * @return new HttpService.
     */
    HttpService newHttpService();

    /**
     * @param url
     *            the HTTP request URL.
     * @param method
     *            the HTTP method.
     * @return new HttpRequest builder.
     */
    Builder.Request<HttpRequest> newRequest(String url, HttpMethod method);

    /**
     * @param status
     *            the HTTP status code.
     * @return new HttpResponse builder.
     */
    Builder.Response<HttpResponse> newResponse(int status);

}
