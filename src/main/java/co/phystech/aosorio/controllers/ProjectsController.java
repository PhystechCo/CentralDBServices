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
import co.phystech.aosorio.models.BackendMessage;
import co.phystech.aosorio.models.Projects;
import co.phystech.aosorio.models.TimeTrackerRegistry;
import spark.Request;
import spark.Response;

/**
 * @author AOSORIO
 *
 */
public class ProjectsController {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(ProjectsController.class);

	private static Datastore datastore;

	public static Object create(Request pRequest, Response pResponse) {

		BackendMessage returnMessage = new BackendMessage();

		pResponse.type("application/json");

		try {

			slf4jLogger.info(pRequest.body());

			ObjectMapper mapper = new ObjectMapper();

			Projects newProject = mapper.readValue(pRequest.body(), Projects.class);

			JsonObject keys = create(newProject);
			pResponse.status(200);
			return returnMessage.getOkMessage(keys.toString());

		} catch (IOException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Problem adding Project");

		} catch (AlreadyExistsException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Project already exists");

		} catch (NoSuchElementException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Item in Project not in DB");

		}

	}

	public static JsonObject create(Projects project) throws AlreadyExistsException, NoSuchElementException {

		datastore = NoSqlController.getInstance().getDatabase();

		JsonObject item_result = new JsonObject();

		Projects result = findOneByTitle(project);

		if (result == null) {

			slf4jLogger.info("Project not found ");

			int number = Constants.PROJECTS_ID_OFFSET + new Integer((int) (count() + 1));

			project.setNumber(number);

			String projectId = project.getTarget();

			projectId = projectId.concat("-" + String.valueOf(number));
			projectId = projectId.concat("-" + String.valueOf(project.getYear()));

			project.setProjectId(projectId);

			datastore.save(project);

			item_result.addProperty("number", project.getNumber());
			item_result.addProperty("projectId", project.getProjectId());
			item_result.addProperty("status", "Saved");

		} else {

			item_result.addProperty("number", result.getNumber());
			item_result.addProperty("projectId", result.getProjectId());
			item_result.addProperty("status", "Already in DB");
		}

		return item_result;

	}


	public static Object read(Request pRequest, Response pResponse) {

		datastore = NoSqlController.getInstance().getDatabase();

		String id = pRequest.params("id");

		slf4jLogger.debug("Parameters: " + id);

		Query<Projects> query = datastore.createQuery(Projects.class);
		List<Projects> result = query.field("providerId").equal(id).asList();

		BackendMessage returnMessage = new BackendMessage();

		try {

			Projects project = result.iterator().next();
			String resultJson = new Gson().toJson(project);
			pResponse.status(200);
			pResponse.type("application/json");
			return returnMessage.getOkMessage(resultJson);

		} catch (NoSuchElementException ex) {

			slf4jLogger.debug("Projects not found");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Projects not founds");

		}

	}

	public static Object readAll(Request pRequest, Response pResponse) {

		datastore = NoSqlController.getInstance().getDatabase();

		Query<Projects> query = datastore.find(Projects.class);
		List<Projects> result = query.asList();

		BackendMessage returnMessage = new BackendMessage();

		try {
			String resultJson = new Gson().toJson(result);
			pResponse.status(200);
			pResponse.type("application/json");
			return returnMessage.getOkMessage(resultJson);

		} catch (NoSuchElementException ex) {

			slf4jLogger.debug("Project not found");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Project not found");

		}

	}

	public static Projects read(String id) {

		datastore = NoSqlController.getInstance().getDatabase();

		return datastore.get(Projects.class, id);

	}

	public static Object update(Request pRequest, Response pResponse) {

		String id = pRequest.params("id");

		BackendMessage returnMessage = new BackendMessage();

		try {

			ObjectMapper mapper = new ObjectMapper();

			ArrayList<Projects> newProviders = mapper.readValue(pRequest.body(),
					new TypeReference<ArrayList<Projects>>() {
					});

			Projects modified = newProviders.iterator().next();

			Key<Projects> keys = update(id, modified);

			JsonArray jArray = new JsonArray();
			JsonObject result = new JsonObject();
			result.addProperty("number", keys.getId().toString());
			result.addProperty("projectId", modified.getProjectId());
			result.addProperty("status", "Updated");

			jArray.add(result);

			pResponse.status(200);
			pResponse.type("application/json");
			return returnMessage.getOkMessage(jArray.toString());

		} catch (IOException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Problem updating Project");

		} catch (NoSuchElementException ex) {

			slf4jLogger.debug("Project not found");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Project not found");
		}

	}

	private static Key<Projects> update(String id, Projects modified) throws NoSuchElementException {

		datastore = NoSqlController.getInstance().getDatabase();

		Projects current = read(id);

		if (current == null)
			throw new NoSuchElementException();

		current.setTitle(modified.getTitle());
		current.setDescription(modified.getDescription());
		current.setStartDate(modified.getStartDate());
		current.setEndDate(modified.getEndDate());
		current.setDivision(modified.getDivision());
		current.setYear(modified.getYear());
		current.setTarget(modified.getTarget());
		current.setOwner(modified.getOwner());
		current.setTeam(modified.getTeam());
		current.setPresentationDate(modified.getPresentationDate());
		current.setApprovalDate(modified.getApprovalDate());
		current.setDocument(modified.getDocument());

		// ...If country changed the set the new country code

		return datastore.save(current);

	}

	public static WriteResult delete(Projects provider) {

		datastore = NoSqlController.getInstance().getDatabase();
		return datastore.delete(provider);
	}

	private static long count() {

		datastore = NoSqlController.getInstance().getDatabase();
		return datastore.find(Projects.class).count();

	}

	public static Projects findOneByTitle(Projects provider) {

		datastore = NoSqlController.getInstance().getDatabase();

		String title = provider.getTitle();

		Query<Projects> query = datastore.createQuery(Projects.class);
		List<Projects> result = query.field("title").containsIgnoreCase(title).asList();

		if (result.isEmpty()) {
			return null;
		} else {
			slf4jLogger.info("Found with title: " + title + " " + String.valueOf(result.size()));
			return result.get(0);
		}

	}

	public static Object addTimeRegistry(Request pRequest, Response pResponse) {

		String id = pRequest.params("id");

		BackendMessage returnMessage = new BackendMessage();

		try {

			ObjectMapper mapper = new ObjectMapper();

			TimeTrackerRegistry newTime = mapper.readValue(pRequest.body(), TimeTrackerRegistry.class);

			JsonArray result = addTimeRegistry(id, newTime);

			pResponse.status(200);
			pResponse.type("application/json");
			return returnMessage.getOkMessage(result.toString());

		} catch (IOException exception) {

			slf4jLogger.debug(exception.getLocalizedMessage());
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Problem adding comment to Project");

		} catch (NoSuchElementException ex) {

			slf4jLogger.debug("Project not found");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Project not founds");

		}

	}

	public static JsonArray addTimeRegistry(String id, TimeTrackerRegistry registry) throws NoSuchElementException {

		JsonArray jArray = new JsonArray();

		Projects project = read(id);

		if (project == null)
			throw new NoSuchElementException();

		List<TimeTrackerRegistry> times = project.getTimeTrackerRegistry();

		if (times == null)
			times = new ArrayList<TimeTrackerRegistry>();

		times.add(registry);

		project.setTimeTrackerRegistry(times);

		Key<Projects> key = datastore.save(project);;

		JsonObject result = new JsonObject();
		result.addProperty("number", key.getId().toString());
		result.addProperty("projectId", project.getProjectId());
		result.addProperty("status", "Time added");

		jArray.add(result);

		return jArray;

	}

}
