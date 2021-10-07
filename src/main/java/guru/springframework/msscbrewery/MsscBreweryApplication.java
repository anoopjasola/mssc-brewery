package guru.springframework.msscbrewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsscBreweryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsscBreweryApplication.class, args);
	}

}


package com.hzn.api.di.subscribers.accounts.web.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.hzn.api.common.exception.ApiException;
import com.hzn.api.common.service.externalidentifier.ExternalIdentifierService;
import com.hzn.api.common.service.repository.ApiMongoTemplate;
import com.hzn.api.di.members.coverages.domain.Coverage;
import com.hzn.api.di.members.coverages.domain.Internal;
import com.hzn.api.di.members.coverages.domain.Premium;
import com.hzn.api.di.members.coverages.domain.Subgroup;
import com.hzn.api.di.members.coverages.service.restclient.CoverageRestClient;
/*import com.hzn.api.di.members.coverages.model.Coverage;
import com.hzn.api.di.members.coverages.model.Internal;
import com.hzn.api.di.members.coverages.model.Premium;
import com.hzn.api.di.members.coverages.model.Subgroup;
import com.hzn.api.di.members.coverages.service.CoverageService;*/
import com.hzn.api.di.subscribers.accounts.config.AccountsAppConfig;
import com.hzn.api.di.subscribers.accounts.domain.AccountsCache;
//import com.hzn.api.di.subscribers.accounts.model.AccountsCache;
import com.hzn.api.di.subscribers.accounts.repository.AccountsRepository;
import com.hzn.api.di.subscribers.accounts.service.AccountsServiceImpl;
import com.lh.digital.integration.customheaderpropagation.CustomHeaderParameterProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountsAppConfig.class)
@WebAppConfiguration
public class AccountsRestControllerTest {

	@InjectMocks
	private AccountsRestController accountsRestController;

	@InjectMocks
	private AccountsServiceImpl accountsService;

	@InjectMocks
	private AccountsRepository accountsRepository;

	@Mock
	private CoverageRestClient coverageService;

	@Mock
	private CustomHeaderParameterProvider customHeaderParameterProvider;

	@Mock
	private ExternalIdentifierService externalIdentifierService;

	@Mock
	private ApiMongoTemplate apiMongoTemplate;

	private static Logger logger;
	private HttpHeaders httpHeaders;
	private final String GATEWAY_URL = "https://dev.api.horizonblue.com/v1";
	private MockMvc mockMvc;
	private String ACCOUNTS_URL = "/v1/subscribers/12345678/accounts";

	@BeforeClass
	public static void beforeClass() {
		logger = LoggerFactory.getLogger(AccountsRestControllerTest.class);
	}

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(accountsRestController, "accountsService", accountsService);
		ReflectionTestUtils.setField(accountsService, "accountsRepository", accountsRepository);
		logger.info("Flow setup");
		mockMvc = standaloneSetup(this.accountsRestController).build();
	}

	/*
	 * Successful for only memberId required queryparam, code - 200
	 */
	@Test
	public void successWithMemberId() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.remove("X-HZN-RequestingMemberId");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(getCoverage());
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenReturn(getAccountsCacheList());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.headers(httpHeaders)).andExpect(status().isOk());
	}

	/*
	 * Passing all required and optional field to cover all validation code code:200
	 * */
	/*@Test
	public void successWithAllQueryParams() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12345678");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(getCoverage());
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenReturn(getAccountsCacheList());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.param("searchStartDate", "1999-12-12").param("searchEndDate", "2018-12-12").param("mainGroupNumber", "2se3wer")
				.param("subGroupNumber", "12s4").param("lineOfBusiness", "Dental").headers(httpHeaders))
				.andExpect(status().isOk());
	}*/
	
	/*
	 * Passing all required and optional field to cover all validation code code:200
	 * */
	/*@Test
	public void successWithAllQueryParams1() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12345678");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(getCoverage());
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		getAccountsCacheList().clear();
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenReturn(getAccountsCacheList());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.param("searchStartDate", "1999-12-12").param("searchEndDate", "2018-12-12").param("mainGroupNumber", "2se3wer")
				.param("subGroupNumber", "12s4").param("lineOfBusiness", "Dental").headers(httpHeaders))
				.andExpect(status().isOk());
	} */
	
	/*@Test
	public void successWithAllQueryParams2() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12345678");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		 List<Coverage> coverageList = getCoverage();
		 coverageList.addAll(getCoverage());
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(coverageList);
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		List<AccountsCache> acctCacheList = getAccountsCacheList();
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenReturn(acctCacheList);
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.param("searchStartDate", "1999-12-12").param("searchEndDate", "2018-12-12").param("mainGroupNumber", "2se3wer")
				.param("subGroupNumber", "12s4").param("lineOfBusiness", "Dental").headers(httpHeaders))
				.andExpect(status().isOk());
	}*/
	
	/*
	 * Required coverage fields are not received, code:404
	 * */
	/*@Test
	public void coverageRequiredfieldNotPassed() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12345678");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		List<Coverage> coverageList = getCoverage();
		coverageList.get(0).setLineOfBusiness("test");
		coverageList.get(0).setEffectiveDate(null);
		coverageList.get(0).setTerminationDate(null);
		coverageList.get(0).getPremium().setAccountNumber("");
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(coverageList);
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenReturn(getAccountsCacheList());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678").headers(httpHeaders))
				.andExpect(status().isNotFound());
	} */
	
	/*
	 * Relationship not self, code:404
	 * */
	/*@Test
	public void relationshipNotSelf() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12345678");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		List<Coverage> coverageList = getCoverage();
		coverageList.get(0).setRelationship("test");
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(coverageList);
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenReturn(getAccountsCacheList());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678").headers(httpHeaders))
				.andExpect(status().isNotFound());
	}*/
	
	/*
	 * required field memberId not passed, errorCode - 422
	 * */
	@Test
	public void subGroupNumberFieldIsNotPassed() throws Exception {
		httpHeaders = getHttpHeaders();
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("mainGroupNumber", "2se3wer").headers(httpHeaders)).andExpect(status().isUnprocessableEntity());
	}
	
	/*
	 * All wrong field passed, errorCode - 422
	 * */
	@Test
	public void allWrongFieldsPassedFailedValidation() throws Exception {
		httpHeaders = getHttpHeaders();
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.param("searchStartDate", "19999-12-12").param("searchEndDate", "20918-12-12").param("mainGroupNumber", "2se83wer")
				.param("subGroupNumber", "12s94").param("lineOfBusiness", "Dental1").headers(httpHeaders))
				.andExpect(status().isUnprocessableEntity());
	}
	

	/*
	 * required field memberId not passed, errorCode - 422
	 * */
	@Test
	public void memberIdNotPassed() throws Exception {
		httpHeaders = getHttpHeaders();
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON)
				.headers(httpHeaders)).andExpect(status().isUnprocessableEntity());
	}
	
	/*
	 * Exception is thrown while connecting with mongoDB code - 500
	 * */
	@Test
	public void mongoDBExceptionThrown() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12345678");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(getCoverage());
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenThrow(new RuntimeException());
		/*mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678").headers(httpHeaders))
				.andExpect(status().isInternalServerError());*/
	}
	
	/*
	 * Subscriber not found code - 404
	 * */
	@Test
	public void subscribersNotFound() throws Exception {
		httpHeaders = getHttpHeaders();
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenThrow(new ApiException(status().isNotFound().toString(), "Subscriber not found", Boolean.FALSE));
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678").headers(httpHeaders))
				.andExpect(status().isNotFound());
	}

	/*
	 * requestingMemberId is not equal to memberId, code = 404
	 * */
	@Test
	public void unAuthorizedDependants() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.headers(httpHeaders)).andExpect(status().isNotFound());
	}
	
	/*
	 * When no records found in the mongoDB and System will create AccountId with other details
	 * */
	/*@Test
	public void newRecordCreationTesting() throws Exception {
		httpHeaders = getHttpHeaders();
		httpHeaders.set("X-HZN-RequestingMemberId", "12345678");
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(getCoverage());
		when(externalIdentifierService.getExternalId(anyString(), any())).thenReturn("12345678901");
		List<AccountsCache> accountsCacheList = getAccountsCacheList();
		accountsCacheList.clear();
		when(apiMongoTemplate.find(anyObject(), eq(AccountsCache.class),anyString(), anyInt())).thenReturn(accountsCacheList);
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.param("searchStartDate", "1999-12-12").param("searchEndDate", "2018-12-12").param("mainGroupNumber", "2se3wer")
				.param("subGroupNumber", "12s4").param("lineOfBusiness", "Dental").headers(httpHeaders))
				.andExpect(status().isOk());
	} */
	
	/*
	 * Empty coverage found, code = 404
	 * */
	@Test
	public void subscriberNotFound() throws Exception {
		httpHeaders = getHttpHeaders();
		when(customHeaderParameterProvider.getHttpHeaderParameters()).thenReturn(httpHeaders.toSingleValueMap());
		List<Coverage> coverageList = getCoverage();
		coverageList.clear();
		when(coverageService.searchCoveragesByCoverageSearchInput(anyString(), anyObject(), anyString(), anyBoolean())).thenReturn(coverageList);
		mockMvc.perform(get(ACCOUNTS_URL).contentType(MediaType.APPLICATION_JSON).param("memberId", "12345678")
				.headers(httpHeaders)).andExpect(status().isNotFound());
	}

	private List<AccountsCache> getAccountsCacheList() {
		List<AccountsCache> accountsCacheList = new ArrayList<>();
		AccountsCache accountsCache = new AccountsCache();
		accountsCache.setAccountId("12345678");
		accountsCache.setAccountNumber("12345678");
		accountsCache.setCardId("12345678");
		accountsCache.setEnrollmentSourceSystem("12345678");
		accountsCache.setModifiedDatetime(LocalDateTime.now());
		accountsCache.setModifiedUserId("12345678");
		accountsCacheList.add(accountsCache);

		return accountsCacheList;
	}

	private List<Coverage> getCoverage() {
		List<Coverage> coverageList = new ArrayList<>();
		Coverage coverage = new Coverage();
		Subgroup subgroup = new Subgroup();
		Internal internal = new Internal();
		Premium premium = new Premium();
		premium.setAccountNumber("12345678");
		com.hzn.api.di.members.coverages.domain.Subscriber subscriber = new com.hzn.api.di.members.coverages.domain.Subscriber();
		subscriber.setCardId("12345678");
		subscriber.setSubscriberId("12345678");
		subgroup.setMainGroupNumber("12345678");
		subgroup.setSubGroupNumber("121212");
		internal.setHorizonId("12345678");
		internal.setEnrollmentSourceSystem("MTEST");
		subscriber.setCardId("12345678");
		coverage.setCoverageId("1234567");
		coverage.setSubgroup(subgroup);
		coverage.setSubscriber(subscriber);
		coverage.setInternal(internal);
		coverage.setPremium(premium);
		coverage.setEffectiveDate(LocalDate.parse("2017-12-12"));
		coverage.setTerminationDate(LocalDate.parse("2018-12-12"));
		coverage.setRelationship("Self");
		coverageList.add(coverage);
		return coverageList;
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-HZN-ClientSubmitDateTime", "2010-07-30T15:05:00.000Z");
		httpHeaders.set("X-HZN-GatewayURL", GATEWAY_URL);
		httpHeaders.set("X-HZN-ClientName", "zipari");
		httpHeaders.set("X-HZN-RequestingMemberId", "123456780");
		httpHeaders.set("X-HZN-Internal", "true");
		return httpHeaders;
	}
}


package com.hzn.api.di.subscribers.accounts.config;

import com.hzn.api.common.config.HorizonAppConfig;
import com.hzn.api.di.members.coverages.service.restclient.CoverageRestClient;
import com.hzn.di.api.subscribers.service.restclient.SubscriberRestClient;
import com.lh.digital.integration.config.SpringToolsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;


@Configuration
@ComponentScan(value = {"com.hzn.api.di.members.coverages"})
@Import({SpringToolsConfig.class, HorizonAppConfig.class})
public class AccountsAppConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${application.coverages.rest:false}")
    private boolean isRestCallToCoverages;

    @Bean
    public CoverageRestClient coverageService() {
       
        return new CoverageRestClient();
    }
    @Bean
    public SubscriberRestClient subscriberService()
    {
       
        return new SubscriberRestClient();
    }
}
