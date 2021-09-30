import java.net.MalformedURLException;
import java.net.URL;

public class URLExtensions {
    public static String getRoot(String url) throws MalformedURLException {
        URL exampleUrl = new URL(url);
        String rootUrl = exampleUrl.getProtocol() + "://" + exampleUrl.getHost();
        if (exampleUrl.getPort() != -1) {
            rootUrl += ":" + exampleUrl.getPort();
        }
        return rootUrl;
    }
}
