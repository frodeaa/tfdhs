package tfdhs.core.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import tfdhs.core.ClientController;
import tfdhs.core.ClientController.Viewstate;

public final class ViewAction extends AbstractAction {
    private final ClientController controller;
    private final Viewstate view;

    public ViewAction(String name, ClientController controller, Viewstate view) {
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