package com.github.bubinimara.movies.data.entity.mapper;

import com.github.bubinimara.movies.data.entity.ConfigurationEntity;
import com.github.bubinimara.movies.domain.Configuration;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by davide.
 */
public class ConfigurationMapperTest {
    private static final String PREFIX_CHANGE_KEYS = "change_keys";
    private static final String PREFIX_POSTER_SIZE = "poster_size";
    private static final String BASE_URL = "http://base.url/mock";

    private ConfigurationEntity configurationEntity;

    @Before
    public void setUp() throws Exception {
        configurationEntity = createMockConfigurationEntity();
    }

    @Test
    public void transform() {
        Configuration configuration = ConfigurationMapper.transform(configurationEntity);
        assertMockConfigurationEntity(configuration);
    }

    private void assertMockConfigurationEntity(Configuration configuration){
        assertNotNull(configuration);
        assertArrayEquals(configuration.getChange_keys().toArray()
                ,createListWithPrefix(PREFIX_CHANGE_KEYS).toArray());

        Configuration.Image images = configuration.getImages();
        assertNotNull(images);
        assertThat(images.getBase_url(),is(BASE_URL));
        assertArrayEquals(images.getPoster_sizes().toArray()
                ,createListWithPrefix(PREFIX_POSTER_SIZE).toArray());

    }

    private ConfigurationEntity createMockConfigurationEntity(){
        ConfigurationEntity configurationEntity = new ConfigurationEntity();
        configurationEntity.setChange_keys(createListWithPrefix(PREFIX_CHANGE_KEYS));
        configurationEntity.setImages(createMockImage());
        return configurationEntity;
    }

    private ConfigurationEntity.Image createMockImage() {
        ConfigurationEntity.Image image = new ConfigurationEntity.Image();
        image.setBase_url(BASE_URL);
        image.setPoster_sizes(createListWithPrefix(PREFIX_POSTER_SIZE));
        return image;
    }

    private List<String> createListWithPrefix(String prefix) {
        int size = 33;
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(prefix+i);
        }
        return list;
    }
}