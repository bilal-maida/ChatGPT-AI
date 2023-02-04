package de.bsi.openai.chatgpt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.bsi.openai.FormInputDTO;
import de.bsi.openai.OpenAiApiClient;

import com.opencsv.CSVWriter;


@Controller
public class ChatGptController {
	
	private static final String MAIN_PAGE = "index";
	
	@Autowired private ObjectMapper jsonMapper;
	@Autowired private OpenAiApiClient client;
	
	
	private String chatWithGpt3(String message) throws Exception {
		var completion = CompletionRequest.defaultWith(message);
		var postBodyJson = jsonMapper.writeValueAsString(completion);
		var responseBody = client.postToOpenAiApi(postBodyJson).body();
		var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
		String answer = completionResponse.firstAnswer().orElseThrow();
		this.storageData(message, answer);
		return answer;
	}
	
	private void storageData(String question, String answer) {
		try {
		    File dataFile = new File("src/main/resources/data.csv");
		    var dataFilePath= dataFile.getPath();
		    
			CSVWriter writer = new CSVWriter(new FileWriter(dataFilePath, true));
			
		    //Create record
			String data = question+";"+answer.replaceAll("\\r|\\n", "");
		    String[] record = new String[]{data};

		    //Write the record to file
		    writer.writeNext(record, false);

		    //close the writer
		    writer.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping(path = "/")
	public String index() {
		return MAIN_PAGE;
	}
	
	@PostMapping(path = "/")
	public String chat(Model model, @ModelAttribute FormInputDTO dto) {
		try {
			model.addAttribute("request", dto.prompt());
			model.addAttribute("response", chatWithGpt3(dto.prompt()));
		} catch (Exception e) {
			model.addAttribute("response", "Error in communication with OpenAI ChatGPT API.");
		}
		return MAIN_PAGE;
	}
	
}
