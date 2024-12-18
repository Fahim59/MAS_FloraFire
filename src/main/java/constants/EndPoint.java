package constants;

public enum EndPoint {

    login("/login");

    public final String url;
    EndPoint(String url) {
        this.url = url;
    }
}
