package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import graphics.AddAnimalDialog;
import plants.Cabbage;
import plants.Lettuce;
import plants.Meat;
import plants.Plant;
import animals.Animal;

public class ZooPanel extends JPanel implements ActionListener {

	private JPanel btMenu = new JPanel();
	private AddAnimalDialog addanimal;
	
	protected static List<Animal> animalarr;
	
	private BufferedImage img = null;
	
	private JButton addAnimal = new JButton("Add Animal");
	private JButton moveAnimal = new JButton("Move Animal");
	private JButton clear = new JButton("Clear");
	private JButton food = new JButton("Food");
	private JButton info = new JButton("Info");
	private JButton exit = new JButton("Exit");
	
	private ZooFrame zooframe;
	private AddFoodDialog foodtype ;
	private Plant plant;
	
	JPanel Food = new JPanel();
	private JButton jb1 = new JButton(" lettuce ");
	private JButton jb2 = new JButton(" cabbage ");
	private JButton jb3 = new JButton(" meat ");
	


	
	public ZooPanel(ZooFrame z) {
		
		this.zooframe = z;
		
		animalarr = new ArrayList<>();

		
		this.setLayout(new BorderLayout());
		
		this.setPreferredSize(new Dimension(600, 800));
		
		
		btMenu.setLayout(new BoxLayout(btMenu, BoxLayout.LINE_AXIS));
		
		btMenu.add(addAnimal);
		btMenu.add(moveAnimal);
		btMenu.add(clear);
		btMenu.add(food);
		btMenu.add(info);
		btMenu.add(exit);

		
		
		addAnimal.addActionListener(this);
		moveAnimal.addActionListener(this);
		clear.addActionListener(this);
		food.addActionListener(this);
		info.addActionListener(this);
		exit.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		//this.setBackground(Color.black);
		//add(background);
		add(btMenu , BorderLayout.SOUTH);	
		this.setOpaque(true);
		this.setVisible(true);
		
		
	}
	
	public BufferedImage getImg() { return this.img ; };
	public void setImg(BufferedImage img) { this.img = img ;}
	
	public static List<Animal> getAnimalList() { return animalarr ;}
	public void setAnimalList( Animal animal) {animalarr.add(animal);}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g) ;
	
		 Graphics2D gr = ( Graphics2D ) g;
		 gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		 try
		 {	
			 if( img!=null ) {gr.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),this);}
			 
			 for( int i =0 ; i< addanimal.getAnimalArr().size() ; i++ ) {
				
				 System.out.println(addanimal.getAnimalArr().size() +"animal first size");
				 if(addanimal.getAnimalArr().get(i) != null/* && animalarr.get(i).getTerm()==true*/) { 
					// System.out.println("");
					 addanimal.getAnimalArr().get(i).loadImages(addanimal.getAnimalArr().get(i).getColor());
					 addanimal.getAnimalArr().get(i).drawObject(gr);
					 addanimal.getAnimalArr().get(i).setChanges(false);
					 
					 
				 }
			 }
			 
			 
			 if(foodtype!=null)
			 {
				System.out.println(addanimal.getAnimalArr().size() +"animalsize");
				 
				 for (int i=0;i<AddFoodDialog.getFood().size();++i)
				 {
					 AddFoodDialog.getFood().get(i).loadImages(AddFoodDialog.getFood().get(i).getClass().getSimpleName());
					 AddFoodDialog.getFood().get(i).drawObject(gr);

				 }
			 }
			 
		 }
		 
		catch (Exception e) {System.out.println("canot load image");
			return ;}
		 
		 }
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource() == addAnimal) {  addanimal = new AddAnimalDialog( animalarr , this, zooframe);  }
		
		if( e.getSource() == moveAnimal) { new MoveAnimalDialog(addanimal.getAnimalArr(),this); }
		
		if( e.getSource() == clear) {this.clearAll();}
		
		if( e.getSource() == food) {
			
		this.foodtype= new AddFoodDialog(zooframe);
		food.setEnabled(false);
		manageZoo();
		
		}
		
		if( e.getSource() == info) {new Info(zooframe , addanimal.getAnimalArr());}
		
		if( e.getSource() == exit) {System.exit(0);}
	}
	
	public boolean isChange() {
	
		 for(int i =0 ; i< animalarr.size() ; i++ ) {
			 
			 if(animalarr.get(i).getChanges() == true ) { return true;}
		 }
		 
		return false;
	}

	public void manageZoo() {
		
		   if(isChange()) { repaint(); }
		   
		   if(tryEatAnotherAnimal()) { repaint();}
		   
		   if(eatPlants()) { repaint(); }
		   
		

	}
	
	private void clearAll()
	{
		for(int i=0;i<getAnimalList().size();++i)
		{
			animalarr.removeAll(animalarr);
		}
		for(int i=0;i<AddFoodDialog.getFood().size();++i) {
			
			AddFoodDialog.getFood().removeAll(AddFoodDialog.getFood());
		}
		
		this.food.setEnabled(true);
		zooframe.resize(this.getWidth(), this.getHeight()+1);
		repaint();
	}
	
	private boolean tryEatAnotherAnimal()
	{
		Animal predator,prey;
		for(int i=0;i<getAnimalList().size();++i)
		{
			for(int j=0; j<getAnimalList().size();++j)
			{

					predator=getAnimalList().get(i);
					 prey=getAnimalList().get(j);
					 
				/*	 System.out.println("distance = " + predator.calcDistance(prey.getLocation())+"  prey size ="+ 
					prey.getSize()+ "eat term =  "+predator.getdiet().canEat(prey)+"predator weight =  "+predator.getWeight()+
					" prey weight =  "+prey.getWeight() );
					*/ 
					 
					 if(predator.getdiet().canEat(prey)&&predator.getWeight()>=2*prey.getWeight() &&
						predator.calcDistance(prey.getLocation()) < prey.getSize() ) 
					 {
						 predator.eat(predator, prey);
						 getAnimalList().remove(j);
						 predator.eatInc();
						 JOptionPane.showMessageDialog(this, predator.getAnimalName()+" Ate :"+prey.getAnimalName());
						 return true;
					 }
			}
		}
		return false;
	}
	
	/**
	 * checks eating conditions and returns true if they returns true.
	 * @return Boolean-true if condition is valid.
	 */
	public boolean eatPlants()
	{
		Plant plantFood;
		Animal animal;
		
		for(int i=0;i<AddFoodDialog.getFood().size();++i)
		{
			
			for(int j=0;j<addanimal.getAnimalArr().size();++j)
			{
				
				plantFood=AddFoodDialog.getFood().get(i);
				animal=addanimal.getAnimalArr().get(j);
				
				/* System.out.println("animal getdiet term = " +animal.getdiet().canEat(plantFood)+"  animal weight ="+ 
						 animal.getWeight()+ "plant weight =  "+plantFood.getWeight()+"animal distance from food  =  "+
						 animal.calcDistance(plantFood.getLocation())+ "animal location"+animal.getLocation()+"food location"+plantFood.getLocation());
				*/
				
				if(animal.getdiet().canEat(plantFood)&& animal.getWeight()>=2*plantFood.getWeight() &&
						animal.calcDistance(plantFood.getLocation())<10 && animal.calcDistance(plantFood.getLocation())>-10)
				{
						 animal.eat(animal, plantFood);
						 AddFoodDialog.getFood().remove(i);
						 JOptionPane.showMessageDialog(this, animal.getAnimalName()+" Ate :"+plantFood.getFoodtype());
						 animal.eatInc();
						 food.setEnabled(true);
						 return true;

				}
			}

		}
		return false;
	}

	
	
	
	
	
}