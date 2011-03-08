package tfdhs.core.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tfdhs.api.AuthenticatorResolver;

/**
 * Display a dialog where the user can enter username/password.
 * 
 * @author frode
 * 
 */
public class DialogAuthenticatorResolver implements AuthenticatorResolver {

    private final Window parent;

    /**
     * Create a new Authenticator resolver.
     * 
     * @param parent
     *            used to determine which fame this dialog is places in.
     */
    public DialogAuthenticatorResolver(Window parent) {
	this.parent = parent;
	initComponents();
    }

    private void initComponents() {
	usernameField = new JTextField(20);
	passwordField = new JPasswordField(20);
	optionPane = new JOptionPane(new Object[] {

	layout(2, new JLabel("Username:"), usernameField, new JLabel(
		"Password:"), passwordField) }, JOptionPane.PLAIN_MESSAGE,
		JOptionPane.OK_CANCEL_OPTION);

	dialog = optionPane.createDialog(parent,
		"Username and password required");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PasswordAuthentication authenticate(Authenticator authenticator) {

	dialog.setVisible(true);
	dialog.dispose();

	int value = ((Integer) optionPane.getValue()).intValue();
	if (value == JOptionPane.OK_OPTION) {
	    return new PasswordAuthentication(usernameField.getText(),
		    passwordField.getPassword());
	}

	return null;
    }

    protected static JComponent layout(int cols, JComponent... components) {
	JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	int x = 0;
	int y = 0;
	for (JComponent component : components) {
	    c.gridx = x;
	    c.gridy = y;
	    x++;
	    if (x == cols) {
		x = 0;
		y++;
	    }
	    panel.add(component, c);
	}
	return panel;
    }

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JOptionPane optionPane;
    private JDialog dialog;

}
