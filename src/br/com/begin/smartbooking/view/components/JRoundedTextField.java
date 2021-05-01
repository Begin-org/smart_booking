package br.com.begin.smartbooking.view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import br.com.begin.smartbooking.view.util.ViewUtil;

public class JRoundedTextField extends JPanel {
	
	/** Stroke size. it is recommended to set it to 1 for better view */
    protected int strokeSize = 1;
    /** Color of shadow */
    protected Color shadowColor = Color.black;
    /** Sets if it drops shadow */
    protected boolean shady = false;
    /** Sets if it has an High Quality view */
    protected boolean highQuality = true;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs;
    /** Distance between shadow border and opaque textfield border */
    protected int shadowGap = 5;
    /** The offset of shadow.  */
    protected int shadowOffset = 4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 150;
    
    private JTextField txtTextField;

	//FOLLOWING CODES GOES HERE
    
    public JTextField getTxtTextField() {
		return txtTextField;
	}

	public void setTxtTextField(JTextField txtTextField) {
		this.txtTextField = txtTextField;
	}

	public JRoundedTextField(int radius, int width, int height, Color backgroundColor) {
        super();
        setOpaque(false);
        arcs = new Dimension(radius,radius);
        initializeTextField(width, height, backgroundColor);
        initializeComponents();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(),
    shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        //Sets antialiasing if HQ.
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //Draws shadow borders if any.
        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(
                    shadowOffset,// X position
                    shadowOffset,// Y position
                    width - strokeSize - shadowOffset, // width
                    height - strokeSize - shadowOffset, // height
                    arcs.width, arcs.height);// arc Dimension
        } else {
            shadowGap = 1;
        }

        //Draws the rounded opaque textfield with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap,
        height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        //graphics.drawRoundRect(0, 0, width - shadowGap,
        //height - shadowGap, arcs.width, arcs.height);

        //Sets strokes to default, is better.
        graphics.setStroke(new BasicStroke());
    }
    
    public void initializeTextField(int width, int height, Color backgroundColor) {
    	
    	setSize(width, height);
    	setLayout(null);
    	setBackground(backgroundColor);
    }
    
    public void initializeComponents() { 
    	
    	
    	txtTextField = new JTextField();
    	txtTextField.setBorder(null);
    	txtTextField.setBackground(getBackground());
    	txtTextField.setSize( getWidth() / 2 + 180, getHeight() / 2);
    	new ViewUtil().centralize(this, txtTextField);
    	add(txtTextField);
    		
    }
    
    


}
