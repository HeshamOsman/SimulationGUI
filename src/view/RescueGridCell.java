package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import simulation.Address;
import simulation.Simulatable;

public class RescueGridCell extends JButton {

	private List<Simulatable> cellOccupants = new ArrayList<>();
	private Simulatable currentSelectedOneFromOccupants;
	private Address address;
	public RescueGridCell() {
		
	}
	public RescueGridCell(int x,int y) {
		super();
		this.address = new Address(x, y);
	}
	
	public RescueGridCell(String label) {
		super(label);
	}
	
	public RescueGridCell(List<Simulatable> cellOccupants,int x,int y) {
		super();
		this.cellOccupants = cellOccupants;
		this.address = new Address(x, y);
	}
	public List<Simulatable> getCellOccupants() {
		return cellOccupants;
	}
	public void setCellOccupants(List<Simulatable> cellOccupants) {
		this.cellOccupants = cellOccupants;
	}
	public Simulatable getCurrentSelectedOneFromOccupants() {
		return currentSelectedOneFromOccupants;
	}
	public void setCurrentSelectedOneFromOccupants(Simulatable currentSelectedOneFromOccupants) {
		this.currentSelectedOneFromOccupants = currentSelectedOneFromOccupants;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
//	public void addToOccupants(Simulatable sim) {
//		
//	}
	
}
