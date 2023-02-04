package de.bsi.openai;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.bsi.openai.chatgpt.CompletionRequest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;


public class OpenAiApiClientTest {
	
	@Autowired
	OpenAiApiClient openAiAPIClient;
	
	@Value("${openai.api_url}")
	private URI openaiApiUrlTest = URI.create("https://api.openai.com/v1/completions");
	
	@Value("${openai.api_key}")
	private String openaiApiKeyTest = "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";
	 
	@Before
	public void setUp() throws Exception {
		openAiAPIClient = new OpenAiApiClient();
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
			ObjectMapper mapper = new ObjectMapper();
			var postBodyJson = mapper.writeValueAsString(completion);
			result = openAiAPIClient.postToOpenAiApi(postBodyJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(200, 200);
	}
	
}
