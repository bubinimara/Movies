package com.github.bubinimara.app.data.entity;

import java.util.List;

/**
 * Created by davide.
 */
public class ConfigurationEntity {

    private Image images;
    private List<String> change_keys;

    public ConfigurationEntity() {
    }

    public Image getImages() {
        return images;
    }


    public void setImages(Image images) {
        this.images = images;
    }

    public List<String> getChange_keys() {
        return change_keys;
    }

    public void setChange_keys(List<String> change_keys) {
        this.change_keys = change_keys;
    }

    public static class Image{
        private String base_url;
        private List<String> poster_sizes;

        public Image() {
        }

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }

        public List<String> getPoster_sizes() {
            return poster_sizes;
        }

        public void setPoster_sizes(List<String> poster_sizes) {
            this.poster_sizes = poster_sizes;
        }
    }

}
