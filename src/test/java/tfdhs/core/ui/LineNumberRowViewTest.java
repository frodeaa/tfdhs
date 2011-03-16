package tfdhs.core.ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import org.junit.Before;
import org.junit.Test;

import tfdhs.core.ui.LineNumberRowView.Alignment;

public class LineNumberRowViewTest {

    private JTextComponent mockTextSource;
    private Document mockDocument;
    private Element mockElement;
    private Font font;

    @Before
    public void setUp() {
	font = new Font(Font.SANS_SERIF, Font.PLAIN, 11);

	mockDocument = mock(Document.class);
	mockElement = mock(Element.class);

	mockTextSource = mock(JTextComponent.class);
	when(mockTextSource.getFont()).thenReturn(font);
	when(mockTextSource.getDocument()).thenReturn(mockDocument);
	when(mockDocument.getDefaultRootElement()).thenReturn(mockElement);

    }

    @Test
    public void testDocumentListener() throws InterruptedException {
	final int[] counts = { 0, 0 };

	LineNumberRowView lineRowView = new LineNumberRowView(mockTextSource) {

	    public void repaint() {
		counts[0]++;
	    }

	    protected void updatePreferredWith() {
		counts[1]++;
	    }

	};

	verify(mockDocument).addDocumentListener(lineRowView);

	Dimension mockDim = mock(Dimension.class);
	when(mockTextSource.getPreferredSize()).thenReturn(mockDim);

	when(mockDim.getHeight()).thenReturn(1.0);
	lineRowView.removeUpdate(null);

	when(mockDim.getHeight()).thenReturn(2.0);
	lineRowView.insertUpdate(null);

	when(mockDim.getHeight()).thenReturn(3.0);
	lineRowView.changedUpdate(null);

	// The update should only occur if the height has change
	assertEquals("Paint called after document change", 2, counts[0]);
	assertEquals("PreferredWidth update", 2, counts[1]);

    }

    @Test
    public void testSetBorderCap() {

	final int[] counts = { 0 };

	Dimension mockDim = mock(Dimension.class);
	when(mockTextSource.getPreferredSize()).thenReturn(mockDim);

	LineNumberRowView lineRowView = new LineNumberRowView(mockTextSource) {

	    protected void updatePreferredWith() {
		counts[0]++;
	    }

	};

	lineRowView.setBorderGap(1);
	lineRowView.setBorderGap(2);
	lineRowView.setBorderGap(3);

	// 2 cals in constructor.
	assertEquals("Preferred with not updated after seting the border gap",
		3 + 2, counts[0]);

    }

    @Test
    public void testSetMinDigitsWidth() {

	final int[] counts = { 0 };

	LineNumberRowView lineRowView = new LineNumberRowView(mockTextSource) {

	    protected void updatePreferredWith() {
		counts[0]++;
	    }

	};

	lineRowView.setMinDigitsWidth(1);
	lineRowView.setMinDigitsWidth(1);
	lineRowView.setMinDigitsWidth(1);

	assertEquals(
		"Preferred with not updated after seting the minimum digits width",
		Alignment.values().length + 2, counts[0]);

    }

    @Test
    public void testUpdatePreferredWidth() {

	Dimension mockDim = mock(Dimension.class);
	when(mockTextSource.getPreferredSize()).thenReturn(mockDim);
	LineNumberRowView lineRowView = new LineNumberRowView(mockTextSource);
	
	
	lineRowView.updatePreferredWith();
    }

}
