package tfdhs.core.ui.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import tfdhs.api.HttpMethod;
import tfdhs.core.ClientModel;

public final class MethodSelectListener implements ItemListener {
    private final JComboBox combobox;
    private final ClientModel model;

    public MethodSelectListener(JComboBox combobox, ClientModel model) {
        this.combobox = combobox;
        this.model = model;
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        model.setMethod((HttpMethod) combobox.getSelectedObjects()[0]);
    }
}