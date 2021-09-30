import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UrlWalker {
    private String srcUrl;

    public UrlWalker(String url) throws ValidationException {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new ValidationException(e.getMessage());
        }
        this.srcUrl = url;
    }

    public String walk(String dstPath) throws ValidationException {
        try {
            new URL(dstPath);
            return dstPath;
        } catch (MalformedURLException e) {
            if (e.getMessage().startsWith("unknown protocol: "))
                throw new ValidationException(e.getMessage());
        }

        URL url = null;
        try {
            url = new URL(srcUrl);
        } catch (MalformedURLException e) {
            throw new ValidationException(e.getMessage());
        }
        String rootUrl = url.getProtocol() + "://" + url.getHost();
        if (url.getPort() != -1) {
            rootUrl += ":" + url.getPort();
        }
        String result = rootUrl;
        Path path = Paths.get(url.getPath());
        while (dstPath.startsWith("/")
                || dstPath.startsWith("./")
                || dstPath.startsWith("../")) {
            if (dstPath.startsWith("/")) {
                dstPath = dstPath.substring(1);
            } else if (dstPath.startsWith("./")) {
                dstPath = dstPath.substring(2);
            } else if (dstPath.startsWith("../")) {
                dstPath = dstPath.substring(3);
                path = path.getParent();
                if (path == null) {
                    path = Paths.get("");
                }
            }
        }
        if (!srcUrl.endsWith("/")) {
            path = path.getParent();
            if (path == null) {
                path = Paths.get("");
            }
        }
        result += path
                .resolve(dstPath)
                .toString()
                .replaceAll("\\\\", "/");
        return result;
    }

    public String getUrl() {
        return srcUrl;
    }
}
