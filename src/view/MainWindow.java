package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import controller.CommandCenter;

public class MainWindow extends JFrame {

	public MainWindow(CommandCenter commandCenter) {
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		SouthPanel sp = new SouthPanel(commandCenter.getEngine().getExecutedDisasters());
		
		
		
		NorthPanel np = new NorthPanel(commandCenter.getEngine(),sp);
		contentPane.add(np, BorderLayout.NORTH);
		
		EastPanel ep = new EastPanel(np);
		contentPane.add(ep, BorderLayout.EAST);
		np.setEastPanel(ep);
		
		WestPanel wp = new WestPanel(commandCenter.getEmergencyUnits(),ep);
		
		contentPane.add(wp, BorderLayout.WEST);
		np.setWp(wp);
		
		
		CenterPanel c =new CenterPanel(commandCenter.getEngine().getBuildings(),
				commandCenter.getEngine().getCitizens(),commandCenter.getEngine().getEmergencyUnits(),np,ep);
		
		contentPane.add(c, BorderLayout.CENTER);
		np.setCenterPanel(c);
		contentPane.add(sp, BorderLayout.SOUTH);
		
		

		
	}
	
}
