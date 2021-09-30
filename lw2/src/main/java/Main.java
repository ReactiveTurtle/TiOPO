import javafx.util.Pair;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Main {
    public static final String rootSiteUrl = "http://91.210.252.240/broken-links/";

    public static void main(String[] args) {
        List<Integer> validUrlStatusList = new ArrayList<>();
        List<String> validUrls = new ArrayList<>();
        List<String> invalidUrlStatusList = new ArrayList<>();
        List<String> invalidUrls = new ArrayList<>();
        List<String> testUrls = new ArrayList<>();
        String root = null;
        try {
            root = URLExtensions.getRoot(rootSiteUrl);
            testUrls.add(rootSiteUrl);
        } catch (MalformedURLException e) {
            invalidUrlStatusList.add("Invalid url");
            invalidUrls.add(rootSiteUrl);
        }
        while (testUrls.size() > 0) {
            String urlAsString = testUrls.remove(0);
            try {
                Pair<Integer, List<String>> statusCodeAndLinks = tryGetAllLinks(urlAsString);
                List<String> links = statusCodeAndLinks.getValue();
                if (urlAsString.startsWith(root)) {
                    for (String link : links) {
                        boolean isLinkNotVisited = !validUrls.contains(link)
                                && !invalidUrls.contains(link)
                                && !testUrls.contains(link);
                        if (isLinkNotVisited) {
                            testUrls.add(link);
                        }
                    }
                }
                validUrlStatusList.add(statusCodeAndLinks.getKey());
                validUrls.add(urlAsString);
            } catch (ValidationException e) {
                invalidUrlStatusList.add("Invalid url");
                invalidUrls.add(urlAsString);
            } catch (HttpStatusException e) {
                invalidUrlStatusList.add(e.getStatusCode() + "");
                invalidUrls.add(urlAsString);
            }
        }
        System.out.println(String.join("\n", validUrls));
        System.out.println("------");
        System.out.println(String.join("\n", invalidUrls));
        try {
            BufferedWriter validUrlOutput = new BufferedWriter(new FileWriter("validUrls.txt"));
            for (int i = 0; i < validUrls.size(); i++) {
                validUrlOutput.write(validUrls.get(i) + " - " + validUrlStatusList.get(i));
                validUrlOutput.newLine();
            }
            validUrlOutput.write(validUrls.size() + "");
            validUrlOutput.newLine();
            validUrlOutput.write(new Date().toString());
            validUrlOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter invalidUrlOutput = new BufferedWriter(new FileWriter("invalidUrls.txt"));
            for (int i = 0; i < invalidUrls.size(); i++) {
                invalidUrlOutput.write(invalidUrls.get(i) + " - " + invalidUrlStatusList.get(i));
                invalidUrlOutput.newLine();
            }
            invalidUrlOutput.write(invalidUrls.size() + "");
            invalidUrlOutput.newLine();
            invalidUrlOutput.write(new Date().toString());
            invalidUrlOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Pair<Integer, List<String>> tryGetAllLinks(String urlString) throws ValidationException, HttpStatusException {
        List<String> links = new ArrayList<>();
        UrlWalker urlWalker = new UrlWalker(urlString);
        try {
            Document document = Jsoup.parse(new URL(urlString), 5000);
            int statusCode = document.connection().response().statusCode();
            Elements elements = document.getElementsByTag("a");
            elements.addAll(document.getElementsByTag("script"));
            elements.addAll(document.getElementsByTag("img"));
            elements.addAll(document.getElementsByTag("link"));
            elements.addAll(document.getElementsByTag("iframe"));
            for (Element element : elements) {
                String nextLink = element.attr("href");
                if (nextLink.isEmpty()) {
                    nextLink = element.attr("src");
                }
                if (!nextLink.isEmpty() && !nextLink.startsWith("#")) {
                    try {
                        String newUrl = urlWalker.walk(nextLink);
                        links.add(newUrl);
                    } catch (ValidationException ignore) {
                        links.add(nextLink);
                    }
                }
            }
            return new Pair<>(statusCode, links);
        } catch (HttpStatusException e) {
            throw new HttpStatusException(e.getMessage(), e.getStatusCode(), e.getUrl());
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public static boolean isKnownUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
