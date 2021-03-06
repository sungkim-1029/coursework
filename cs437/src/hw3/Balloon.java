package hw3;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;


public class Balloon {

    private double x;
    private double y;
    private double w;
    private double h;
    private Color colorOval;
    private Color colorLine;
    private Color tempColorOval;
    private Color tempColorLine;
    private Ellipse2D oval;
    private Line2D line;
    private static final Color DEFAULT_COLOR_OVAL = Color.magenta;
    private static final Color DEFAULT_COLOR_LINE = Color.black;
    
    public Balloon() {
        //None
    }
    
    public Balloon(double x, double y, double w, double h, Color colorOval, Color colorLine) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.colorOval = colorOval;
        this.colorLine = colorLine;
        this.tempColorOval = null;
        this.tempColorLine = null;
        oval = new Ellipse2D.Double(this.x, this.y, this.w, this.h);
        line = new Line2D.Double(this.x + (this.w / 2), this.y + this.h, this.x + (this.w / 2), this.y + this.h + 50);
    }
    
    public void changeOvalColorTo() {
    	
    	changeOvalColorTo(null);
    }
    
    
    public void changeOvalColorTo(Color colorOval) {
    	if (colorOval == null) {
        	if (tempColorOval == null) {
        		this.tempColorOval = this.colorOval;
        		this.colorOval = DEFAULT_COLOR_OVAL;
        	} else {
        		this.colorOval = this.tempColorOval;
        		this.tempColorOval = null;
        	}
    	} else {
    		if (this.colorOval == colorOval) {
    			// Do nothing
    		} else {
    			this.colorOval = colorOval;
    		}
    	}
    }
    
    public Color getDefaultColorOval() {
    
        return DEFAULT_COLOR_OVAL;
    }

    
    public Color getDefaultColorLine() {
    
        return DEFAULT_COLOR_LINE;
    }

    public Balloon(double x, double y, double w, double h) {
        this(x, y, w, h, DEFAULT_COLOR_OVAL, DEFAULT_COLOR_LINE);
    }

    public Balloon(double x, double y, double w, double h, Color colorOval) {
        this(x, y, w, h, colorOval, DEFAULT_COLOR_LINE);
    }
    
    public double getX() {
    
        return x;
    }

    
    public void setX(double x) {
    
        this.x = x;
    }

    
    public double getY() {
    
        return y;
    }

    
    public void setY(double y) {
    
        this.y = y;
    }

    
    public double getW() {
    
        return w;
    }

    
    public void setW(double w) {
    
        this.w = w;
    }

    
    public double getH() {
    
        return h;
    }

    
    public void setH(double h) {
    
        this.h = h;
    }

    
    public Color getColorOval() {
    
        return colorOval;
    }

    
    public void setColorOval(Color colorOval) {
    
        this.colorOval = colorOval;
    }

    
    public Color getColorLine() {
    
        return colorLine;
    }

    
    public void setColorLine(Color colorLine) {
    
        this.colorLine = colorLine;
    }

    
    public Ellipse2D getOval() {
    
        return oval;
    }

    
    public void setOval(Ellipse2D oval) {
    
        this.oval = oval;
    }

    
    public Line2D getLine() {
    
        return line;
    }

    
    public void setLine(Line2D line) {
    
        this.line = line;
    }

    
    public Color getTempColorOval() {
    
        return tempColorOval;
    }

    
    public void setTempColorOval(Color tempColorOval) {
    
        this.tempColorOval = tempColorOval;
    }

    
    public Color getTempColorLine() {
    
        return tempColorLine;
    }

    
    public void setTempColorLine(Color tempColorLine) {
    
        this.tempColorLine = tempColorLine;
    }

    

}
