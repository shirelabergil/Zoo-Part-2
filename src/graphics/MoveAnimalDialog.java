package graphics;

import mobility.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import animals.*;
import mobility.Mobile;
/***
 * This class defines the behavior of the button:"Move Animal"
 * @author Administrator
 * Shirel ghanah:206645103 
 * Noa Asulin:213250749
 * Ashdod Campus
 *
 */

public class MoveAnimalDialog extends JDialog implements ActionListener {

	private TabbedPane mainTB ;
	private int point_x;
	private int point_y;
	private String typeForConstructor;
	private  List<Animal> animalArr ;
	private int animalsCounter = 0;
	private ZooPanel pan;
	private ArrayList<JRadioButton> buttonArr;
	private JRadioButton tempButton;
	
	public int getAnimalCounter() { return this.animalsCounter; }
	public void setAnimalCounter(int newNum) { this.animalsCounter = newNum ;}
	
	
	public MoveAnimalDialog( List<Animal> arr , ZooPanel zp) {
		
		this.animalArr = arr;
		this.pan = zp;
		buttonArr = new ArrayList<JRadioButton>(10);
		
		mainTB = new TabbedPane();
	}
	
		public class TabbedPane implements ActionListener {  
		
			JFrame f;  
			JButton MoveAnimal = new JButton("Move Animal") ;
			JSpinner x ;
			JSpinner y ;
		
		
			public TabbedPane() {  
				f=new JFrame("Move Animal");  
		    
				JPanel animalType=new JPanel();  
				animalType.setLayout(new BoxLayout(animalType, BoxLayout.PAGE_AXIS));  
		    
				JPanel location_x=new JPanel();  
				JPanel location_y=new JPanel(); 
		    
		    
				JTabbedPane tp=new JTabbedPane();  
				tp.setBounds(50,50,200,200);  
				tp.add("Animal Type",animalType);  
				tp.add("location of x",location_x);  
				tp.add("location of y",location_y);  
		    	
				SpinnerModel value = new SpinnerNumberModel(150, 50,800, 1);   
				x = new JSpinner(value);  
			 
				location_x.add(x);
			 
				SpinnerModel val = new SpinnerNumberModel(150, 50,600, 1);   
				y = new JSpinner(val);  
			 
				location_y.add(y);
				
				x.addChangeListener(new SpinnerListener());
				y.addChangeListener(new SpinnerListener());
			 
				ButtonGroup chooseGroup=new ButtonGroup(); 
			 
				for(int i =0 ; i< animalArr.size() ; i++ ) {
					
					if(animalArr.get(i)!=null) 
					{
						tempButton=new JRadioButton(animalArr.get(i).toString(),false);
						buttonArr.add(tempButton);
						chooseGroup.add(tempButton);
						tempButton.addActionListener(this);
						animalType.add(tempButton);
					} 
					if(i ==0) {tempButton.setSelected(true);}
				 
				 //setVisible(true);
				}
			
				f.add(tp);  
				f.add(MoveAnimal);
				f.setSize(600,400);  
				f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS)); 
				MoveAnimal.addActionListener(this);
				f.setVisible(true);  
			
			
				
		}
		
		private class SpinnerListener implements  ChangeListener {
			
			@Override
			public void stateChanged(ChangeEvent e) {
			
				point_x = (int) x.getValue();
				point_y = (int) y.getValue();
			}	
		}

			@Override
			public void actionPerformed(ActionEvent ev) {
			
				point_x = (int) x.getValue();
				point_y = (int) y.getValue();
				
				if ( ev.getSource() == MoveAnimal ) 
				{
					f.dispose();
				
				
					for(int i =0 ; i< buttonArr.size() ; i++ ) 
					{
						 if(buttonArr.get(i).isSelected()) 
						 {
							 for(int j =0 ;j < animalArr.size() ; j++ ) 
							 {
							    if(buttonArr.get(i).getText().equals(animalArr.get(j).toString()))
							    {
							    
							    	animalArr.get(j).setLocation(new Point(point_x,point_y));
							    	animalArr.get(j).setChanges(true);
							    	pan.manageZoo();
							    	
								}
						     }	
							
						 }
					}	
				}
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
				
}