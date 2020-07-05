package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import java.awt.Color;

public class Rectangle extends PaintObject
{

	public Rectangle(Color color, Point startPoint, Point endPoint)
	{
		super(color, startPoint, endPoint);
	}

	@Override
	public void draw(GraphicsContext gc)
	{
		double startX, startY, endX, endY;
		
		if (this.myStartPoint.getX() < this.myEndPoint.getX())
		{
			startX = this.myStartPoint.getX();
			endX = this.myEndPoint.getX();
		}else
		{
			startX = this.myEndPoint.getX();
			endX = this.myStartPoint.getX();
		}
		
		if (this.myStartPoint.getY() < this.myEndPoint.getY())
		{
			startY = this.myStartPoint.getY();
			endY = this.myEndPoint.getY();
		}else
		{
			startY = this.myEndPoint.getY();
			endY = this.myStartPoint.getY();
		}
		
		gc.setFill(Awt2Fx(this.myColor));
		gc.fillRect(startX, startY, endX-startX, endY-startY);	
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
