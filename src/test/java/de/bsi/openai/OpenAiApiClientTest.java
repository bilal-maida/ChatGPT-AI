package de.bsi.openai;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.bsi.openai.chatgpt.CompletionRequest;

@ActiveProfiles("test")
public class OpenAiApiClientTest {
	
	@InjectMocks
	OpenAiApiClient openAiAPIClient;
	
	@Value("${openai.api_url}")
	private URI openaiApiUrlTest;
	
	@Value("${openai.api_key}")
	private String openaiApiKeyTest;
	
	@Before
	public void setUp() throws Exception {
		openAiAPIClient = new OpenAiApiClient();
		System.out.println("******dddddddddddd**** :  "+openaiApiKeyTest);
		ReflectionTestUtils.setField(openAiAPIClient, "openaiApiUrl", openaiApiUrlTest);
		ReflectionTestUtils.setField(openAiAPIClient, "openaiApiKey", openaiApiKeyTest);

	}

	@After
	public void tearDown() throws Exception {
		openAiAPIClient = null;
	}
	
	@Test
	public void testPostToOpenAiApi() throws InterruptedException {
		HttpResponse<String> result = null;
		try {
			String message = "Hello?";
			var completion = CompletionRequest.defaultWith(message);
			System.out.println("*****************");
			System.out.println(completion);
			ObjectMapper mapper = new ObjectMapper();
			var postBodyJson = mapper.writeValueAsString(completion);
			//var postBodyJson = jsonMapper.writeValueAsString(completion);
			System.out.println("+++++++++++++++++");
			System.out.println(postBodyJson);
			result = openAiAPIClient.postToOpenAiApi(postBodyJson);
			System.out.println("-------------------");
			//System.out.println(result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(200, 200);
	}
	
}
