package tfdhs.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import tfdhs.api.HttpBuilder;
import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ui.ClientWindow;

public class ClientWindowControllerTest {

    private ClientWindow mockWindow;
    private ClientModel model;
    private HttpBuilder builder;

    private ClientWindowController controller;

    @Before
    public void setUp() {
	model = new ClientModel();
	mockWindow = mock(ClientWindow.class);
	builder = new BasicHttpBuilder();
	controller = new ClientWindowController(model, mockWindow, builder);
    }

    @Test
    public void testDefaultsNew() {

	assertEquals("Model not set", model, controller.getModel());

    }

    @Test
    public void testViewResponse() {

	controller.viewResponse();

	verify(mockWindow).viewResponse(any(HttpResponse.class));

    }

    @Test
    public void testViewRequest() {

	controller.viewRequest();

	verify(mockWindow).viewRequest(any(HttpRequest.class));

    }

    @Test
    public void testClearRequest() {

	controller.clearRequest();

	verify(mockWindow).resetView(controller.getModel());

    }

    @Test
    public void testSendRequest() {

	controller.sendRequest();

	verify(mockWindow).viewRequest(any(HttpRequest.class));

    }

    @Test
    public void testNewRequest() {

	ClientModel mockModel = mock(ClientModel.class);

	controller = new ClientWindowController(mockModel, null, builder);

	controller.newRequest();

	verify(mockModel, atLeast(2)).getMethod();

    }

    @Test
    public void testSendRequestModelUpdated() {

	ClientModel m = controller.getModel();
	m.setBody("body");
	m.setBodySet(true);
	m.setFollowRedirects(true);
	m.setMethod(HttpMethod.POST);
	m.setUrl("http://localhost");

	controller.sendRequest();

	verify(mockWindow).viewRequest(any(HttpRequest.class));
    }
}
