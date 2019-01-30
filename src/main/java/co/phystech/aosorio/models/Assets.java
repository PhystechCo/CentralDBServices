/**
 * 
 */
package co.phystech.aosorio.models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author AOSORIO
 *
 */

@Entity("assets")
public class Assets {
	
	@Id
	private String itemcode;
	
	private AssetsTypes type;
	private AssetsStatus status;
	
	private String description;
	private int quantity;
	private String unit;
	private String partNumber;
	private String serialNumber;
	private double unitValue;
	private String valueCurrency;
	private String entryDate;
	private String dischargeDate;
	private String location;
	private String barcode;
	private String note;
	private String project;
	
	/**
	 * @return the itemcode
	 */
	public String getItemcode() {
		return itemcode;
	}
	/**
	 * @param itemcode the itemcode to set
	 */
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	/**
	 * @return the type
	 */
	public AssetsTypes getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(AssetsTypes type) {
		this.type = type;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}
	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the unitValue
	 */
	public double getUnitValue() {
		return unitValue;
	}
	/**
	 * @param unitValue the unitValue to set
	 */
	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
	}
	/**
	 * @return the entryDate
	 */
	public String getEntryDate() {
		return entryDate;
	}
	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	/**
	 * @return the dischargeDate
	 */
	public String getDischargeDate() {
		return dischargeDate;
	}
	/**
	 * @param dischargeDate the dischargeDate to set
	 */
	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	/**
	 * @return the status
	 */
	public AssetsStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(AssetsStatus status) {
		this.status = status;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}
	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}
	/**
	 * @return the valueCurrency
	 */
	public String getValueCurrency() {
		return valueCurrency;
	}
	/**
	 * @param valueCurrency the valueCurrency to set
	 */
	public void setValueCurrency(String valueCurrency) {
		this.valueCurrency = valueCurrency;
	}
		
}
