package tfdhs.core.ui.listeners;

import javax.swing.text.JTextComponent;

import tfdhs.core.ClientModel;

public final class BodyTextDocumentListener implements
        javax.swing.event.DocumentListener {
    private final ClientModel model;
    private final JTextComponent text;

    public BodyTextDocumentListener(ClientModel model, JTextComponent text) {
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