package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import exceptions.UnitException;
import model.people.Citizen;
import model.units.Evacuator;
import model.units.Unit;
import simulation.Rescuable;

public class EastPanel extends JPanel{

	private NorthPanel np;
	public EastPanel(NorthPanel np) {
		
		this.np = np;
//		
		JLabel selectedUnitNameLabel = new JLabel("Selected Unit Data");
//		JLabel currentUnitNameLabel = new JLabel("AMB");
		add(selectedUnitNameLabel);
	}
	
	public void update(Unit unit) {
		removeAll();
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(boxLayout);
		
		JLabel unitIDLabel = new JLabel("Unit ID");
		JLabel currentUnitIDLabel = new JLabel(unit.getUnitID());
		currentUnitIDLabel.setOpaque(true);
		currentUnitIDLabel.setBackground(Color.CYAN);
		add(unitIDLabel);
		add(currentUnitIDLabel);
		
		JLabel unitTypeLabel = new JLabel("Unit Type");
		JLabel currentUnitTypeLabel = new JLabel(unit.getClass().getSimpleName());
		currentUnitTypeLabel.setOpaque(true);
		currentUnitTypeLabel.setBackground(Color.CYAN);
		add(unitTypeLabel);
		add(currentUnitTypeLabel);
		
		JLabel unitStatusLabel = new JLabel("Status");
		JLabel currentUnitStatusLabel = new JLabel(""+unit.getState());
		currentUnitStatusLabel.setOpaque(true);
		currentUnitStatusLabel.setBackground(Color.CYAN);
		add(unitStatusLabel);
		add(currentUnitStatusLabel);
		
		
		JLabel unitTargetLabel = new JLabel("Target");
		JLabel currentUnitTargetLabel = new JLabel(unit.getTarget()==null?"No target":
			unit.getTarget().getClass().getSimpleName()+"("+unit.getTarget().getLocation()+")");
		currentUnitTargetLabel.setOpaque(true);
		currentUnitTargetLabel.setBackground(Color.CYAN);
		add(unitTargetLabel);
		add(currentUnitTargetLabel);
		
		
		JLabel unitStepsLabel = new JLabel("steps/cycle");
		JLabel currentUnitStepsLabel = new JLabel(""+unit.getStepsPerCycle());
		currentUnitStepsLabel.setOpaque(true);
		currentUnitStepsLabel.setBackground(Color.CYAN);
		add(unitStepsLabel);
		add(currentUnitStepsLabel);
		
		JLabel unitLocationLabel = new JLabel("Location");
		JLabel currentUnitLocationLabel = new JLabel(""+unit.getLocation());
		currentUnitLocationLabel.setOpaque(true);
		currentUnitLocationLabel.setBackground(Color.CYAN);
		add(unitLocationLabel);
		add(currentUnitLocationLabel);
		
		if(unit instanceof Evacuator) {
			Evacuator e = (Evacuator) unit;
			JLabel unitPassangersLabel = new JLabel("Passengers");
			add(unitPassangersLabel);
			JPanel passPanel = new JPanel();
			passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.Y_AXIS));
			for(Citizen c:e.getPassengers()) {
				RescueGridCell passButton = new RescueGridCell(c.getNationalID());
				passButton.getCellOccupants().add(c);
				passButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Citizen ci = (Citizen)passButton.getCellOccupants().get(0);
						JDialog dialog = new JDialog();
						dialog.setLayout( new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS) );
						dialog.add(new JLabel(c.getName()));
						dialog.add(new JLabel(""+c.getAge()));
						dialog.add(new JLabel(c.getNationalID()));
						dialog.add(new JLabel(""+c.getHp()));
						dialog.add(new JLabel(""+c.getBloodLoss()));
						dialog.add(new JLabel(""+c.getToxicity()));
						dialog.add(new JLabel(c.getLocation().toString()));
						dialog.add(new JLabel(c.getState().name()));
						dialog.add(new JLabel(c.getDisaster()==null||!c.getDisaster().isActive()?"No Current":c.getDisaster().getClass().getSimpleName()));
						dialog.setSize(400, 300);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true); 
					}
				});
				passPanel.add(passButton);
			}
			
			JScrollPane passScroll = new JScrollPane(passPanel);
			passScroll.setPreferredSize(new Dimension(100, 35));
			add(passScroll);
		}
		
		
		JButton respondButton = new JButton("Respond To");
		respondButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Rescuable r = np.getCurrentSelectedRescuable();
				if(r == null)
					np.updateBottomPanel("No target specified");
				else {
					try {
						unit.respond(r);
						currentUnitStatusLabel.setText(unit.getState().name());
						currentUnitTargetLabel.setText(unit.getTarget().getClass().getSimpleName()+"("+unit.getTarget().getLocation()+")");
					} catch (UnitException e2) {
						np.updateBottomPanel(e2.getMessage());
					}
				}
				
			}
		});
		add(respondButton);
		validate();
		repaint();
	}
	
	public void update() {
		removeAll();
		JLabel selectedUnitNameLabel = new JLabel("Selected Unit Data");
		add(selectedUnitNameLabel);
		validate();
		repaint();
	}
}
