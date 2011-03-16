package tfdhs.core.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

/**
 * This class will display the line numbers for a text component. The text
 * component must use the same line height for each line.
 * 
 * @author frode
 * 
 */
public class LineNumberRowView extends JComponent implements DocumentListener {

    /** The alignment for the numbers shown in the number column. */
    enum Alignment {

	left(0.0f), center(0.5f), right(1.0f);

	Alignment(float value) {
	    this.value = value;
	}

	private float value;

	public float value() {
	    return value;
	}

    }

    /** Default left/right margin width. */
    public static final int DEFAULT_BORDER_GAP = 5;

    /** Default number of digits to create width for. */
    public static final int DEFAULT_DIGITS_WIDTH = 4;

    /** Default alignment. */
    public static final Alignment DEFAULT_ALIGNMENT = Alignment.right;

    /**
     * Create a new LineNumberRowView.
     * 
     * @param source
     *            the source to show line numbers for.
     * @param alignment
     *            the line number alignment.
     * @return new LineNumberRowView.
     */
    public static LineNumberRowView newNumberComponentView(
	    JTextComponent source, Alignment alignment) {
	LineNumberRowView cv = new LineNumberRowView(source);
	cv.setAlignment(alignment);
	return cv;
    }

    private final JTextComponent source;
    private Alignment alignment;

    private int minDigitsWidth;
    private int currentDigitsWidth = 0;
    private int currentHeight = 0;
    private HashMap<String, FontMetrics> fonts;

    /**
     * Create a new instance.
     * 
     * @param source
     *            the source to create line numbers from.
     */
    public LineNumberRowView(JTextComponent source) {
	super();
	this.source = source;
	setFont(source.getFont());
	setAlignment(DEFAULT_ALIGNMENT);
	setMinDigitsWidth(DEFAULT_DIGITS_WIDTH);
	setBorderGap(DEFAULT_BORDER_GAP);
	source.getDocument().addDocumentListener(this);
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
	updateLineNumbersLater();
    }

    @Override
    public void insertUpdate(DocumentEvent event) {
	updateLineNumbersLater();
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
	updateLineNumbersLater();
    }

    /**
     * Refresh the line numbers shown in the component asynchronously.
     */
    protected void updateLineNumbersLater() {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		int newHeight = source.getPreferredSize().height;
		if (newHeight != currentHeight) {
		    updatePreferredWith();
		    repaint();
		    currentHeight = newHeight;
		}
	    }
	});

    }

    /**
     * @param alignment
     *            the alignment to use when displaying the line numbers.
     */
    public void setAlignment(Alignment alignment) {
	this.alignment = alignment;
    }

    /**
     * The border gap is used to calculate the left and right insets.
     * 
     * @param gap
     */
    public void setBorderGap(int gap) {
	setBorder(new CompoundBorder(OUTER, BorderFactory.createEmptyBorder(0,
		gap, 0, gap)));
	updatePreferredWith();
    }

    public void setMinDigitsWidth(int minDigitsWidth) {
	this.minDigitsWidth = minDigitsWidth;
	updatePreferredWith();
    }

    protected void updatePreferredWith() {

	Element root = source.getDocument().getDefaultRootElement();
	int lineCount = root.getElementCount();
	int digitsWidth = Math.max(String.valueOf(lineCount).length(),
		minDigitsWidth);

	if (currentDigitsWidth != digitsWidth) {
	    currentDigitsWidth = digitsWidth;

	    updateWidth(calculateWidth(currentDigitsWidth));
	}

    }

    /**
     * Calculate the width needed to display a number of digits.
     * 
     * @param digitsCount
     *            the number of digits to calculate width for.
     * @return the width needed to display the number of digits.
     */
    protected int calculateWidth(int digitCount) {

	FontMetrics fontMetrics = getFontMetrics(getFont());
	int width = fontMetrics.charWidth('0') * digitCount;

	Insets border = getInsets();
	return border.left + width + border.right;

    }

    /**
     * Update the component width
     * 
     * @param width
     *            the new width to set.
     */
    protected void updateWidth(int width) {

	Dimension dim = getPreferredSize();
	dim.setSize(width, HEIGHT);
	setPreferredSize(dim);
	setSize(dim);

    }

    /**
     * Draw the line numbers
     */
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);

	Rectangle clip = g.getClipBounds();
	int startLine = source.viewToModel(new Point(0, clip.y));
	int endLine = source.viewToModel(new Point(0, clip.y + clip.height));

	drawLineNumbers(g, startLine, endLine);
    }

    /**
     * Draw line numbers in on a Graphics.
     * 
     * @param g
     *            the Graphics to draw on.
     * @param startLine
     *            the start offset to start drawing line numbers.
     * @param endLine
     *            the end offset to stop drawing line numbers.
     */
    protected void drawLineNumbers(Graphics g, int startLine, int endLine) {
	Insets insets = getInsets();
	int width = getSize().width - insets.right;

	FontMetrics fontMetrics = getFontMetrics(getFont());

	while (startLine <= endLine) {
	    try {
		drawLine(g, startLine, getForeground(), fontMetrics, width);
		startLine = Utilities.getRowEnd(source, startLine) + 1;
	    } catch (Exception e) {
	    }
	}
    }

    /**
     * Draw line number on a graphics.
     * 
     * @param g
     *            the graphics to draw on.
     * @param line
     *            the line and value to draw.
     * @param color
     *            the color to use.
     * @param fontMetrics
     *            the metrics to use when calculating the location to draw the
     *            line number.
     * @param width
     *            the available with that the line number can be drawn on.
     * @throws BadLocationException
     */
    protected void drawLine(Graphics g, int line, Color color,
	    FontMetrics fontMetrics, int width) throws BadLocationException {

	String lineNumber = getTextLineNumber(line);
	int stringWidth = fontMetrics.stringWidth(lineNumber);
	int x = getOffsetX(width, stringWidth);
	int y = getOffsetY(line, fontMetrics);
	g.drawString(lineNumber, x, y);

    }

    /*
     * Get the line number to be drawn. The empty string will be returned when a
     * line of text has wrapped.
     */
    protected String getTextLineNumber(int rowStartOffset) {
	Element root = source.getDocument().getDefaultRootElement();
	int index = root.getElementIndex(rowStartOffset);
	Element line = root.getElement(index);

	if (line.getStartOffset() == rowStartOffset)
	    return String.valueOf(index + 1);
	else
	    return "";
    }

    /*
     * Determine the X offset to properly align the line number when drawn
     */
    private int getOffsetX(int availableWidth, int stringWidth) {
	return (int) ((availableWidth - stringWidth) * alignment.value());
    }

    /*
     * Determine the Y offset for the current row
     */
    private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics)
	    throws BadLocationException {
	Rectangle r = source.modelToView(rowStartOffset);
	int lineHeight = fontMetrics.getHeight();
	int y = r.y + r.height;
	int descent = 0;

	// The text needs to be positioned above the bottom of the bounding
	// rectangle based on the descent of the font(s) contained on the row.

	if (r.height == lineHeight) {
	    descent = fontMetrics.getDescent();
	} else {
	    if (fonts == null)
		fonts = new HashMap<String, FontMetrics>();

	    Element root = source.getDocument().getDefaultRootElement();
	    int index = root.getElementIndex(rowStartOffset);
	    Element line = root.getElement(index);

	    for (int i = 0; i < line.getElementCount(); i++) {
		Element child = line.getElement(i);
		AttributeSet as = child.getAttributes();
		String fontFamily = (String) as
			.getAttribute(StyleConstants.FontFamily);
		Integer fontSize = (Integer) as
			.getAttribute(StyleConstants.FontSize);
		String key = fontFamily + fontSize;

		FontMetrics fm = fonts.get(key);

		if (fm == null) {
		    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
		    fm = source.getFontMetrics(font);
		    fonts.put(key, fm);
		}

		descent = Math.max(descent, fm.getDescent());
	    }
	}

	return y - descent;
    }

    private final static int HEIGHT = Integer.MAX_VALUE - 1000000;
    private final static Border OUTER = BorderFactory.createMatteBorder(0, 0,
	    0, 0, Color.GRAY);

}
