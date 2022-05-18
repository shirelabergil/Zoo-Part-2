package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import animals.Animal;

import graphics.AddAnimalDialog;

public class Info extends ZooPanel{

	private JTable jt;
	private static List<Animal> animalArr ;
	DefaultTableModel tableModel=new DefaultTableModel();
	private int total=0;
	//private AddAnimalDialog addDialog;

	
	public Info(ZooFrame frame , List<Animal> arr ) 
	{
		super(frame);
		
		this.animalArr = arr;
		this.initTable();
	
		JPanel panel=new JPanel();
		panel.setSize(500,500);
	
		// jt.setFocusable(false);
	     
		panel.add(jt);
		
        jt.setRowSelectionAllowed(false);

		JDialog d=new JDialog();
		d.setSize(500,500);
		d.add(panel);
		d.setVisible(true);
		this.setSize(new Dimension(500,500));
		JScrollPane js=new JScrollPane(jt);
		d.add(js);
		this.setVisible(true);
		
	}
	public Info(String s)
	{
		super(null);
		this.initTable();
	}
	public  JTable getTable()
	{
		return jt;
	}
	public void initTable()
	{
		jt=new JTable(tableModel);
	
	     jt.setEnabled(false);
	 
	     jt.setRowHeight(50);
	    jt.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.DARK_GRAY));

		tableModel.addColumn("Animal");
		tableModel.addColumn("Color");
		tableModel.addColumn("Weight");
		tableModel.addColumn("Hor Speed");
		tableModel.addColumn("Ver SPeed");
		tableModel.addColumn("Eat Count");
		
		System.out.println(this.animalArr.size()+ "checking " );
		
		for(int i=0; i<this.animalArr.size(); ++i)
		{
			//System.out.println(animalarr.size());
			tableModel.addRow(new Object[] {this.animalArr.get(i).getClass().getSimpleName(),
					this.animalArr.get(i).getColor(),this.animalArr.get(i).getWeight(),this.animalArr.get(i).getHorSpeed(),this.animalArr.get(i).getVerSpeed(),this.animalArr.get(i).getEatCount()});
		
			total+=this.animalArr.get(i).getEatCount();
		}
		tableModel.addRow(new Object[] {"Total","","","","",total});

		jt.setFillsViewportHeight(true);
		jt.setPreferredScrollableViewportSize(new Dimension(450,63));

	}

}
