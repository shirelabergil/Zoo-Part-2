package animals;

import mobility.Mobile;
import mobility.Point;
import food.IEdible;
import diet.IDiet;
import food.EFoodType;
import utilities.MessageUtility;
import diet.Carnivore;
import diet.Herbivore;
import diet.Omnivore;
import graphics.IAnimalBehavior;
import graphics.IDrawable;
import graphics.ZooPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;



/**
 * This class defines the characteristics and behavior of an animal in general.
 * 
 * @author 
 * Shirel ghanah:206645103 
 * Noa Asulin:213250749
 * Ashdod Campus
 * 
 * @see Lion,Bear,Elephent,Giraffe,Turtle
 */
 public abstract class Animal extends Mobile implements IEdible , IDiet, IDrawable,
IAnimalBehavior   {
	
	
	/**
	 * The animal name
	 */
	private String name;
	
	/**
	 * The animal weight
	 */
	private double weight;
	
	/**
	 * An object's instance who can eat and can check what food type he can eat.
	 */
	private IDiet diet; 
	private final int EAT_DISTANCE = 5;
	private int size;
	private String col;
	private int horSpeed;
	private int verSpeed;
	private boolean coordChanged;
	private Thread thread;
	private  int x_dir = 1;
	private  int y_dir = 1;
	private int eatCount;
	private ZooPanel pan;
	private BufferedImage img1, img2;
	private Point location ;
	private boolean drawTerm;
	
	
	public Animal(String name, Point point , double weight , IDiet diet , 
			int size , int horSpeed , int verSpeed , String color, ZooPanel pan) {
		
		super(point);
		this.name = name;
		this.weight = weight;
		this.diet = diet;
		this.size = size;
		this.horSpeed = horSpeed;
		this.verSpeed = verSpeed;
		this.col = color;
		this.eatCount = 0;
		this.coordChanged = false;
		this.x_dir =1;
		this.y_dir = 1;
		this.pan = pan;
		this.drawTerm = true;
		pan.setEnabled(true);
	}
	
	public ZooPanel getPan() { return this.pan ;}
	
	public BufferedImage getImg1() {return this.img1;}
	
	public BufferedImage getImg2() {return this.img2 ;}
	
	public int getXdir() { return this.x_dir ;}
	
	public int getYdir() { return this.y_dir ;}
	
	public String getAnimalName() {return this.name;}
	
	public int getEatCount() { return this.eatCount ;}
	
	public boolean getChanges() {return this.coordChanged ;}
	
	public void setChanges (boolean state) { this.coordChanged = state; }
	
	public String getColor() { return this.col ; }
	public void setColor(String col) { this.col = col ;}
	
	public int getSize() { return this.size ; }
	public void setSize(int size ) { this.size = size ;}
	
	public int getHorSpeed() { return this.horSpeed ; }
	public void setHorSpeed(int speed ) { this.horSpeed = speed ;}
	
	public int getVerSpeed() { return this.verSpeed ; }
	public void setVerSpeed(int speed ) { this.verSpeed = speed ;}
	
	public void eatInc() { this.eatCount += 1 ;}
	
	public boolean getTerm() {return this.drawTerm ;}
	public void setTerm(boolean t) {this.drawTerm = t;}
	
	public IDiet getdiet() { return this.diet;}
	

	
	/**
	 * The current class name.
	 * This method check what is the name of the class.
	 * 
	 * @return the name of the class
	 */
	private String className = this.getClass().getSimpleName();
	
	/**
	 * This method checks what is the name of the current class
	 * 
	 * @return the name of the current class
	 */
	public String getClasssName() {
		
		
		MessageUtility.logGetter(this.name,"getClassName",this.className);
		return this.className;
	}
	
	/**
	 * 	This method check what is the animal name 
	 * 
	 * @return the animal name
	 */
	public String getName() {
		
		MessageUtility.logGetter(this.name,"getName",this.name);
		return this.name;
	}
	
	/**
	 *The method changes the name of the animal.
	 *
	 * @param name 
	 * 		  the new name of the animal
	 * 
	 * @return true or false( if the name were changed or not )
	 */
	public boolean setName(String name) {
	
		this.name = name;
		MessageUtility.logSetter(this.name,"setName",name,true);
		return true;
	}
	
	/**
	 *The method changes the weight of the animal.
	 *
	 * @param weight
	 * 		  the new weight of the animal
	 * 
	 * @return true or false(if the weight were changed or not  )
	 */
	public boolean setWeight(double weight) {
		
		if(weight <= 0) {
			
			MessageUtility.logSetter(this.name,"setWeight",weight,false);
			return false;
		}
		else {
			
			this.weight = weight;
			MessageUtility.logSetter(this.name,"setWeight",weight,true);
			return true;
			
		}
		
	}
	
	/**
	 * This function checks what is the weight of the animal
	 * 
	 * @return the weight of the animal
	 */
	public double getWeight() {
	
		MessageUtility.logGetter(this.name,"getWeight",this.weight);
		return this.weight;
		
	}

	 /**
    * the method receives an animal and food and it checks if the animal can eat the food.
    * 
      @param animal 
      		  The animal that eats
      		  
      @param food 
      		  The desired food for feeding
      		  
      @return The weight gained by the animal after feeding
    */
	public double eat(Animal animal, IEdible food) {
		
		
		if (animal.canEat(food)) {
			
			if (food.getFoodtype()== EFoodType.MEAT) {
				this.makeSound();
				this.weight *= 1.1;
				return weight*0.1;
			}
			
			if (food.getFoodtype()== EFoodType.VEGETABLE) {
				this.makeSound();
				this.weight *= 1.07;
				return weight*0.07;
			}
		}
		
		return 0;
	}
	
	/**
	 * The method accepts a food type, and checks if the food is edible for the animal.
	 * 
	  * @param food (MEAT,VETABLE OR NOTFOOD)
	  *        The desired food for feeding
	  * 	   
	  * @return true or false . whether the animal can eat the food received or not
	 */
	public boolean canEat(IEdible food) {
	
		
		//System.out.println("predator :  "+this.getClass().getSimpleName());
		//System.out.println("prey :  "+food.getFoodtype());
		
		if ( this instanceof Carnivore) {
			
			if ( food.getFoodtype() == EFoodType.MEAT ) {
				return true;
			}
		}
			
		if ( this instanceof Omnivore) {
			
			if ( food.getFoodtype() == EFoodType.MEAT ||  food.getFoodtype() == EFoodType.VEGETABLE) {
				return true;
			}
		}
		if ( this instanceof Herbivore) {
				
			if ( food.getFoodtype() == EFoodType.VEGETABLE ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The method receives a location and moves the animal to the location it received
	 * @param p
	 * 		  the new location of the animal
	 * 
	 * @return the distance the animal made from where it was to the new location.
	 */
	public double move(Point p) {
		
			double dis = calcDistance(p);
			this.setLocation(p);
			this.setWeight(this.getWeight()-this.getTotalDistance()*this.weight*0.00025);
			MessageUtility.logBooleanFunction(this.name,"move", p, Point.checkBoundaries(p) );
			return dis;
		}
	
	/**
	 * The method checks what kind of food the animal is
	 * 
	 * @return  The food kind (MEAT,VETABLE OR NOTFOOD) .
	 */
	public EFoodType getFoodtype() {
		
		if (this instanceof Lion ) {
			
			return EFoodType.NOTFOOD;
		}
		return EFoodType.MEAT;	
	}
	
	/**
	 * The constructor initializes the fields of the animal according
	    to the parameters entered.
	    
	 * @param name 
	 * 		  the name of the animal
	 * 
	 * @param location
	 * 		  the location of the animal
	 */
	public Animal(String name , Point location) {
		super(location);
		this.name = name;
	}
	
	/**
	 * The abstract method prints the sound that the animal makes
	 */
	public abstract void makeSound();
	
	/**
	 * The method checks if the animal was able to eat the food it was given
	 * 
	 * @param food 
	 * 		  The desired food for feeding
	 * 
	 * @return true or false. whether the animal was able to eat the food or not.
	 */
	public boolean eat(IEdible food) {
		
		MessageUtility.logBooleanFunction(this.name, "eat", food, this.canEat(food));
		return this.canEat(food);
	}
	
	/**
	 * Prints the description of the animal .
	 */
	public String toString() {
		
		return "["+this.className+"]" + "	[Color] :" + this.col + "	[HoeSpeed] :" 
		+ this.horSpeed + "	[VerSpeed] :" +this.verSpeed+ "	[Location] :"+ this.getLocation();
	}
	 
	
	public void loadImages(String nm) {
		
		final String [] names = { "_b_1.png" ,"_b_2.png" , "_n_1.png" , "_n_2.png" , "_r_1.png" , "_r_2.png" };
		if(Arrays.asList("Red", "Netural" , "Blue").contains(nm)) {
			
			if(this.className.equals("Lion")) {
				
				switch (nm)
				{
				case "Red" :
				{
					 try { 

							 this.img1 = ImageIO.read(new File(PICTURE_PATH + "lio" + names[4]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "lio" + names[5]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Blue" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "lio" + names[0]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "lio" + names[1]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Netural" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "lio" + names[2]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "lio" + names[3]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}	
			  }	
			}
			
			if(this.className.equals("Bear")) {
				
				switch (nm)
				{
				case "Red" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "bea" + names[4]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "bea" + names[5]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Blue" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "bea" + names[0]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "bea" + names[1]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Netural" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "bea" + names[2]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "bea" + names[3]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}	
			  }	
			}
			
			if(this.className.equals("Elephant")) {
				
				switch (nm)
				{
				case "Red" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "elf" + names[4]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "elf" + names[5]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Blue" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "elf" + names[0]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "elf" + names[1]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Netural" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "elf" + names[2]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "elf" + names[3]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}	
			  }	
			}
			
			if(this.className.equals("Giraffe")) {
				
				switch (nm)
				{
				case "Red" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "grf" + names[4]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "grf" + names[5]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Blue" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "grf" + names[0]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "grf" + names[1]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Netural" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "grf" + names[2]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "grf" + names[3]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}	
			  }	
			}
			
			if(this.className.equals("Turtle")) {
				
				switch (nm)
				{
				case "Red" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "trt" + names[4]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "trt" + names[5]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Blue" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "trt" + names[0]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "trt" + names[1]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}
				
				case "Netural" :
				{
					 try { 
						 
						 this.img1 = ImageIO.read(new File(PICTURE_PATH + "trt" + names[2]));
						 this.img2 = ImageIO.read(new File(PICTURE_PATH + "trt" + names[3]));
					 }
					 catch (IOException e) { System.out.println("Cannot load image"); } break ;	
				}	
			  }	
			}	
		  }
    	}
	
		public void drawObject (Graphics g)
		{
		
		 
		 if(x_dir==1) {
			 
			 
			 if (this.getClasssName().equals("Bear")) {
			
				 g.drawImage(this.img1, this.getLocation().getx(), this.getLocation().gety(), this.size, this.size/(3/2) , this.pan);
			 }
			 
			 if (this.getClasssName().equals("Elephant")) {
				 
				 g.drawImage(this.img1, this.getLocation().getx(), this.getLocation().gety(), this.size , this.size/(6/5), this.pan);
			 }

			 if (this.getClasssName().equals("Giraffe")) {
				 
				 g.drawImage(this.img1, this.getLocation().getx()-this.size/2, this.getLocation().gety()-this.size/10, this.size/2, this.size, this.pan);
			 }

			 if (this.getClasssName().equals("Lion")) {
	 
				 g.drawImage(this.img1, this.getLocation().getx()-this.size/2, this.getLocation().gety()-this.size/3, this.size, this.size/(3/2), this.pan);

			 }

			 if (this.getClasssName().equals("Turtle")) {
				 
				 g.drawImage(this.img1, this.getLocation().getx()-this.size/2, this.getLocation().gety()-this.size/4*3, this.size/3, this.size/3, this.pan);

			 }
		 }
		 
				 else 
				 {
					 
					 if (this.getClasssName().equals("Bear")) {
						 
						 g.drawImage(this.img2, this.getLocation().getx(), this.getLocation().gety(), this.size/2, this.size, this.pan);
					 }
					 
					 if (this.getClasssName().equals("Elephant")) {
						 
						 g.drawImage(this.img2, this.getLocation().getx(), this.getLocation().gety(), this.size/2, this.size, this.pan);
					 }

					 if (this.getClasssName().equals("Giraffe")) {
						 
						 g.drawImage(this.img2, this.getLocation().getx()-this.size/2, this.getLocation().gety()-this.size/10, this.size/2, this.size, this.pan);
					 }

					 if (this.getClasssName().equals("Lion")) {
			 
						 g.drawImage(this.img2, this.getLocation().getx()-this.size/2, this.getLocation().gety()-this.size/3, this.size/2, this.size, this.pan);

					 }

					 if (this.getClasssName().equals("Turtle")) {
						 
						 g.drawImage(this.img2, this.getLocation().getx()-this.size/2, this.getLocation().gety()-this.size/4*3, this.size/2, this.size, this.pan);

					 }
				 }
					 
		}
		
	
}

