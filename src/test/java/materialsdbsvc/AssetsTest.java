package materialsdbsvc;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.phystech.aosorio.controllers.AssetsController;
import co.phystech.aosorio.exceptions.AlreadyExistsException;
import co.phystech.aosorio.models.Assets;
import co.phystech.aosorio.models.AssetsStatus;
import co.phystech.aosorio.models.AssetsTypes;

public class AssetsTest {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(AssetsTest.class);
	
	private static final String itemcode = "1001";
	
	private static final AssetsTypes type = AssetsTypes.EQUIPMENT;
	private static final AssetsStatus status = AssetsStatus.ACTIVE;
	
	private static final String description = "LENOVO LAPTOP X131";
	private static final int quantity = 1;
	private static final String unit = "EA";
	private static final String partNumber = "X123456789";
	private static final String serialNumber = "A123456789";
	private static final double unitValue = 1000.0;
	private static final String entryDate = "01/01/2015";
	private static final String dischargeDate = "NA";
	private static final String location = "BOG";
	private static final String barcode = "1234567890";
	private static final String note = "Laptop for personal use";
	private static final String project = "PHY-0001-2018";
	private static final String valueCurrency = "USD";

	@BeforeClass
	public static void beforeClass() {

		Assets asset = new Assets();

		asset.setType(type);
		asset.setStatus(status);
		asset.setDescription(description);
		asset.setPartNumber(partNumber);
		asset.setSerialNumber(serialNumber);
		asset.setQuantity(quantity);
		asset.setUnit(unit);
		asset.setUnitValue(unitValue);
		asset.setValueCurrency(valueCurrency);
		asset.setEntryDate(entryDate);
		asset.setDischargeDate(dischargeDate);
		asset.setBarcode(barcode);
		asset.setLocation(location);
		asset.setNote(note);
		asset.setProject(project);

		slf4jLogger.info("Adding new provider");

		try {
		
			AssetsController.create(asset);
	
		} catch (AlreadyExistsException e) {
			
		}
		
	}

	@AfterClass
	public static void afterClass() {

		Assets asset = AssetsController.read(itemcode);

		AssetsController.delete(asset);

	}
	
	@Test
	public void test() {
		
		assertEquals(true,true);
		
	}

}
