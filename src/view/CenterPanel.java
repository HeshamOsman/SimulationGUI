package view;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class CenterPanel extends JPanel implements MouseListener {

	private RescueGridCell[][] gridIcons;
	private NorthPanel northPanel;
	private EastPanel eastPanel;
	private ImageIcon roadIm;
	private ImageIcon citizenIm;
	private ImageIcon buildingIm;
	private ImageIcon unitIm;
	private ImageIcon multiIm;
	private ImageIcon deadIm;
	private ImageIcon collapsedBuildingIm;
	public CenterPanel(ArrayList<ResidentialBuilding> buildings, ArrayList<Citizen> citizens,
			ArrayList<Unit> emergencyUnits,NorthPanel north,EastPanel eastPanel) {
		northPanel = north;
		this.eastPanel = eastPanel;
		roadIm = changeImageSize(new ImageIcon("road.jpg").getImage());
		buildingIm = changeImageSize(new ImageIcon("building.png").getImage());
		multiIm = changeImageSize(new ImageIcon("multi.png").getImage());
		citizenIm = changeImageSize(new ImageIcon("citizen.png").getImage());
		unitIm = changeImageSize(new ImageIcon("unit.png").getImage());
		deadIm = changeImageSize(new ImageIcon("dead.png").getImage());
		collapsedBuildingIm = changeImageSize(new ImageIcon("collapsed.jpg").getImage());
		
		GridLayout grid = new GridLayout(10, 10);

		setLayout(grid);

		gridIcons = new RescueGridCell[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridIcons[i][j] = new RescueGridCell(i, j);
				gridIcons[i][j].addMouseListener(this);
				for (ResidentialBuilding rb : buildings) {
					if (rb.getLocation().equals(gridIcons[i][j].getAddress())) {
						gridIcons[i][j].getCellOccupants().add(rb);
						
					}
						
				}
				if(gridIcons[i][j].getCellOccupants().isEmpty()) {
				for (Citizen c : citizens) {
					if (c.getLocation().equals(gridIcons[i][j].getAddress())) {
						gridIcons[i][j].getCellOccupants().add(c);
					}
					
				}
				}
				for (Unit u : emergencyUnits) {
					if (u.getLocation().equals(gridIcons[i][j].getAddress())) {
						gridIcons[i][j].getCellOccupants().add(u);
					}
						
				}
				
				if(gridIcons[i][j].getCellOccupants().size()>1) {
					gridIcons[i][j].setIcon(multiIm);
				}else if(gridIcons[i][j].getCellOccupants().size()==1) {
					if(gridIcons[i][j].getCellOccupants().get(0) instanceof ResidentialBuilding) {
						ResidentialBuilding rb = (ResidentialBuilding) gridIcons[i][j].getCellOccupants().get(0);
						if(rb.getStructuralIntegrity()<=0) {
							gridIcons[i][j].setIcon(collapsedBuildingIm);
						}else {
							gridIcons[i][j].setIcon(buildingIm);
						}
						
					}else if(gridIcons[i][j].getCellOccupants().get(0) instanceof Citizen) {
						Citizen c =(Citizen)gridIcons[i][j].getCellOccupants().get(0);
						if(c.getState()==CitizenState.DECEASED) {
							gridIcons[i][j].setIcon(deadIm);
						}else {
							gridIcons[i][j].setIcon(citizenIm);
						}
						
					}else {
						gridIcons[i][j].setIcon(unitIm);
					}
				}else {
					gridIcons[i][j].setIcon(roadIm);
				}
				
				add(gridIcons[i][j]);
				
			}
		}



	}
	
	public void updateCells(ArrayList<ResidentialBuilding> buildings, ArrayList<Citizen> citizens,
			ArrayList<Unit> emergencyUnits) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridIcons[i][j].getCellOccupants().clear();
				
				for (ResidentialBuilding rb : buildings) {
					if (rb.getLocation().equals(gridIcons[i][j].getAddress())) {
						gridIcons[i][j].getCellOccupants().add(rb);
					}
						
				}
				if(gridIcons[i][j].getCellOccupants().isEmpty()) {
					for (Citizen c : citizens) {
						if (c.getLocation().equals(gridIcons[i][j].getAddress())) {
							gridIcons[i][j].getCellOccupants().add(c);
						}
						
					}
				}
				
				for (Unit u : emergencyUnits) {
					if (u.getLocation().equals(gridIcons[i][j].getAddress())) {
						gridIcons[i][j].getCellOccupants().add(u);
					}
						
				}
				
				if(gridIcons[i][j].getCellOccupants().size()>1) {
					gridIcons[i][j].setIcon(multiIm);
				}else if(gridIcons[i][j].getCellOccupants().size()==1) {
					if(gridIcons[i][j].getCellOccupants().get(0) instanceof ResidentialBuilding) {
						ResidentialBuilding rb = (ResidentialBuilding) gridIcons[i][j].getCellOccupants().get(0);
						if(rb.getStructuralIntegrity()<=0) {
							gridIcons[i][j].setIcon(collapsedBuildingIm);
						}else {
							gridIcons[i][j].setIcon(buildingIm);
						}
					}else if(gridIcons[i][j].getCellOccupants().get(0) instanceof Citizen) {
						Citizen c =(Citizen)gridIcons[i][j].getCellOccupants().get(0);
						if(c.getState()==CitizenState.DECEASED) {
							gridIcons[i][j].setIcon(deadIm);
						}else {
							gridIcons[i][j].setIcon(citizenIm);
						}
					}else {
						gridIcons[i][j].setIcon(unitIm);
					}
				}else {
					gridIcons[i][j].setIcon(roadIm);
				}
			}
		}
	}
	
	public void diableGridCells() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridIcons[i][j].setEnabled(false);
				
			
			}
		}
	}
	
	private ImageIcon changeImageSize(Image image) {
		Image newimg = image.getScaledInstance( 87, 43,  java.awt.Image.SCALE_SMOOTH ) ;  
		return new ImageIcon(newimg);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		RescueGridCell cell = (RescueGridCell)e.getComponent();
		if(cell.getCellOccupants().size()==1&&cell.getCellOccupants().get(0)instanceof Unit){
			
		}else if(cell.getCellOccupants().size()==1&&cell.getCellOccupants().get(0)instanceof Rescuable) {
			northPanel.updateBottomPanel((Rescuable)cell.getCellOccupants().get(0));
		}else if(cell.getCellOccupants().size()>1) {

			JDialog dialog = new JDialog();
			dialog.setLayout( new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS) );
			for(Simulatable s:cell.getCellOccupants()) {
				JButton j;
				if(s instanceof Unit) {
					Unit u = (Unit) s;
					j = new JButton(u.getUnitID()+" "+s.getClass().getSimpleName());
					j.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							eastPanel.update(u);
							dialog.dispose();
							
						}
					});
				}else {
					Rescuable r = (Rescuable) s;
					String appendtext="";
					if(r instanceof ResidentialBuilding) {
						ResidentialBuilding rb = (ResidentialBuilding) r;
						if(rb.getStructuralIntegrity()<=0)
							appendtext = "(collapsed)";
					}else {
						Citizen c= (Citizen)r;
						if(c.getState()==CitizenState.DECEASED)
							appendtext = "(Deceased)";
					}
					
					j = new JButton(r.getClass().getSimpleName()+appendtext);
					j.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							northPanel.updateBottomPanel(r);
							dialog.dispose();
						}
					});
					
				}
				dialog.add(j);
			}
			dialog.setSize(400, 300);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);  
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
}
