package constants;

public enum EndPoint {

    login("/login"),
    registration("/register");

    public final String url;
    EndPoint(String url) {
        this.url = url;
    }
}
