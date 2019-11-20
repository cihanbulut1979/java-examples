package tr.com.cihan.java.networking;

import java.net.URI;

public class ParseURI {
    public static void main(String[] args) throws Exception {

        URI aURI = new URI("http://example.com:80/docs/books/tutorial"
                           + "/index.html?name=networking#DOWNLOADING");

        System.out.println("URL = " + aURI.toURL());
        
        
        URI uri = new URI("http", "example.com:80", "/docs/books/tutorial/index.html", "name=networking", "DOWNLOADING");
        
        System.out.println("URL = " + uri.toURL());
    }
}