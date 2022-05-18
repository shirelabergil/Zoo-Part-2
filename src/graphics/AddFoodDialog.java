package graphics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plants.Cabbage;
import plants.Lettuce;
import plants.Meat;
import plants.Plant;

public class AddFoodDialog   {

	private JDialog dialog;
	private JButton lettuce=new JButton("Lettuce");
	private JButton cabbage=new JButton("Cabbage");
	private JButton meat=new JButton("Meat");
	private JPanel displayButtons=new JPanel();
	private JPanel displayLabel =new JPanel();
	private JLabel title=new JLabel("Please Select Food Type:");
	private ZooPanel zooPanel;
	private ZooFrame zooFrame;
	private BufferedImage img;
	private String getType=this.getClass().getSimpleName();


	private static List<Plant> food=new ArrayList<>();


	public AddFoodDialog(ZooFrame zf) {
		dialog=new JDialog();
		this.zooFrame=zf;
		this.zooPanel=new ZooPanel(zooFrame);

		displayLabel.add(title);
		displayButtons.add(cabbage);
		displayButtons.add(lettuce);
		displayButtons.add(meat);

		cabbage.addActionListener(new ActionListener() {

		@Override
			public void actionPerformed(ActionEvent e) {
				food.add(new Cabbage(zooPanel, zooFrame));
				disableButtons();
				dialog.dispose();
				

			}
		});
		
		lettuce.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				food.add(new Lettuce(zooPanel ,zooFrame));
				disableButtons();
				dialog.dispose();

			}
		});
		
		meat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				food.add(new Meat(zooPanel, zooFrame));
				disableButtons();
				dialog.dispose();
				
			}
		});
		

		dialog.pack();
		dialog.add(displayLabel,BorderLayout.NORTH);
		dialog.add(displayButtons,BorderLayout.SOUTH);
		dialog.setBounds(200, 200, 500, 300);
		dialog.setDefaultCloseOperation(dialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		
	}


	private void disableButtons()
	{
		meat.setEnabled(false);
		lettuce.setEnabled(false);
		cabbage.setEnabled(false);
		zooFrame.resize(new Dimension(2200,830));

	}
	public static List <Plant> getFood()
	{
		return food;
	}
	public String getFoodName()
	{
		return this.getType;
	}


}