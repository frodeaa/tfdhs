package tfdhs.core.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ClientController;
import tfdhs.core.ClientModel;

public class HttpClientWindowTest {

    private HttpClientWindow window;
    private ClientController mockController;
    private ClientModel model;

    @Before
    public void setUp() {
	mockController = mock(ClientController.class);
	model = new ClientModel();
	window = new HttpClientWindow();
    }

    @Test
    public void testBuildPanel() {

	assertNotNull("Window panel was null", window.build());

    }

    @Test
    public void bind() {

	window.bind(mockController);

    }

    @Test
    public void resetView() {

	window.resetView(model);
	JPanel parent = window.build();

	assertUrlField(parent);
	assertMethodBox(parent);
	assertFollowRedirects(parent);

    }

    protected void assertUrlField(JPanel parent) {

	Component comp = getChildNamed(parent, "urlField");
	assertTrue(comp instanceof JTextField);

	JTextField urlField = (JTextField) comp;

	model.setUrl("http://someurl");

	window.resetView(model);

	assertEquals("urlfield value", "http://someurl", urlField.getText());

    }

    protected void assertMethodBox(JPanel parent) {

	Component comp = getChildNamed(parent, "methodComboBox");
	assertTrue(comp instanceof JComboBox);

	JComboBox urlField = (JComboBox) comp;

	model.setMethod(HttpMethod.CONNECT);

	window.resetView(model);

	assertEquals("selected method value", HttpMethod.CONNECT,
		urlField.getSelectedItem());

    }

    protected void assertFollowRedirects(JPanel parent) {

	Component comp = getChildNamed(parent, "followRedirectsCheckBox");
	assertTrue(comp instanceof JCheckBox);

	JCheckBox urlField = (JCheckBox) comp;

	model.setFollowRedirects(false);

	window.resetView(model);

	assertEquals("selected method value", model.isFollowRedirects(),
		urlField.isSelected());

	model.setFollowRedirects(true);

	window.resetView(model);

	assertEquals("selected method value", model.isFollowRedirects(),
		urlField.isSelected());

    }

    @Test
    public void viewRequest() {

	HttpRequest mockRequest = mock(HttpRequest.class);

	window.viewRequest(mockRequest);

    }

    public static Component getChildNamed(Component parent, String name) {
	if (name.equals(parent.getName())) {
	    return parent;
	}

	if (parent instanceof Container) {
	    Component[] children = ((Container) parent).getComponents();

	    for (int i = 0; i < children.length; ++i) {
		Component child = getChildNamed(children[i], name);
		if (child != null) {
		    return child;
		}
	    }
	}

	return null;
    }

    @Test
    public void viewResponseFail() {

	HttpResponse mockResponse = mock(HttpResponse.class);

	when(mockResponse.getStatus()).thenReturn(-1);

	window.viewResponse(mockResponse);

    }

}
