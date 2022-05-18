package graphics;

import javax.swing.*;

public interface IAnimalBehavior {

	public String getAnimalName();
	public int getSize();
	public void eatInc();
	public int getEatCount();
	public boolean getChanges ();
	public void setChanges (boolean state);
}
