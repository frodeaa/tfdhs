package tfdhs.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import tfdhs.api.HttpRequest;

public class HttpURLConnectionHttpServiceTest {

    private HttpURLConnectionHttpService service;

    @Before
    public void setUp() {
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

    }

}
