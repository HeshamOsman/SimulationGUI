package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.units.Unit;

public class WestPanel extends JPanel{
    private List<RescueGridCell> unitsButtons;
	public WestPanel(List<Unit> units,EastPanel ep) {
		GridLayout gridLayout = new GridLayout(units.size(),1);
		setLayout(gridLayout);
		unitsButtons = new ArrayList<>();
		for(Unit u:units) {
			RescueGridCell c = new RescueGridCell();
			c.setText(u.getUnitID()+" "+u.getClass().getSimpleName());
			c.getCellOccupants().add(u);
			c.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ep.update((Unit)((RescueGridCell)e.getSource()).getCellOccupants().get(0));
					
				}
			});
			add(c);
			unitsButtons.add(c);
		}
		
	}
	
	public void disableUnitButtons() {
		for(RescueGridCell c:unitsButtons) {
			c.setEnabled(false);
		}
	}
	
}
