package tfdhs.core.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import tfdhs.core.ClientController;

public final class SendRequestAction extends AbstractAction {
    private final ClientController controller;

    public SendRequestAction(ClientController controller) {
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