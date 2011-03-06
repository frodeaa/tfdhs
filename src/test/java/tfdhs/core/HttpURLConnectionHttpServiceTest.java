package tfdhs.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tfdhs.api.Builder;
import tfdhs.api.HttpBuilder;
import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;

public class HttpURLConnectionHttpServiceTest {

    private HttpURLConnectionHttpService service;
    private HttpBuilder mockBuilder;

    @Before
    public void setUp() {
	mockBuilder = mock(HttpBuilder.class);
	service = new HttpURLConnectionHttpService(null);
    }

    @Test
    public void testNewConnection() throws IOException {

	HttpRequest mockRequest = mock(HttpRequest.class);

	when(mockRequest.getUrl()).thenReturn("http://localhost");

	HttpURLConnection connection = null;
	try {
	    connection = service.newConnection(mockRequest);
	    assertNotNull("Connection was null", connection);
	} catch (IOException e) {
	    fail("Unable to create new connection, got\n" + e);
	} finally {
	    if (connection != null) {
		connection.disconnect();
	    }
	}

    }

    @Test(expected = MalformedURLException.class)
    public void testNewConnectionFailsUrl() throws IOException {

	HttpRequest mockRequest = mock(HttpRequest.class);

	service.newConnection(mockRequest);

    }

    @Test(expected = MalformedURLException.class)
    public void testNewUrlFailMalformedUrl() throws MalformedURLException {

	HttpRequest mockRequest = mock(HttpRequest.class);

	service.newUrl(mockRequest);

    }

    @Test
    public void testNewUrl() throws MalformedURLException {

	HttpRequest mockRequest = mock(HttpRequest.class);

	when(mockRequest.getUrl()).thenReturn("http://localhost");

	URL url = service.newUrl(mockRequest);

	assertNotNull("URL null", url);
	assertEquals("URL host", "localhost", url.getHost());

    }

    @Test
    public void testReadInput() throws IOException {

	String expected = "simple\ntest\nstring\n";
	ByteArrayInputStream input = new ByteArrayInputStream(
		expected.getBytes());

	assertEquals("Input read", expected,
		HttpURLConnectionHttpService.readInput(input));

	assertEquals("Input read", "",
		HttpURLConnectionHttpService.readInput(null));

    }

    @Test
    public void testSendHttpRequest() throws IOException {

	@SuppressWarnings("unchecked")
	final Builder.Response<HttpResponse> mockResponseBuilder = mock(Builder.Response.class);
	final HttpRequest mockRequest = mock(HttpRequest.class);
	final HttpURLConnection mockConnection = mock(HttpURLConnection.class);
	final HttpResponse mockResponse = mock(HttpResponse.class);

	service = new HttpURLConnectionHttpService(mockBuilder) {
	    protected HttpURLConnection newConnection(HttpRequest request) {
		assertEquals("Wrong request", mockRequest, request);
		return mockConnection;
	    }
	};

	when(mockRequest.getMethod()).thenReturn(HttpMethod.GET);

	when(mockConnection.getResponseCode()).thenReturn(200);
	when(mockBuilder.newResponse(200)).thenReturn(mockResponseBuilder);

	when(mockConnection.getResponseMessage()).thenReturn("message");
	when(mockResponseBuilder.message("message")).thenReturn(
		mockResponseBuilder);

	when(mockConnection.getInputStream()).thenReturn(
		new ByteArrayInputStream("body".getBytes()));
	when(mockResponseBuilder.body("body\n"))
		.thenReturn(mockResponseBuilder);

	Map<String, List<String>> noResponseHeaders = Collections.emptyMap();
	Map<String, String> noHeaders = Collections.emptyMap();
	when(mockConnection.getHeaderFields()).thenReturn(noResponseHeaders);
	when(mockResponseBuilder.headers(noHeaders)).thenReturn(
		mockResponseBuilder);

	when(mockResponseBuilder.build()).thenReturn(mockResponse);

	HttpResponse response = service.sendHttpRequest(mockRequest);
	assertNotNull("Response was null", response);
	assertEquals("Wrong response", mockResponse, response);

	verify(mockConnection).setRequestMethod("GET");
	verify(mockConnection).disconnect();
    }

    @Test
    public void testSendFails() throws IOException {

	@SuppressWarnings("unchecked")
	final Builder.Response<HttpResponse> mockResponseBuilder = mock(Builder.Response.class);
	final HttpURLConnection mockConnection = mock(HttpURLConnection.class);
	final HttpRequest mockRequest = mock(HttpRequest.class);
	final HttpResponse mockResponse = mock(HttpResponse.class);

	service = new HttpURLConnectionHttpService(mockBuilder) {
	    protected HttpURLConnection newConnection(HttpRequest request) {
		assertEquals("Wrong request", mockRequest, request);
		return mockConnection;
	    }
	};

	Map<String, String> noHeaders = Collections.emptyMap();

	when(mockRequest.getMethod()).thenReturn(HttpMethod.GET);

	when(mockConnection.getResponseCode()).thenThrow(new IOException());
	when(mockBuilder.newResponse(-1)).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.message(anyString())).thenReturn(
		mockResponseBuilder);
	when(mockResponseBuilder.body(anyString())).thenReturn(
		mockResponseBuilder);
	when(mockResponseBuilder.message(anyString())).thenReturn(
		mockResponseBuilder);
	when(mockResponseBuilder.headers(noHeaders)).thenReturn(
		mockResponseBuilder);
	when(mockResponseBuilder.build()).thenReturn(mockResponse);

	HttpResponse response = service.sendHttpRequest(mockRequest);
	assertNotNull("Response was null", response);
	assertEquals("Wrong response", mockResponse, response);

	verify(mockConnection).setRequestMethod("GET");
	verify(mockConnection).disconnect();
    }

    @Test
    public void testWrite() throws IOException {

	String text = "word";
	OutputStream mockOut = mock(OutputStream.class);

	HttpURLConnectionHttpService.write(text, mockOut);

	verify(mockOut).write("w".getBytes()[0]);
	verify(mockOut).write("o".getBytes()[0]);
	verify(mockOut).write("r".getBytes()[0]);
	verify(mockOut).write("d".getBytes()[0]);
	verify(mockOut).close();

    }

    @Test
    public void testWriteNull() throws IOException {

	OutputStream mockOut = mock(OutputStream.class);

	HttpURLConnectionHttpService.write(null, mockOut);

	verify(mockOut).close();

    }

    @Test
    public void testReadStatus() throws IOException {
	HttpURLConnection mockConnection = mock(HttpURLConnection.class);

	when(mockConnection.getResponseCode()).thenReturn(2010);

	int status = HttpURLConnectionHttpService.readStatus(mockConnection);

	assertEquals("status code", 2010, status);
    }

    @Test
    public void testSendFailsIO() throws IOException {

	final HttpRequest mockRequest = mock(HttpRequest.class);
	@SuppressWarnings("unchecked")
	final Builder.Response<HttpResponse> mockResponseBuilder = mock(Builder.Response.class);
	final HttpResponse mockResponse = mock(HttpResponse.class);
	final Map<String, String> noHeaders = Collections.emptyMap();

	service = new HttpURLConnectionHttpService(mockBuilder) {

	    protected HttpURLConnection newConnection(HttpRequest request)
		    throws IOException {
		assertEquals(mockRequest, request);
		throw new IOException();
	    }

	};

	when(mockBuilder.newResponse(-1)).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.message(null)).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.body("")).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.message(null)).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.headers(noHeaders)).thenReturn(
		mockResponseBuilder);
	when(mockResponseBuilder.build()).thenReturn(mockResponse);

	service.sendHttpRequest(mockRequest);
    };

    @Test
    public void testSendFailsIOCloseConnection() throws IOException {
	final HttpURLConnection mockConnection = mock(HttpURLConnection.class);
	final HttpRequest mockRequest = mock(HttpRequest.class);
	@SuppressWarnings("unchecked")
	final Builder.Response<HttpResponse> mockResponseBuilder = mock(Builder.Response.class);
	final HttpResponse mockResponse = mock(HttpResponse.class);
	final Map<String, String> noHeaders = Collections.emptyMap();

	service = new HttpURLConnectionHttpService(mockBuilder) {

	    protected HttpURLConnection newConnection(HttpRequest request)
		    throws IOException {
		assertEquals(mockRequest, request);
		return mockConnection;
	    }

	};

	when(mockRequest.getMethod()).thenReturn(HttpMethod.POST);
	when(mockConnection.getResponseCode()).thenThrow(new IOException());

	when(mockBuilder.newResponse(-1)).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.message(null)).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.body("")).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.message(null)).thenReturn(mockResponseBuilder);
	when(mockResponseBuilder.headers(noHeaders)).thenReturn(
		mockResponseBuilder);
	when(mockResponseBuilder.build()).thenReturn(mockResponse);

	service.sendHttpRequest(mockRequest);

	verify(mockConnection).disconnect();
    };

    @Test
    public void testPrepareConnection() throws IOException {
	HttpURLConnection mockConnection = mock(HttpURLConnection.class);
	HttpRequest mockRequest = mock(HttpRequest.class);
	OutputStream mockOut = mock(OutputStream.class);

	when(mockRequest.getMethod()).thenReturn(HttpMethod.POST);
	when(mockRequest.getBody()).thenReturn("body");
	when(mockConnection.getOutputStream()).thenReturn(mockOut);

	service.prepareConnection(mockConnection, mockRequest);

	verify(mockConnection).setRequestMethod("POST");
	verify(mockConnection).setRequestProperty("Content-Length",
		"body".getBytes().length + "");
	verify(mockConnection).setDoOutput(true);

    }

    @Test(expected = ProtocolException.class)
    public void testPrepareConnectionFail() throws IOException {
	HttpURLConnection mockConnection = mock(HttpURLConnection.class);
	HttpRequest mockRequest = mock(HttpRequest.class);

	when(mockRequest.getMethod()).thenReturn(HttpMethod.CONNECT);
	doThrow(new ProtocolException()).when(mockConnection).setRequestMethod(
		anyString());

	service.prepareConnection(mockConnection, mockRequest);

    }

    @Test
    public void testReadMessage() throws IOException {

	HttpURLConnection mockConnection = mock(HttpURLConnection.class);

	when(mockConnection.getResponseMessage()).thenReturn("message");

	String message = HttpURLConnectionHttpService
		.readMessage(mockConnection);

	assertNotNull("Message null", message);
	assertEquals("Message", "message", message);

    }

    @Test
    public void testReadResponse() throws IOException {

	HttpURLConnection mockConnection = mock(HttpURLConnection.class);

	when(mockConnection.getInputStream()).thenReturn(
		new ByteArrayInputStream("content".getBytes()));

	String response = HttpURLConnectionHttpService
		.readResponse(mockConnection);

	assertNotNull("Response null", response);
	assertEquals("Response", "content\n", response);

    }

    @Test
    public void testReadHeaders() {

	Map<String, List<String>> headers = new HashMap<String, List<String>>();
	headers.put("content", Collections.nCopies(2, "word"));
	headers.put("time", Collections.singletonList("clock"));
	HttpURLConnection mockConnection = mock(HttpURLConnection.class);

	when(mockConnection.getHeaderFields()).thenReturn(headers);

	Map<String, String> result = HttpURLConnectionHttpService
		.readHeaders(mockConnection);

	assertNotNull("Header map null", result);
	assertTrue("Missing header", result.containsKey("content"));
	assertEquals("Missing value", "word word", result.get("content"));

	assertTrue("Missing header", result.containsKey("time"));
	assertEquals("Missing value", "clock", result.get("time"));

	verify(mockConnection).getHeaderFields();

    }

    @Test
    public void testWriteFails() throws IOException {

	OutputStreamThrowsIO out = new OutputStreamThrowsIO();

	HttpURLConnectionHttpService.write("w", out);

	assertTrue("OutputStream not closed", out.closed);
    }

    static class OutputStreamThrowsIO extends OutputStream {

	boolean closed = false;

	@Override
	public void write(int b) throws IOException {
	    throw new IOException();
	}

	public void close() {
	    this.closed = true;
	}

    }

    @Test
    public void testFlattenEmptyList() {

	List<String> emptyList = Collections.emptyList();

	assertEquals("Flatten empty list", "",
		HttpURLConnectionHttpService.flattenList(emptyList));

    }

    @Test
    public void testFlattenNonEmptyList() {

	List<String> stringList = Collections.nCopies(4, "value");

	assertEquals("Flatten empty list", "value value value value",
		HttpURLConnectionHttpService.flattenList(stringList));

    }

}
