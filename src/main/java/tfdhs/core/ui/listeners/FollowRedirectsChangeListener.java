package tfdhs.core.ui.listeners;

import javax.swing.JCheckBox;

import tfdhs.core.ClientModel;

public final class FollowRedirectsChangeListener implements
        javax.swing.event.ChangeListener {
    private final JCheckBox checkBox;
    private final ClientModel model;

    public FollowRedirectsChangeListener(JCheckBox checkBox,
    	ClientModel model) {
        this.checkBox = checkBox;
        this.model = model;
    }

    @Override
    public void stateChanged(javax.swing.event.ChangeEvent e) {
        model.setFollowRedirects(checkBox.isSelected());
    }
}