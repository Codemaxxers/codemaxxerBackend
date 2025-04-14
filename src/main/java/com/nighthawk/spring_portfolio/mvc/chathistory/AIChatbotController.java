package com.nighthawk.spring_portfolio.mvc.chathistory;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.springframework.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.RestController;
import com.nighthawk.spring_portfolio.mvc.person.PersonApiController;
import com.nighthawk.spring_portfolio.mvc.person.Person;

// AI Chat Bot Controller based on Chat GPT 3.5 API
@RestController
@RequestMapping("/aichatbot")
public class AIChatbotController {
	@Autowired
	ChatJpaRepository chatJpaRepository;

	@Autowired
	PersonApiController personApiController;
	
	static Dotenv dotenv = Dotenv.load();

	// create chat GPT assistant id
	private static String assistantId = "asst_" + dotenv.get("ai_asst_id");

	// create chat GTP thread id
	private static String threadId  =  "thread_" + dotenv.get("ai_thread_id");

	// basic hello greeting
	@GetMapping("")
	public String greeting() {
		return "Hello From Chatbot AI.";
	}

	// chat request mapping  
	@GetMapping("/chat")
	//@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> chat(@RequestParam String message,@RequestParam Long personid) {
		try {
			// user sends a message that is sent to chat gpt and a response is returned
			String response = getResponseFromAI(message);
			System.out.println("Chat: " + message);
			System.out.println("Response: " + response);
			
			Chat chat = new Chat(message, response, new Date(System.currentTimeMillis()), personid);
			Chat chatUpdated = chatJpaRepository.save(chat);
			System.out.println("Chat saved in db: " + chatUpdated.getId());
			return new ResponseEntity<Chat>(chatUpdated, HttpStatus.OK);
			//return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Long getPersonId() {
		//ResponseEntity<Person> personData = personApiController.getAuthenticatedPersonData();
		//System.out.println("Logged In Person: " + personData.getBody().getId());
		//return personData.getBody().getId();
		return 1l;
	}

	@DeleteMapping("/chat/history/clear")
	//@PreAuthorize("isAuthenticated()")

	public String clearChatHistory(@RequestParam Long personid) {

		List<Chat> 	chats = chatJpaRepository.deleteByPersonId(personid);
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
       
		for (Chat c : chats) {
			System.out.println("Chat ID: " + c.getId());
			 list.add(c.toJSON());
		}
		
		obj.put("chats", list);
		return obj.toJSONString();
	}

	@DeleteMapping("/chat/history/delete/{id}")
	//@PreAuthorize("isAuthenticated()")
	public List<Chat> deleteChat(@PathVariable Long id, @RequestParam Long personid) {
		chatJpaRepository.deleteById(id);
		return getAllChatsForUser(personid);
	}
	
	@GetMapping("/chat/history")
	//@PreAuthorize("isAuthenticated()")
	public List<Chat> getAllChatsForUser(@RequestParam Long personid) {
		
		List<Chat> 	chats = chatJpaRepository.findByPersonId(personid);
		return chats;
	}
	
	@GetMapping("/chat/history/all")
	// get all chats and return as a two dimensional string array
	public String[][] getAllChats() {
		// get all chats
		List<Chat> 	chats = chatJpaRepository.findAll();
		// initialize the two dimensional array
		// array size is same as number of chats in the list above
		// the other dimension of the two dimensional array is fixed at 4 to hold:
		// person id; chat message; chat response; and time stamp
		String[][] allChats = new String[chats.size()][4];
		
		// initialize the counter
		int counter = 0;
		
		// iterate over the list of chats
		for (Chat c : chats) {
			// retrieve values
			long personId = c.getPersonId();
			String chatMsg = c.getChatMessage();
			String response = c.getChatReponse();
			Date timeStamp = c.getTimestamp();
			// set values in the two dimensional array
			// counter is incremented at the end
			allChats[counter][0] = String.valueOf(personId);
			allChats[counter][1] = chatMsg;
			allChats[counter][2] = response;
			allChats[counter][3] = timeStamp.toString();
			
			// increment counter
			counter++;
		}
		
		// return the chats for all users
		return allChats;
	}

	// Update Methods
	// @PutMapping("/chat/history/update")
	// public String updateChatHistory(){
	// 	List<Chat> 	chats = chatJpaRepository.findByPersonId(1l);


	// }

	// @PatchMapping("/chat/history/patch")
	// public String patchChatHistory(){

	// }
	/**
	 * Chat GPT API requires creating a message, then calling the run. The status
	 * check must come "complete" Then the response can be read from the messages
	 * and returned to the user
	 */
	public String getResponseFromAI(String userQuery) throws Exception {
		System.out.println("Assistant Id: " + assistantId);
		System.out.println("Thread Id: " + threadId);

		// Create the message. Use the user's query
		String createMessageUrl = "https://api.openai.com/v1/threads/" + threadId + "/messages";
		Header contentType = new BasicHeader("Content-Type", "application/json");
		Header auth = new BasicHeader("Authorization", "Bearer sk-proj-" + dotenv.get("ai_key"));
		Header org = new BasicHeader("OpenAI-Organization", "org-sv0fuwJ8PSa0kMI5psf5d0Q8");
		Header openAiBeta = new BasicHeader("OpenAI-Beta", "assistants=v1");

		String bodyStr = "{\"role\": \"user\",\"content\": \"" + userQuery + "\"}";

		JSONObject message = sendHttpPost(createMessageUrl, bodyStr, contentType, auth, openAiBeta, org);
		String messageId = (String) message.get("id");
		System.out.println("Message ID:" + messageId);
		
		// Call the RUN api
		String runThreadUrl = "https://api.openai.com/v1/threads/" + threadId + "/runs";
		String tBodyStr = "{\"assistant_id\": \"" + assistantId
				+ "\",\"instructions\": \"Please address the user as Shivansh. The user has a premium account.\"}";

		JSONObject runObj = sendHttpPost(runThreadUrl, tBodyStr, contentType, auth, openAiBeta);
		String runId = (String) runObj.get("id");

		// check status
		String statusCheckUrl = "https://api.openai.com/v1/threads/" + threadId + "/runs/" + runId;
		JSONObject sObj = sendHttpGet(statusCheckUrl, contentType, auth, openAiBeta, org);

		String status = (String) sObj.get("status");
		int retry = 0;

		while (!status.equals("completed")) {
			// wait max 10 seconds for completion
			if (++retry >= 10) {
				break;
			}

			// sleep a second
			Thread.sleep(1000);
			sObj = sendHttpGet(statusCheckUrl, contentType, auth, openAiBeta);
			status = (String) sObj.get("status");
		}

		// get response
		// TODO error handling
		String getResponseUrl = "https://api.openai.com/v1/threads/" + threadId + "/messages";

		JSONObject rObj = sendHttpGet(getResponseUrl, contentType, auth, openAiBeta, org);

		System.out.println("JSON Response: \n" + rObj.toJSONString() + "\n\n");
		// the response will match the first id
		String firstId = (String)rObj.get("first_id");
		// get data array from json
		JSONArray dataArray = (JSONArray)rObj.get("data");

		// to create the response string
		StringBuilder chatReponse = new StringBuilder();
		
	    for (int i = 0; i < dataArray.size(); i++) {
	    	JSONObject anObj = (JSONObject)dataArray.get(i);
	    	
	    	// the role must be assistant to hold the value and id must match firstId
	    	if (anObj.get("role").equals("assistant") && anObj.get("id").equals(firstId)) {
	    		JSONArray contentArray = (JSONArray)anObj.get("content");
	    		
	    		for (int j = 0; j < contentArray.size(); j++) {
	    			JSONObject contentObj = (JSONObject)contentArray.get(j);
	    			JSONObject textObj = (JSONObject)contentObj.get("text");
	    		
	    			// this contains the chat gpt's response
	    			chatReponse.append((String)textObj.get("value"));
	    			break;
	    		}
	    	}
	    }

	    return chatReponse.toString();
	}

	// send http post and return JSON response
	public static JSONObject sendHttpPost(String url, String body, Header... headers) throws Exception {
		JSONObject json = null;

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(body));
			httpPost.setHeaders(headers);
			json = httpClient.execute(httpPost, new JSONResponseHandler());
		}

		return json;
	}

	// send http get and return JSON response
	public static JSONObject sendHttpGet(String url, Header... headers) throws Exception {
		JSONObject json = null;

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeaders(headers);
			json = httpClient.execute(httpGet, new JSONResponseHandler());
		}

		return json;
	}

	// main method to testing
	public static void main(String[] args) throws Exception {
		String aiKey = System.getenv("AI_KEY");
        System.out.println("AI key: " + aiKey);
		AIChatbotController ai = new AIChatbotController();
		String response = ai.getResponseFromAI("Hi");
		System.out.println(response);
	}
	
}

class JSONResponseHandler implements HttpClientResponseHandler<JSONObject> {

	@Override
	public JSONObject handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
		// Get the status of the response
		int status = response.getCode();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			} else {
				JSONParser parser = new JSONParser();
				try {
					return (JSONObject) parser.parse(EntityUtils.toString(entity));
				} catch (ParseException | org.json.simple.parser.ParseException | IOException e) {
					e.printStackTrace();
					return null;
				}
			}

		} else {
			return null;
		}
	}
}

