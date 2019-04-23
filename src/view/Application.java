package view;

import javax.swing.JFrame;

import controller.CommandCenter;

public class Application {

	public static void main(String[] args) throws Exception {
		CommandCenter commandCenter = new CommandCenter();
		
		MainWindow mainWindow = new MainWindow(commandCenter);
		mainWindow.setSize(1200, 600);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
	}
	
}
