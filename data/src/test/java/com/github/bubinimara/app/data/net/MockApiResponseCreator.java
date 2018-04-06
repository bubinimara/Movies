package com.github.bubinimara.app.data.net;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import okio.Okio;

/**
 * Created by davide.
 */

public class MockApiResponseCreator {

    public String getResponseBody(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(fileName);
        return Okio.buffer(Okio.source(inputStream)).readString(StandardCharsets.UTF_8);
    }
}
