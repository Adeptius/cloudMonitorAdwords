package ua.adeptius.model;


public class HttpReport {

    private boolean wordIsFounded;
    private int responceCode;
    private HttpStatus httpStatus;
    private String url;

    public HttpReport(boolean wordIsFounded, int responceCode, HttpStatus httpStatus, String url) {
        this.wordIsFounded = wordIsFounded;
        this.responceCode = responceCode;
        this.httpStatus = httpStatus;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isWordIsFounded() {
        return wordIsFounded;
    }

    public void setWordIsFounded(boolean wordIsFounded) {
        this.wordIsFounded = wordIsFounded;
    }

    public int getResponceCode() {
        return responceCode;
    }

    public void setResponceCode(int responceCode) {
        this.responceCode = responceCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "HttpReport{" +
                "wordIsFounded=" + wordIsFounded +
                ", responceCode=" + responceCode +
                ", httpStatus=" + httpStatus +
                ", url='" + url + '\'' +
                '}';
    }
}
