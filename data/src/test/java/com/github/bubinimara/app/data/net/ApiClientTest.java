package com.github.bubinimara.app.data.net;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertNotNull;

/**
 * Created by davide.
 */
public class ApiClientTest {
    private static final String FAKE_KEY = "MY_FAKE_KEY";

    private MockWebServer mockWebServer = new MockWebServer();
    private ApiClient apiClient;

    @Before
    public void setUp() throws Exception {
        apiClient = new ApiClient(FAKE_KEY,mockWebServer.url("/"));
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void getApiTmb() throws Exception {
        ApiTmb apiTmb = apiClient.getApiTmb();
        assertNotNull(apiTmb);
    }
}