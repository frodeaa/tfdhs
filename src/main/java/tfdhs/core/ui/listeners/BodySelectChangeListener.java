package tfdhs.core.ui.listeners;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

public final class BodySelectChangeListener implements
        javax.swing.event.ChangeListener {
    private final JTextComponent bodyText;
    private final JScrollPane bodyScollPane;
    private final JCheckBox checkBox;

    public BodySelectChangeListener(JTextComponent bodyText,
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