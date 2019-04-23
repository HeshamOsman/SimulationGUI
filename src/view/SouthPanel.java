package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.disasters.Disaster;

public class SouthPanel extends JPanel{

	private JList<String> activeList;
	private JList<String> excutedList;
	private DefaultListModel<String> activeModel;
	private DefaultListModel<String> excutedModel;
	private List<Disaster> active;
	public SouthPanel(List<Disaster> excuted) {
		setLayout(new GridLayout(2,2));
		add(new JLabel("active"));
		add(new JLabel("excuatable"));
//		setSize(1200, 50);
		activeModel = new DefaultListModel<String>();
		excutedModel = new DefaultListModel<String>();
		
		active = new ArrayList<>();
		for (int i = 0; i < excuted.size(); i++) {
			excutedModel.addElement(excuted.get(i).getClass().getSimpleName());
			if(excuted.get(i).isActive())
				active.add(excuted.get(i));
		}
		
		for (int i = 0; i < active.size(); i++) {
			activeModel.addElement(active.get(i).getClass().getSimpleName());
		}

		
		
		activeList  = new JList<String>(activeModel);
//		activeList.setSize(600, 50);
		excutedList = new JList<String>(excutedModel);
//		excutedList.setSize(600, 50);
		JScrollPane activeScroll = new JScrollPane(activeList);
		activeScroll.setPreferredSize(new Dimension(600, 50));
		JScrollPane excutedScroll = new JScrollPane(excutedList);
		excutedScroll.setPreferredSize(new Dimension(600, 50));
		
		add(activeScroll);
		add(excutedScroll);
		
	}
	
	public void updateDisasters(List<Disaster> excuted) {
//		String[] activeValueList = new String[active.size()];
//		String[] excutedValueList = new String[excuted.size()];
		activeModel.clear();
		excutedModel.clear();
		
		active = new ArrayList<>();
		for (Disaster d:excuted) {
			excutedModel.addElement(d.getClass().getSimpleName()+". Cycle: "+d.getStartCycle()+
					" ("+
					d.getTarget().getClass().getSimpleName()+", "+d.getTarget().getLocation()+")");
			if(d.isActive())
				active.add(d);
		}
		
		for (Disaster d:active) {
			activeModel.addElement(d.getClass().getSimpleName()+" ("+
					d.getTarget().getClass().getSimpleName()+", "+d.getTarget().getLocation()+")");
		}

		
//		
//		
//		JPanel leftPanel = new JPanel();
//		System.out.println(); 
//		BoxLayout lBoxLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
//		leftPanel.setLayout(lBoxLayout);
//		JLabel activeLabel = new JLabel("active");
//		leftPanel.add(activeLabel);
//		JPanel rightPanel = new JPanel();
//		BoxLayout rBoxLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
//		rightPanel.setLayout(rBoxLayout);
//		JLabel excuatable = new JLabel("excuatable");
//		rightPanel.add(excuatable);
//		
//		add(leftPanel);
//		add(rightPanel);
	}
}
