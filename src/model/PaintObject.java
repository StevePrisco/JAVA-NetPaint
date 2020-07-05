package model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;


/**
 * PaintObject
 * 
 * PaintObject is the superclass of paint objects Oval, Rectangle, Line, and
 * Picture that can be drawn using a Color, two Points, and some Canvas methods.   
 * 
 * @author Steve Prisco
 *
 */
public abstract class PaintObject implements Serializable
{
	protected Color myColor;
	protected Point myStartPoint;
	protected Point myEndPoint;
	protected double startX, startY, endX, endY;
	
	public PaintObject(Color color, Point startPoint, Point endPoint)
	{
		this.myColor = color;
		this.myStartPoint = startPoint;
		this.myEndPoint = endPoint;
	}
	
	public abstract void draw(GraphicsContext gc);
	
	public abstract boolean isImage();
	
	public abstract double getStartX();
	public abstract double getStartY();
	public abstract double getEndX();
	public abstract double getEndY();
	
	public static javafx.scene.paint.Color Awt2Fx(Color awtColor)
	{
	    int r = awtColor.getRed();
	    int g = awtColor.getGreen();
	    int b = awtColor.getBlue();
	    javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b);
	    return fxColor;
	}
}