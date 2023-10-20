import java.net.URISyntaxException;
import java.util.Scanner;
import java.net.URL;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        System.out.print("Введите запрос: ");
        Scanner in = new Scanner(System.in);
        String search = in.nextLine();

        Searching test = new Searching();
        URL temp = test.Search(search);

        try {
            URI uri = temp.toURI();
            System.out.println("Ожидайте, страница загружается в браузере...");
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(uri);
                }
            }
        } catch (IOException | InternalError e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException c){
            System.out.println();
        }
    }
}