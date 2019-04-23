package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Rescuable;
import simulation.Simulator;

public class NorthPanel extends JPanel{

//	private int cycleCount;
//	private int casualtiesCount;
	private JPanel bottomPanel;
	private SouthPanel sp;
	private CenterPanel centerPanel;
	private EastPanel eastPanel;
	private WestPanel wp;
	private Rescuable currentSelectedRescuable;
	public NorthPanel(Simulator simulator,SouthPanel s) {
		sp = s;
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		JLabel cycleCountLabel = new JLabel("Current Cycle:");
		JLabel cycleCountCounterLabel = new JLabel("0");
		topPanel.add(cycleCountLabel);
		topPanel.add(cycleCountCounterLabel);
		
		JLabel casualtiesLabel = new JLabel("Casualties:");
		JLabel casualtiesCounterLabel = new JLabel("0");
		topPanel.add(casualtiesLabel);
		topPanel.add(casualtiesCounterLabel);
		
		JButton nextCycle = new JButton("Next");
		nextCycle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateBottomPanel();
				eastPanel.update();
				simulator.nextCycle();
			    if(simulator.checkGameOver()) {
			    	updateBottomPanel("Game Ended , Casualties = "+simulator.calculateCasualties() );
			    	nextCycle.setEnabled(false);
			    	wp.disableUnitButtons();
			    	centerPanel.diableGridCells();
			    }
				cycleCountCounterLabel.setText(""+simulator.getCurrentCycle()); 
				casualtiesCounterLabel.setText(""+simulator.calculateCasualties());
				
				
				sp.updateDisasters(simulator.getExecutedDisasters());
				centerPanel.updateCells(simulator.getBuildings(), simulator.getCitizens(),
						simulator.getEmergencyUnits());
				
			}
		});
		
		topPanel.add(nextCycle);
		
		bottomPanel = new JPanel();
		bottomPanel.setSize(1200, 70);
	
		
		setLayout(new GridLayout(2, 1));
		add(topPanel);
		add(bottomPanel);
		
	}
	public Rescuable getCurrentSelectedRescuable() {
		return currentSelectedRescuable;
	}
	public void updateBottomPanel() {
		currentSelectedRescuable = null;
		bottomPanel.removeAll();
		bottomPanel.validate();
		bottomPanel.repaint();
		
	}
	public void updateBottomPanel(String message) {
		currentSelectedRescuable = null;
		bottomPanel.removeAll();
		bottomPanel.add(new JLabel(message));
		bottomPanel.validate();
		bottomPanel.repaint();
	}
	public void updateBottomPanel(Rescuable r) {
		bottomPanel.removeAll();
		currentSelectedRescuable = r;
		if(r instanceof Citizen) {
			bottomPanel.setLayout(new GridLayout(2, 9)) ;
			Citizen c=(Citizen)r;
			JLabel cNameLabel = new JLabel("Name");
			JLabel cAgeLabel = new JLabel("Age");
			JLabel cIDLabel = new JLabel("ID");
			JLabel cHPLabel = new JLabel("HP");
			JLabel cBloodLossLabel = new JLabel("Blood Loss");
			JLabel cToxicityLabel = new JLabel("Toxicity");
			JLabel cAddressLabel = new JLabel("Loction");
			JLabel cStateLabel = new JLabel("State");
			JLabel cDisasterLabel = new JLabel("Disaster");
			bottomPanel.add(cNameLabel);
			bottomPanel.add(cAgeLabel);
			bottomPanel.add(cIDLabel);
			bottomPanel.add(cHPLabel);
			bottomPanel.add(cBloodLossLabel);
			bottomPanel.add(cToxicityLabel);
			bottomPanel.add(cAddressLabel);
			bottomPanel.add(cStateLabel);
			bottomPanel.add(cDisasterLabel);
//			JLabel cNameLabel = new JLabel("Name");
//			JLabel cAgeLabel = new JLabel("Age");
//			JLabel cIDLabel = new JLabel("ID");
//			JLabel cHPLabel = new JLabel("HP");
//			JLabel cBloodLossLabel = new JLabel("Blood Loss");
//			JLabel cToxicityLabel = new JLabel("Toxicity");
			bottomPanel.add(new JLabel(c.getName()));
			bottomPanel.add(new JLabel(""+c.getAge()));
			bottomPanel.add(new JLabel(c.getNationalID()));
			bottomPanel.add(new JLabel(""+c.getHp()));
			bottomPanel.add(new JLabel(""+c.getBloodLoss()));
			bottomPanel.add(new JLabel(""+c.getToxicity()));
			bottomPanel.add(new JLabel(c.getLocation().toString()));
			bottomPanel.add(new JLabel(c.getState().name()));
			bottomPanel.add(new JLabel(c.getDisaster()==null||!c.getDisaster().isActive()?"No Current":c.getDisaster().getClass().getSimpleName()));
			
		}else {
			bottomPanel.setLayout(new GridLayout(2, 8)) ;
			ResidentialBuilding rb=(ResidentialBuilding)r;
			JLabel rbStructuralIntegrityLabel = new JLabel("structural integrity");
			JLabel rbFireDamageLabel = new JLabel("fire damage");
			JLabel rbGasLevelLabel = new JLabel("gas level");
			JLabel rbFoundationDamageLabel = new JLabel("foundation damage");
			JLabel rbNumOfOccupantsLabel = new JLabel("# occupants");
			JLabel rbLocationLabel = new JLabel("location");
			JLabel rbOccupantsLabel = new JLabel("occupants");
			JLabel rbDisasterLabel = new JLabel("Disaster");
			bottomPanel.add(rbStructuralIntegrityLabel);
			bottomPanel.add(rbFireDamageLabel);
			bottomPanel.add(rbGasLevelLabel);
			bottomPanel.add(rbFoundationDamageLabel);
			bottomPanel.add(rbNumOfOccupantsLabel);
			bottomPanel.add(rbLocationLabel);
			bottomPanel.add(rbOccupantsLabel);
			bottomPanel.add(rbDisasterLabel);
//			JLabel cNameLabel = new JLabel("Name");
//			JLabel cAgeLabel = new JLabel("Age");
//			JLabel cIDLabel = new JLabel("ID");
//			JLabel cHPLabel = new JLabel("HP");
//			JLabel cBloodLossLabel = new JLabel("Blood Loss");
//			JLabel cToxicityLabel = new JLabel("Toxicity");
			bottomPanel.add(new JLabel(""+rb.getStructuralIntegrity()));
			bottomPanel.add(new JLabel(""+rb.getFireDamage()));
			bottomPanel.add(new JLabel(""+rb.getGasLevel()));
			bottomPanel.add(new JLabel(""+rb.getFoundationDamage()));
			bottomPanel.add(new JLabel(""+rb.getOccupants().size()));
			bottomPanel.add(new JLabel(rb.getLocation().toString()));
			
			JPanel occPanel = new JPanel();
			occPanel.setLayout(new BoxLayout(occPanel, BoxLayout.Y_AXIS));
			for(Citizen c:rb.getOccupants()) {
				JButton j = new JButton(c.getNationalID());
				j.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						updateBottomPanel(c);
						
					}
				});
				
				occPanel.add(j);
			}
			JScrollPane occupantsScroll = new JScrollPane(occPanel);
			occupantsScroll.setPreferredSize(new Dimension(100, 35));
			bottomPanel.add(occupantsScroll);
			bottomPanel.add(new JLabel(rb.getDisaster()==null||!rb.getDisaster().isActive()?"No Current":rb.getDisaster().getClass().getSimpleName()));
		}
		
		bottomPanel.validate();
		bottomPanel.repaint();
	}
	public void setCenterPanel(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
	}
	
	public void setEastPanel(EastPanel eastPanel) {
		this.eastPanel = eastPanel;
	}
	public void setWp(WestPanel wp) {
		this.wp = wp;
	}
	
	
}
