package tfdhs.core.ui;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import tfdhs.api.Builder;
import tfdhs.api.HttpRequest;
import tfdhs.api.HttpResponse;
import tfdhs.core.ClientController;
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
	// TODO Auto-generated method stub

    }

    @Override
    public void resetView(ClientModel model) {

	urlField.setText(model.getUrl());
	methodComboBox.setSelectedItem(model.getMethod());
	followRedirectsCheckBox.setSelected(model.isFollowRedirects());
	headersTable.setModel(model.getHeaders());
	selectBodyBox.setSelected(model.isBodySet());
	bodyTextArea.setText(model.getBody());
	viewButtonGroup.setSelected(viewRequestButton.getModel(), true);

    }

    @Override
    public void viewRequest(HttpRequest request) {

    }

    @Override
    public void viewResponse(HttpResponse response) {
	StringBuilder responseText = new StringBuilder();

	if (response.getStatus() > 0) {

	    // if (response.getHeaders().containsKey(null)) {
	    // responseText.append(String.format("%s\n", response.getHeaders()
	    // .get(null)));
	    // }
	    //
	    // for (Entry<String, String> header : response.getHeaders()
	    // .entrySet()) {
	    // if (header.getKey() == null) {
	    // continue;
	    // }
	    // responseText.append(String.format("%s: %s\n", header.getKey(),
	    // header.getValue()));
	    // }
	    // responseText.append("\n");
	    // String body = response.getBody();
	    // responseText.append(body == null ? "" : body);
	    // viewTextPane.setCaretPosition(0);
	}

	viewTextPane.setText(responseText.toString());
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
	headerScrollPane.setVisible(false);
	headersTable.getColumnModel().getColumn(0)
		.setHeaderValue("Header Name");
	headersTable.getColumnModel().getColumn(1)
		.setHeaderValue("Header Value");

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

}
