package tfdhs.core.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Container;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.junit.Before;
import org.junit.Test;

import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ClientController;
import tfdhs.core.ClientController.Viewstate;
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

	when(mockController.getModel()).thenReturn(model);

	window.bind(mockController);

    }

    @Test
    public void testAddNewHeaderRowAction() {

	JButton source = new JButton("Adding");
	JTable mockTable = mock(JTable.class);
	ListValueMapTableModel mockModel = mock(ListValueMapTableModel.class);

	Action action = window.addNewHeaderRowAction(source, mockModel,
		mockTable);

	assertNotNull("Action was null", action);
	assertEquals("Action name", "Adding", source.getText());
	when(mockModel.addRow()).thenReturn(33);

	action.actionPerformed(null);

	verify(mockModel).addRow();
	verify(mockTable).setRowSelectionInterval(33, 33);
    }

    @Test
    public void testAddNewHeaderRowActionButtonActionSet() {

	JButton mockButton = mock(JButton.class);

	Action action = window.addNewHeaderRowAction(mockButton, null, null);

	verify(mockButton).setAction(action);

    }

    @Test
    public void testRemoveSelectedRowAction() {

	JButton source = new JButton("Removing");
	JTable mockTable = mock(JTable.class);
	ListValueMapTableModel mockModel = mock(ListValueMapTableModel.class);

	Action action = window.removeSelectedRowAction(source, mockModel,
		mockTable);

	assertNotNull("Action was null", action);
	assertEquals("Action name", "Removing", source.getText());

	when(mockTable.getSelectedRow()).thenReturn(33);
	when(mockModel.getRowCount()).thenReturn(32);

	action.actionPerformed(null);

	verify(mockModel).removeRow(33);
	verify(mockTable).setRowSelectionInterval(32, 32);

    }

    @Test
    public void testRemoveNone() {

	JButton source = new JButton("Removing");
	JTable mockTable = mock(JTable.class);
	ListValueMapTableModel mockModel = mock(ListValueMapTableModel.class);

	window.removeSelectedRowAction(source, mockModel, mockTable);

	when(mockTable.getSelectedRow()).thenReturn(-1);
    }

    @Test
    public void testRemoveLast() {

	JButton source = new JButton("Removing");
	JTable mockTable = mock(JTable.class);
	ListValueMapTableModel mockModel = mock(ListValueMapTableModel.class);

	window.removeSelectedRowAction(source, mockModel, mockTable);

	when(mockTable.getSelectedRow()).thenReturn(0);
	when(mockModel.getRowCount()).thenReturn(0);

    }

    @Test
    public void resetView() {

	when(mockController.getModel()).thenReturn(model);
	window.bind(mockController);

	window.resetView(model);

	JPanel parent = window.build();
	assertUrlField(parent);
	assertMethodBox(parent);
	assertFollowRedirects(parent);
	assertBody(parent);

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

    protected void assertBody(JPanel parent) {

	Component comp = getChildNamed(parent, "selectBodyBox");
	assertTrue("Expected selectBodyBox", comp instanceof JCheckBox);

	JCheckBox selectBodyBox = (JCheckBox) comp;

	comp = getChildNamed(parent, "bodyTextArea");
	assertTrue("Expected bodyTextArea", comp instanceof JTextComponent);
	JTextComponent bodyTextArea = (JTextComponent) comp;

	assertFalse("Body check selected", selectBodyBox.isSelected());

	model.setBody("somebody");
	model.setBodySet(true);
	window.resetView(model);

	assertTrue("Body check NOT selected", selectBodyBox.isSelected());
	assertEquals("Body value", "somebody", bodyTextArea.getText());
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
    public void testViewResponseFail() {

	HttpResponse mockResponse = mock(HttpResponse.class);

	when(mockResponse.getStatus()).thenReturn(-1);

	window.viewResponse(mockResponse);

	Component comp = getChildNamed(window.build(), "viewTextPane");
	assertTrue("Expected viewTextPane", comp instanceof JTextComponent);
	JTextComponent viewTextPane = (JTextComponent) comp;

	assertEquals("view text", "", viewTextPane.getText());

    }

    @Test
    public void testViewResponseSuccess() {

	HttpResponse mockResponse = mock(HttpResponse.class);

	when(mockResponse.getStatus()).thenReturn(200);
	when(mockResponse.getBody()).thenReturn("body text");

	window.viewResponse(mockResponse);

	Component comp = getChildNamed(window.build(), "viewTextPane");
	assertTrue("Expected viewTextPane", comp instanceof JTextComponent);
	JTextComponent viewTextPane = (JTextComponent) comp;

	assertTrue("view text missing body",
		viewTextPane.getText().contains("body text"));

    }

    @Test
    public void testViewRequest() {

	HttpRequest mockRequest = mock(HttpRequest.class);
	when(mockRequest.getUrl()).thenReturn("anyURL");
	when(mockRequest.getBody()).thenReturn("body text");

	window.viewRequest(mockRequest);

	Component comp = getChildNamed(window.build(), "viewTextPane");
	assertTrue("Expected viewTextPane", comp instanceof JTextComponent);
	JTextComponent viewTextPane = (JTextComponent) comp;

	assertTrue("view text missing body",
		viewTextPane.getText().contains("body text"));

    }

    @Test
    public void testUpdateUrlPropertyDocumentListener() {

	JTextComponent mockComponent = mock(JTextComponent.class);
	Document mockDocument = mock(Document.class);

	when(mockComponent.getDocument()).thenReturn(mockDocument);

	DocumentListener listener = HttpClientWindow
		.updateUrlPropertyDocumentListener(model, mockComponent);

	verify(mockDocument).addDocumentListener(listener);

	when(mockComponent.getText()).thenReturn("url1");
	listener.removeUpdate(null);
	assertEquals("Model url", "url1", model.getUrl());

	when(mockComponent.getText()).thenReturn("url2");
	listener.removeUpdate(null);
	assertEquals("Model url", "url2", model.getUrl());

	when(mockComponent.getText()).thenReturn("url3");
	listener.changedUpdate(null);
	assertEquals("Model url", "url3", model.getUrl());

    }

    @Test
    public void testUpdateBodyDocumentListener() {

	JTextComponent mockComponent = mock(JTextComponent.class);
	Document mockDocument = mock(Document.class);

	when(mockComponent.getDocument()).thenReturn(mockDocument);

	DocumentListener listener = HttpClientWindow
		.updateBodyDocumentListener(model, mockComponent);

	verify(mockDocument).addDocumentListener(listener);

	when(mockComponent.getText()).thenReturn("text1");
	listener.removeUpdate(null);
	assertEquals("Model url", "text1", model.getBody());

	when(mockComponent.getText()).thenReturn("text2");
	listener.removeUpdate(null);
	assertEquals("Model url", "text2", model.getBody());

	when(mockComponent.getText()).thenReturn("text3");
	listener.changedUpdate(null);
	assertEquals("Model url", "text3", model.getBody());

    }

    @Test
    public void testSendRequestAction() throws InterruptedException {

	JButton mockButton = mock(JButton.class);
	ClientModel mockClientModel = mock(ClientModel.class);
	ClientController mockController = mock(ClientController.class);
	ListValueMapTableModel mockModel = mock(ListValueMapTableModel.class);

	Action action = HttpClientWindow.sendRequestAction(mockController,
		mockModel, mockButton);

	assertNotNull("Send request action was null", action);
	verify(mockButton).addActionListener(action);

	Map<String, List<String>> data = Collections.emptyMap();
	when(mockModel.getData()).thenReturn(data);
	when(mockController.getModel()).thenReturn(mockClientModel);

	action.actionPerformed(null);
	Thread.sleep(100);
	verify(mockClientModel).setHeaderFields(data);
	verify(mockController).sendRequest();

    }

    @Test
    public void testViewRequestAction() throws InterruptedException {

	JButton mockButton = mock(JButton.class);
	ClientController mockController = mock(ClientController.class);

	Action action = HttpClientWindow.viewRequestAction(mockController,
		mockButton);

	assertNotNull("View request action was null", action);
	verify(mockButton).setAction(action);

	action.actionPerformed(null);
	Thread.sleep(100);
	verify(mockController).view(Viewstate.request);

    }

    @Test
    public void testViewResponseAction() throws InterruptedException {

	JButton mockButton = mock(JButton.class);
	ClientController mockController = mock(ClientController.class);

	Action action = HttpClientWindow.viewResponseAction(mockController,
		mockButton);

	assertNotNull("View request action was null", action);
	verify(mockButton).setAction(action);

	action.actionPerformed(null);
	Thread.sleep(100);
	verify(mockController).view(Viewstate.response);

    }

}
