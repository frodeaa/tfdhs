package tfdhs.core.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import tfdhs.core.ClientController;
import tfdhs.core.ui.ListValueMapTableModel;

public final class SendRequestAction extends AbstractAction {
    private final ClientController controller;
    private final ListValueMapTableModel model;

    public SendRequestAction(ClientController controller,
	    ListValueMapTableModel model) {
	this.controller = controller;
	this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

	    @Override
	    protected Void doInBackground() throws Exception {
		controller.getModel().setHeaderFields(model.getData());
		controller.sendRequest();
		return null;
	    }
	};
	SwingUtilities.invokeLater(worker);
    }
}