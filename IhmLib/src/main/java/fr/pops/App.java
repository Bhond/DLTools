package fr.pops;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App  {
    // extends Application
//    public void start(Stage stage) {
//        var javaVersion = SystemInfo.javaVersion();
//        var javafxVersion = SystemInfo.javafxVersion();
//
//        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        var scene = new Scene(new StackPane(label), 640, 480);
//        stage.setScene(scene);
//        stage.show();
//
//    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8163);
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while(scanner.hasNextLine()){
                out.println(scanner.nextLine());
                System.out.println(in.nextLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}