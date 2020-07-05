package model;

import java.awt.Color;
import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;

public class Line extends PaintObject
{

	public Line(Color color, Point startPoint, Point endPoint)
	{
		super(color, startPoint, endPoint);
	}

	@Override
	public void draw(GraphicsContext gc)
	{
		gc.setStroke(Awt2Fx(this.myColor));
		gc.strokeLine(this.myStartPoint.getX(), this.myStartPoint.getY(), this.myEndPoint.getX(), this.myEndPoint.getY());
	}
	
	@Override
	public boolean isImage()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getStartX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getStartY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEndX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEndY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
