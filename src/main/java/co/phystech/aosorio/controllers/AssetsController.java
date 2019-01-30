/**
 * 
 */
package co.phystech.aosorio.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.WriteResult;

import co.phystech.aosorio.config.Constants;
import co.phystech.aosorio.exceptions.AlreadyExistsException;
import co.phystech.aosorio.models.Assets;
import co.phystech.aosorio.models.BackendMessage;
import spark.Request;
import spark.Response;

/**
 * @author AOSORIO
 *
 */
public class AssetsController {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(AssetsController.class);

	private static Datastore datastore;

	public static Object create(Request pRequest, Response pResponse) {

		BackendMessage returnMessage = new BackendMessage();

		pResponse.type("application/json");

		try {

			slf4jLogger.info(pRequest.body());

			ObjectMapper mapper = new ObjectMapper();

			Assets newAsset = mapper.readValue(pRequest.body(), Assets.class);

			JsonObject keys = create(newAsset);
			pResponse.status(200);
			return returnMessage.getOkMessage(keys.toString());

		} catch (IOException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Problem adding new Asset");

		} catch (AlreadyExistsException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Asset already exists");

		}

	}

	public static JsonObject create(Assets asset) throws AlreadyExistsException {

		datastore = NoSqlController.getInstance().getDatabase();

		JsonObject item_result = new JsonObject();

		Assets result = findOneBySerial(asset);

		if (result == null) {

			slf4jLogger.info("Asset not found ");

			int itemcode = Constants.ASSETS_ID_OFFSET + new Integer((int) (count() + 1));

			asset.setItemcode(String.valueOf(itemcode));

			datastore.save(asset);

			item_result.addProperty("itemcode", asset.getItemcode());
			item_result.addProperty("serial", asset.getSerialNumber());
			item_result.addProperty("status", "Saved");

		} else {

			item_result.addProperty("itemcode", result.getItemcode());
			item_result.addProperty("serial", result.getSerialNumber());
			item_result.addProperty("status", "Already in DB");
		}

		return item_result;

	}

	public static Object read(Request pRequest, Response pResponse) {

		datastore = NoSqlController.getInstance().getDatabase();

		String id = pRequest.params("id");

		slf4jLogger.debug("Parameters: " + id);

		Query<Assets> query = datastore.createQuery(Assets.class);
		List<Assets> result = query.field("providerId").equal(id).asList();

		BackendMessage returnMessage = new BackendMessage();

		try {

			Assets project = result.iterator().next();
			String resultJson = new Gson().toJson(project);
			pResponse.status(200);
			pResponse.type("application/json");
			return returnMessage.getOkMessage(resultJson);

		} catch (NoSuchElementException ex) {

			slf4jLogger.debug("Assets not found");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Assets not founds");

		}

	}

	public static Object readAll(Request pRequest, Response pResponse) {

		datastore = NoSqlController.getInstance().getDatabase();

		Query<Assets> query = datastore.find(Assets.class);
		List<Assets> result = query.asList();

		BackendMessage returnMessage = new BackendMessage();

		try {
			String resultJson = new Gson().toJson(result);
			pResponse.status(200);
			pResponse.type("application/json");
			return returnMessage.getOkMessage(resultJson);

		} catch (NoSuchElementException ex) {

			slf4jLogger.debug("Asset not found");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Asset not found");

		}

	}

	public static Assets read(String id) {

		datastore = NoSqlController.getInstance().getDatabase();

		return datastore.get(Assets.class, id);

	}

	public static Object update(Request pRequest, Response pResponse) {

		String id = pRequest.params("id");

		BackendMessage returnMessage = new BackendMessage();

		try {

			ObjectMapper mapper = new ObjectMapper();

			ArrayList<Assets> newProviders = mapper.readValue(pRequest.body(), new TypeReference<ArrayList<Assets>>() {
			});

			Assets modified = newProviders.iterator().next();

			Key<Assets> keys = update(id, modified);

			JsonArray jArray = new JsonArray();
			JsonObject result = new JsonObject();
			result.addProperty("itemcode", keys.getId().toString());
			result.addProperty("serial", modified.getSerialNumber());
			result.addProperty("status", "Updated");

			jArray.add(result);

			pResponse.status(200);
			pResponse.type("application/json");
			return returnMessage.getOkMessage(jArray.toString());

		} catch (IOException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Problem updating Asset");

		} catch (NoSuchElementException ex) {

			slf4jLogger.debug("Project not found");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Project not found");
		}

	}

	private static Key<Assets> update(String id, Assets modified) throws NoSuchElementException {

		datastore = NoSqlController.getInstance().getDatabase();

		Assets current = read(id);

		if (current == null)
			throw new NoSuchElementException();

		current.setType(modified.getType());
		current.setDescription(modified.getDescription());
		current.setQuantity(modified.getQuantity());
		current.setUnit(modified.getUnit());
		current.setPartNumber(modified.getPartNumber());
		current.setSerialNumber(modified.getSerialNumber());
		current.setUnitValue(modified.getUnitValue());
		current.setEntryDate(modified.getEntryDate());
		current.setDischargeDate(modified.getDischargeDate());
		current.setStatus(modified.getStatus());
		current.setLocation(modified.getLocation());
		current.setNote(modified.getNote());
		current.setProject(modified.getProject());

		// ...If country changed the set the new country code

		return datastore.save(current);

	}

	public static WriteResult delete(Assets asset) {

		datastore = NoSqlController.getInstance().getDatabase();
		return datastore.delete(asset);
	}

	private static long count() {

		datastore = NoSqlController.getInstance().getDatabase();
		return datastore.find(Assets.class).count();

	}

	public static Assets findOneBySerial(Assets asset) {

		datastore = NoSqlController.getInstance().getDatabase();

		String serial = asset.getSerialNumber();

		Query<Assets> query = datastore.createQuery(Assets.class);
		List<Assets> result = query.field("serialNumber").containsIgnoreCase(serial).asList();

		if (result.isEmpty()) {
			return null;
		} else {
			slf4jLogger.info("Found with title: " + serial + " " + String.valueOf(result.size()));
			return result.get(0);
		}

	}

}
