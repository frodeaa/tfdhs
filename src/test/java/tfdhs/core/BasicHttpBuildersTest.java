package tfdhs.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tfdhs.api.Builder;
import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;

public class BasicHttpBuildersTest {

    private BasicHttpBuilder builder;

    @Before
    public void setUp() {
	builder = new BasicHttpBuilder();
    }

    @Test
    public void testNewHttpService() {

	assertNotNull("Service null", builder.newHttpService());

    }

    @Test
    public void testNewRequest() {

	Builder.Request<HttpRequest> reqBuilder = builder.newRequest("someurl",
		HttpMethod.POST);

	assertNotNull("Request builder null", reqBuilder);

    }

    @Test
    public void testNewResponse() {

	Builder.Response<HttpResponse> reqBuilder = builder.newResponse(2030);

	assertNotNull("Response builder null", reqBuilder);

	assertEquals(2030, reqBuilder.build().getStatus());

    }

    @Test
    public void testNewResponseBuilder() {

	Builder.Response<HttpResponse> reqBuilder = builder.newResponse(-1);

	Map<String, List<String>> headers = Collections.singletonMap("time",
		Collections.singletonList("now"));
	HttpResponse response = reqBuilder.body("body").headers(headers)
		.message("message").status(220).build();

	assertNotNull("Response null", response);
	assertEquals(220, response.getStatus());
	assertEquals("body", response.getBody());
	assertEquals("message", response.getMessage());
	assertEquals(headers, response.getHeaders());

	assertNotNull(response.toString());

    }

    @Test
    public void testNewRequeset() {

	Builder.Request<HttpRequest> reqBuilder = builder.newRequest("someurl",
		HttpMethod.HEAD);

	assertNotNull("Response builder null", reqBuilder);

	assertEquals("someurl", reqBuilder.build().getUrl());
	assertEquals(HttpMethod.HEAD, reqBuilder.build().getMethod());

    }

    @Test
    public void testNewRequestBuilder() {

	Builder.Request<HttpRequest> reqBuilder = builder.newRequest("someurl",
		HttpMethod.HEAD);

	Map<String, List<String>> headers = Collections.singletonMap("time",
		Collections.singletonList("now"));
	HttpRequest request = reqBuilder.body("body").headers(headers)
		.followRedirects(true).url("someUrl").build();

	assertNotNull("Response null", request);
	assertEquals("body", request.getBody());
	assertEquals(headers, request.getHeaders());
	assertTrue(request.isFollowRedirects());

	assertNotNull(request.toString());

    }

}
