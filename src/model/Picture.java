package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.awt.Color;

public class Picture extends PaintObject
{
	private String myFilePath;
	
	public Picture(Point startPoint, Point endPoint, String picName)
	{
		super(Color.BLACK, startPoint, endPoint);
		myFilePath = "file:NetPaintFX/images/" + picName;
//		this.myImage = new Image(myFilePath, false);
	}

	@Override
	public void draw(GraphicsContext gc)
	{	
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
		
//		gc.drawImage(this.myFilePath, startX, startY, (endX-startX), (endY-startY));
	}

	@Override
	public boolean isImage()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public double getStartX() {
		// TODO Auto-generated method stub
		return this.startX;
	}

	@Override
	public double getStartY() {
		// TODO Auto-generated method stub
		return this.startY;
	}

	@Override
	public double getEndX() {
		// TODO Auto-generated method stub
		return this.endX;
	}

	@Override
	public double getEndY() {
		// TODO Auto-generated method stub
		return this.endY;
	}
}
