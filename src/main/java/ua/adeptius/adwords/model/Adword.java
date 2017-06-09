package ua.adeptius.adwords.model;


public class Adword {

    private String name;
    private String citeUrl;
    private String url;
    private String description;

    public Adword(String name, String citeUrl, String url, String description) {
        this.name = name;
        this.citeUrl = citeUrl;
        this.url = url;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCiteUrl() {
        return citeUrl;
    }

    public void setCiteUrl(String citeUrl) {
        this.citeUrl = citeUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Adword{" +
                "name='" + name + '\'' +
                ", citeUrl='" + citeUrl + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
