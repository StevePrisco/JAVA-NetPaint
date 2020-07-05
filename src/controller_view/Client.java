package controller_view;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Line;
import model.Oval;
import model.PaintObject;
import model.Picture;
import model.Rectangle;

/**
 * A GUI for Netpaint that has all paint objects drawn upon a Canvas. This file
 * also represents the controller as it controls how paint objects are drawn and
 * sends new paint objects to the server. All Client objects also listen to the
 * server to read the Vector of PaintObjects and repaint every time any client
 * adds a new one.
 * 
 * @author Steven Prisco-Deglman
 * 
 */
public class Client extends Application {
	boolean drawing = false;
	boolean set = false;
	int anchorX;
	int anchorY;
	double oldX;
	double oldY;

	GraphicsContext gcTemp;

	Vector<PaintObject> allPaintObjects = new Vector<PaintObject>();
	RadioButton line = new RadioButton("Line");
	RadioButton rectangle = new RadioButton("Rectangle");
	RadioButton oval = new RadioButton("Oval");
	RadioButton picture = new RadioButton("Picture");
	ColorPicker colorPicker = new ColorPicker();
	ObjectInputStream input;
	Canvas canvas;
	Image myImage = new Image("file:NetPaintFX/images/doge.jpeg");
	
	Point startPoint;
	Point endPoint;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Socket server = new Socket("localhost", 4000);
		
		ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
		input = new ObjectInputStream(server.getInputStream());
		
		makeConnection();
		
		BorderPane all = new BorderPane();
		canvas = new Canvas(800, 650);
		gcTemp = canvas.getGraphicsContext2D();

		FlowPane flow = new FlowPane(15, 5);
		flow.setPadding(new Insets(5, 5, 5, 115));
		
		ToggleGroup group = new ToggleGroup();
	    line.setToggleGroup(group);
	    	line.setFont(Font.font("Consolas", 18));
	    rectangle.setToggleGroup(group);
	    	rectangle.setFont(Font.font("Consolas", 18));
	    oval.setToggleGroup(group);
	    	oval.setFont(Font.font("Consolas", 18));
	    picture.setToggleGroup(group);
	    	picture.setFont(Font.font("Consolas", 18));
	    
	    flow.getChildren().add(line);
	    flow.getChildren().add(rectangle);
	    flow.getChildren().add(oval);
	    flow.getChildren().add(picture);
	    flow.getChildren().add(colorPicker);

		canvas.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (!drawing)
				{
					drawing = true;
					anchorX = (int) event.getX();
					anchorY = (int) event.getY();
				} else
				{
					
					startPoint = new Point(anchorX, anchorY);
					endPoint = new Point((int) event.getX(), (int) event.getY());
					
					drawing = false;
					gcTemp.clearRect(0, 0, 800, 650);
					if (group.getSelectedToggle() == line)
					{
						Line curr = new Line(Fx2Awt(colorPicker.getValue()), startPoint, endPoint);
						try {
							output.writeObject(curr);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						allPaintObjects.add(curr);
					}else if (group.getSelectedToggle() == rectangle)
					{
						Rectangle curr = new Rectangle(Fx2Awt(colorPicker.getValue()), startPoint, endPoint);
						try {
							output.writeObject(curr);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						allPaintObjects.add(curr);
					}else if (group.getSelectedToggle() == oval)
					{
						Oval curr = new Oval(Fx2Awt(colorPicker.getValue()), startPoint, endPoint);
						try {
							output.writeObject(curr);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						allPaintObjects.add(curr);
					}else
					{
						Picture curr = new Picture(startPoint, endPoint, "doge.jpeg");
						try {
							output.writeObject(curr);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//					allPaintObjects.add(curr);
					}
					
//					drawAllPaintObjects(allPaintObjects, canvas);
				}
			}
		});

		canvas.setOnMouseMoved(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (drawing)
				{
					gcTemp.clearRect(0, 0, 800, 650);
					drawAllPaintObjects(allPaintObjects, canvas);
					
					startPoint = new Point(anchorX, anchorY);
					endPoint = new Point((int) event.getX(), (int) event.getY());
					
					if (group.getSelectedToggle() == line)
					{
						Line curr = new Line(Fx2Awt(colorPicker.getValue()), startPoint, endPoint);
						curr.draw(gcTemp);
					}else if (group.getSelectedToggle() == rectangle)
					{
						Rectangle curr = new Rectangle(Fx2Awt(colorPicker.getValue()), startPoint, endPoint);
						curr.draw(gcTemp);
					}else if (group.getSelectedToggle() == oval)
					{
						Oval curr = new Oval(Fx2Awt(colorPicker.getValue()), startPoint, endPoint);
						curr.draw(gcTemp);
					}else
					{
						Picture curr = new Picture(startPoint, endPoint, "doge.jpeg");
						curr.draw(gcTemp);
						gcTemp.drawImage(myImage, curr.getStartX(), curr.getStartY(), (curr.getEndX()-curr.getStartX()), (curr.getEndY()-curr.getStartY()));
					}
				}
			}
		});

		all.setCenter(canvas);
		all.setBottom(flow);

		Scene scene = new Scene(all, 800, 700);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void drawAllPaintObjects(Vector<PaintObject> allPaintObjects, Canvas canvas)
	{
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for (PaintObject po : allPaintObjects)
		{
			if (!po.isImage())
				po.draw(gc);
			else
			{
				po.draw(gc);
				gc.drawImage(myImage, po.getStartX(), po.getStartY(), (po.getEndX()-po.getStartX()), (po.getEndY()-po.getStartY()));
			}
		}
	}
	
	public static java.awt.Color Fx2Awt(javafx.scene.paint.Color fxColor)
	{
	    int r = (int) (255 * fxColor.getRed());
	    int g = (int) (255 * fxColor.getGreen());
	    int b = (int) (255 * fxColor.getBlue());
	    java.awt.Color awtColor = new java.awt.Color(r, g, b);
	    return awtColor;
	} 
	
	private void makeConnection()
	{
	    ListenForServerUpdates listener = new ListenForServerUpdates();
	    Thread thread = new Thread(listener);
	    thread.setDaemon(true);
	    thread.start();
	} 

	  // Listen for the Server to read a modified Vector<String>   
	  // Because JavaFX is not Thread-safe. This must be started in a new Thread,
	  // or better now as a Task or Service from the javafx.concurrent Package.
	private class ListenForServerUpdates extends Task<Object> implements Runnable
	{ 
		@Override
	    public void run()
	    {
	      // TODO 7: Wait for writes from the server with readObject.
			while (true)
			{
				try {
					allPaintObjects = (Vector<PaintObject>) input.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				drawAllPaintObjects(allPaintObjects, canvas);
			}
	    }

	    @Override
	    protected Object call() throws Exception
	    {
	      // Not using this call, but we need to override it to compile
	      return null;
	    }
	}
}