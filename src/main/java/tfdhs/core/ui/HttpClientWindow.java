package tfdhs.core.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import tfdhs.api.Builder;
import tfdhs.api.HttpMethod;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ClientController;
import tfdhs.core.ClientController.Viewstate;
import tfdhs.core.ClientModel;

/**
 * Client panel.
 * 
 * @author frode
 * 
 */
public class HttpClientWindow implements ClientWindow,
	Builder<javax.swing.JPanel> {

    public HttpClientWindow() {
	this.tableModel = new ListValueMapTableModel();
	this.panel = new JPanel();
	initComponents();
	layoutComponents();
    }

    @Override
    public JPanel build() {
	return panel;
    }

    @Override
    public void bind(ClientController controller) {

	methodComboBox.setModel(new javax.swing.DefaultComboBoxModel(HttpMethod
		.values()));

	ClientModel model = controller.getModel();

	resetView(model);

	updateUrlPropertyDocumentListener(model, urlField);
	methodChangeListener(model, methodComboBox);
	followRedirectsChangeListener(model, followRedirectsCheckBox);
	bodyInputChangeListener(selectBodyBox, bodyTextArea, bodyScrollPane);
	updateBodyDocumentListener(model, bodyTextArea);
	sendRequestAction(controller, sendButton);

	viewRequestAction(controller, viewRequestButton);
	viewResponseAction(controller, viewResponseButton);

	addHeaderButton.setAction(new AbstractAction("+") {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		int newRow = tableModel.addRow();
		headersTable.setRowSelectionInterval(newRow, newRow);
	    }
	});

	removeHeaderButton.setAction(new AbstractAction("-") {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		int rowSelected = headersTable.getSelectedRow();
		if (rowSelected > -1) {
		    tableModel.removeRow(rowSelected);
		    int newSelect = Math.min(Math.max(0, rowSelected - 1),
			    Math.min(rowSelected, tableModel.getRowCount()));
		    if (tableModel.getRowCount() > 0) {
			headersTable.setRowSelectionInterval(newSelect,
				newSelect);
		    }
		}
	    }
	});

    }

    @Override
    public void resetView(ClientModel model) {

	urlField.setText(model.getUrl());
	methodComboBox.setSelectedItem(model.getMethod());
	followRedirectsCheckBox.setSelected(model.isFollowRedirects());

	tableModel.updateModel(model.getHeaderFields());
	selectBodyBox.setSelected(model.isBodySet());
	bodyTextArea.setText(model.getBody());
	viewButtonGroup.setSelected(viewRequestButton.getModel(), true);

    }

    protected static DocumentListener updateUrlPropertyDocumentListener(
	    final ClientModel model, final JTextComponent text) {

	DocumentListener listener = new UrlFieldDocumentListener(model, text);
	text.getDocument().addDocumentListener(listener);
	return listener;

    }

    protected static ItemListener methodChangeListener(final ClientModel model,
	    final JComboBox combobox) {

	ItemListener listener = new MethodSelectListener(combobox, model);
	combobox.addItemListener(listener);
	return listener;

    }

    protected static ChangeListener followRedirectsChangeListener(
	    final ClientModel model, final JCheckBox checkBox) {

	ChangeListener listener = new FollowRedirectsChangeListener(checkBox,
		model);
	checkBox.addChangeListener(listener);
	return listener;

    }

    protected static ChangeListener bodyInputChangeListener(
	    final JCheckBox checkBox, final JTextComponent bodyText,
	    final JScrollPane bodyScollPane) {

	ChangeListener listener = new BodySelectChangeListener(bodyText,
		bodyScollPane, checkBox);
	checkBox.addChangeListener(listener);
	return listener;

    }

    protected static DocumentListener updateBodyDocumentListener(
	    final ClientModel model, final JTextComponent text) {

	DocumentListener listener = new BodyTextDocumentListener(model, text);
	text.getDocument().addDocumentListener(listener);
	return listener;
    }

    protected static Action sendRequestAction(
	    final ClientController controller, AbstractButton button) {

	Action sendRequest = new SendRequestAction(controller);
	button.addActionListener(sendRequest);
	return sendRequest;

    }

    protected static Action viewRequestAction(
	    final ClientController controller, final AbstractButton button) {

	Action action = new ViewAction(button.getText(), controller,
		Viewstate.request);
	button.setAction(action);
	return action;

    }

    protected static Action viewResponseAction(
	    final ClientController controller, final AbstractButton button) {

	Action action = new ViewAction(button.getText(), controller,
		Viewstate.response);
	button.setAction(action);
	return action;

    }

    @Override
    public void viewRequest(HttpRequest request) {
	viewButtonGroup.setSelected(viewRequestButton.getModel(), true);

	StringBuilder requestText = new StringBuilder();

	String url = request.getUrl();
	if (url != null && !url.isEmpty()) {

	    requestText.append(request.getMethod());
	    requestText.append(' ');
	    requestText.append(url);

	    for (Entry<String, List<String>> header : request.getHeaders()
		    .entrySet()) {
		requestText.append(String.format("%s: %s\n", header.getKey(),
			header.getValue()));
	    }

	    requestText.append("\n\n");
	    String body = request.getBody();
	    requestText.append(body == null ? "" : body);

	}

	viewTextPane.setText(requestText.toString());
	viewTextPane.setCaretPosition(0);
    }

    @Override
    public void viewResponse(HttpResponse response) {
	StringBuilder responseText = new StringBuilder();

	if (response.getStatus() > 0) {

	    if (response.getHeaders().containsKey(null)) {
		responseText.append(String.format("%s\n", response.getHeaders()
			.get(null)));
	    }

	    for (Entry<String, List<String>> header : response.getHeaders()
		    .entrySet()) {
		if (header.getKey() == null) {
		    continue;
		}
		responseText.append(String.format("%s: %s\n", header.getKey(),
			header.getValue()));
	    }
	    responseText.append("\n");
	    String body = response.getBody();
	    responseText.append(body == null ? "" : body);
	}

	viewTextPane.setText(responseText.toString());
	viewTextPane.setCaretPosition(0);
    }

    private void initComponents() {
	urlLabel = new javax.swing.JLabel();
	urlField = new javax.swing.JTextField();
	methodComboBox = new javax.swing.JComboBox();
	followRedirectsCheckBox = new javax.swing.JCheckBox();
	sendButton = new javax.swing.JButton();
	headerScrollPane = new javax.swing.JScrollPane();
	headersTable = new javax.swing.JTable();
	editHeaderToolbar = new javax.swing.JToolBar();
	addHeaderButton = new javax.swing.JButton();
	removeHeaderButton = new javax.swing.JButton();
	selectBodyBox = new javax.swing.JCheckBox();
	bodyScrollPane = new javax.swing.JScrollPane();
	bodyTextArea = new javax.swing.JTextArea();
	viewToolbar = new javax.swing.JToolBar();
	viewRequestButton = new javax.swing.JToggleButton();
	viewResponseButton = new javax.swing.JToggleButton();
	viewScroler = new javax.swing.JScrollPane();
	viewTextPane = new JTextPane();
	viewButtonGroup = new javax.swing.ButtonGroup();

	urlLabel.setText("URL:");
	urlLabel.setName("urlLabel");

	urlField.setText("");
	urlField.setName("urlField");

	methodComboBox.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
	methodComboBox.setName("methodComboBox");

	followRedirectsCheckBox.setText("Follow Redirects");
	followRedirectsCheckBox.setName("followRedirectsCheckBox");

	sendButton.setText("Send");
	sendButton.setName("sendButton");

	viewButtonGroup.add(viewRequestButton);
	viewButtonGroup.add(viewResponseButton);

	headerScrollPane.setName("headerScrollPane");

	headersTable.setModel(new javax.swing.table.DefaultTableModel(2, 2));
	headersTable.setName("headersTable");
	headerScrollPane.setViewportView(headersTable);
	headersTable.setModel(tableModel);

	editHeaderToolbar.setFloatable(false);
	editHeaderToolbar.setRollover(true);
	editHeaderToolbar.setName("editHeaderToolbar");

	addHeaderButton.setText("+");
	addHeaderButton.setFocusable(false);
	addHeaderButton
		.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	addHeaderButton.setName("addHeaderButton");
	addHeaderButton
		.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	editHeaderToolbar.add(addHeaderButton);

	removeHeaderButton.setText("-");
	removeHeaderButton.setFocusable(false);
	removeHeaderButton
		.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	removeHeaderButton.setName("removeHeaderButton");
	removeHeaderButton
		.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	editHeaderToolbar.add(removeHeaderButton);

	selectBodyBox.setText("Body:");
	selectBodyBox.setName("selectBodyBox");

	bodyScrollPane.setName("bodyScrollPane");

	bodyTextArea.setColumns(20);
	bodyTextArea.setRows(5);
	bodyTextArea.setName("bodyTextArea");
	bodyScrollPane.setViewportView(bodyTextArea);
	bodyScrollPane.setVisible(selectBodyBox.isSelected());

	viewToolbar.setFloatable(false);
	viewToolbar.setRollover(true);
	viewToolbar.setName("viewToolbar");

	viewRequestButton.setText("Request");
	viewRequestButton.setFocusable(false);
	viewRequestButton
		.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	viewRequestButton.setName("viewRequestButton");
	viewRequestButton
		.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	viewToolbar.add(viewRequestButton);

	viewResponseButton.setText("Response");
	viewResponseButton.setFocusable(false);
	viewResponseButton
		.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	viewResponseButton.setName("viewResponseButton");
	viewResponseButton
		.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	viewToolbar.add(viewResponseButton);

	viewScroler.setName("viewScroler");

	viewTextPane.setContentType("text/xml; charset=UTF-8");
	viewTextPane.getDocument().putProperty("IgnoreCharsetDirective",
		new Boolean(true));
	viewTextPane.setName("viewTextPane");
	viewScroler.setViewportView(viewTextPane);
    }

    private void layoutComponents() {
	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
	panel.setLayout(layout);
	layout.setHorizontalGroup(layout
		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
							layout.createSequentialGroup()
								.addComponent(
									urlLabel)
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									urlField,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									576,
									Short.MAX_VALUE))
						.addGroup(
							layout.createSequentialGroup()
								.addComponent(
									methodComboBox,
									javax.swing.GroupLayout.PREFERRED_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									followRedirectsCheckBox)
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED,
									298,
									Short.MAX_VALUE)
								.addComponent(
									sendButton)))
				.addContainerGap())
		.addComponent(headerScrollPane,
			javax.swing.GroupLayout.DEFAULT_SIZE, 622,
			Short.MAX_VALUE)
		.addGroup(
			javax.swing.GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(
							bodyScrollPane,
							javax.swing.GroupLayout.DEFAULT_SIZE,
							610, Short.MAX_VALUE)
						.addGroup(
							layout.createSequentialGroup()
								.addComponent(
									selectBodyBox)
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED,
									496,
									Short.MAX_VALUE)
								.addComponent(
									editHeaderToolbar,
									javax.swing.GroupLayout.PREFERRED_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addContainerGap())
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(viewToolbar,
					javax.swing.GroupLayout.DEFAULT_SIZE,
					610, Short.MAX_VALUE).addContainerGap())
		.addComponent(viewScroler,
			javax.swing.GroupLayout.DEFAULT_SIZE, 622,
			Short.MAX_VALUE));
	layout.setVerticalGroup(layout
		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(urlLabel)
						.addComponent(
							urlField,
							javax.swing.GroupLayout.PREFERRED_SIZE,
							javax.swing.GroupLayout.DEFAULT_SIZE,
							javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(
							methodComboBox,
							javax.swing.GroupLayout.PREFERRED_SIZE,
							javax.swing.GroupLayout.DEFAULT_SIZE,
							javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(
							followRedirectsCheckBox)
						.addComponent(sendButton))
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(headerScrollPane,
					javax.swing.GroupLayout.PREFERRED_SIZE,
					94,
					javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(
							editHeaderToolbar,
							javax.swing.GroupLayout.PREFERRED_SIZE,
							25,
							javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(selectBodyBox))
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(bodyScrollPane,
					javax.swing.GroupLayout.PREFERRED_SIZE,
					javax.swing.GroupLayout.DEFAULT_SIZE,
					javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(viewToolbar,
					javax.swing.GroupLayout.PREFERRED_SIZE,
					25,
					javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(viewScroler,
					javax.swing.GroupLayout.DEFAULT_SIZE,
					182, Short.MAX_VALUE)));
    }

    private final javax.swing.JPanel panel;
    private javax.swing.JButton addHeaderButton;
    private javax.swing.JScrollPane bodyScrollPane;
    private javax.swing.JTextArea bodyTextArea;
    private javax.swing.JToolBar editHeaderToolbar;
    private javax.swing.JCheckBox followRedirectsCheckBox;
    private javax.swing.JTable headersTable;
    private javax.swing.JScrollPane headerScrollPane;
    private javax.swing.JComboBox methodComboBox;
    private javax.swing.JButton removeHeaderButton;
    private javax.swing.JCheckBox selectBodyBox;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField urlField;
    private javax.swing.JLabel urlLabel;
    private javax.swing.JToggleButton viewRequestButton;
    private javax.swing.JToggleButton viewResponseButton;
    private javax.swing.JScrollPane viewScroler;
    private javax.swing.JTextPane viewTextPane;
    private javax.swing.JToolBar viewToolbar;
    private javax.swing.ButtonGroup viewButtonGroup;
    private final ListValueMapTableModel tableModel;

    private static final class ViewAction extends AbstractAction {
	private final ClientController controller;
	private final Viewstate view;

	private ViewAction(String name, ClientController controller,
		Viewstate view) {
	    super(name);
	    this.view = view;
	    this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
		    controller.view(view);
		}
	    });
	}
    }

    private static final class SendRequestAction extends AbstractAction {
	private final ClientController controller;

	private SendRequestAction(ClientController controller) {
	    this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

		@Override
		protected Void doInBackground() throws Exception {
		    controller.sendRequest();
		    return null;
		}
	    };
	    SwingUtilities.invokeLater(worker);
	}
    }

    private static final class BodySelectChangeListener implements
	    javax.swing.event.ChangeListener {
	private final JTextComponent bodyText;
	private final JScrollPane bodyScollPane;
	private final JCheckBox checkBox;

	private BodySelectChangeListener(JTextComponent bodyText,
		JScrollPane bodyScollPane, JCheckBox checkBox) {
	    this.bodyText = bodyText;
	    this.bodyScollPane = bodyScollPane;
	    this.checkBox = checkBox;
	}

	@Override
	public void stateChanged(javax.swing.event.ChangeEvent e) {
	    bodyText.setText("");
	    bodyScollPane.setVisible(checkBox.isSelected());
	    bodyScollPane.getParent().validate();
	}
    }

    private static final class FollowRedirectsChangeListener implements
	    javax.swing.event.ChangeListener {
	private final JCheckBox checkBox;
	private final ClientModel model;

	private FollowRedirectsChangeListener(JCheckBox checkBox,
		ClientModel model) {
	    this.checkBox = checkBox;
	    this.model = model;
	}

	@Override
	public void stateChanged(javax.swing.event.ChangeEvent e) {
	    model.setFollowRedirects(checkBox.isSelected());
	}
    }

    private static final class MethodSelectListener implements ItemListener {
	private final JComboBox combobox;
	private final ClientModel model;

	private MethodSelectListener(JComboBox combobox, ClientModel model) {
	    this.combobox = combobox;
	    this.model = model;
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
	    model.setMethod((HttpMethod) combobox.getSelectedObjects()[0]);
	}
    }

    private static final class BodyTextDocumentListener implements
	    javax.swing.event.DocumentListener {
	private final ClientModel model;
	private final JTextComponent text;

	private BodyTextDocumentListener(ClientModel model, JTextComponent text) {
	    this.model = model;
	    this.text = text;
	}

	@Override
	public void removeUpdate(javax.swing.event.DocumentEvent event) {
	    changedUpdate(event);
	}

	@Override
	public void insertUpdate(javax.swing.event.DocumentEvent event) {
	    changedUpdate(event);
	}

	@Override
	public void changedUpdate(javax.swing.event.DocumentEvent event) {
	    model.setBody(text.getText());
	}
    }

    private static final class UrlFieldDocumentListener implements
	    javax.swing.event.DocumentListener {
	private final ClientModel model;
	private final JTextComponent text;

	private UrlFieldDocumentListener(ClientModel model, JTextComponent text) {
	    this.model = model;
	    this.text = text;
	}

	@Override
	public void removeUpdate(javax.swing.event.DocumentEvent event) {
	    changedUpdate(event);
	}

	@Override
	public void insertUpdate(javax.swing.event.DocumentEvent event) {
	    changedUpdate(event);
	}

	@Override
	public void changedUpdate(javax.swing.event.DocumentEvent event) {
	    model.setUrl(text.getText());
	}
    }

}
