package com.github.bubinimara.app.data.entity.mapper;

import com.github.bubinimara.app.data.entity.ConfigurationEntity;
import com.github.bubinimara.app.domain.Configuration;

/**
 * Created by davide.
 */
public class ConfigurationMapper {
    public static Configuration transform(ConfigurationEntity configurationEntity){
        Configuration configuration = new Configuration();
        configuration.setChange_keys(configurationEntity.getChange_keys());
        configuration.setImages(transform(configurationEntity.getImages()));
        return configuration;
    }

    private static Configuration.Image transform(ConfigurationEntity.Image imageConfig) {
        Configuration.Image image = new Configuration.Image();
        if(imageConfig == null){
            return image;
        }
        image.setBase_url(imageConfig.getBase_url());
        image.setPoster_sizes(imageConfig.getPoster_sizes());
        return image;
    }

}
