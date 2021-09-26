/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominoMultiplayer;

import dominoMultiplayer.classes.Domino;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JButton;

//TODO: Tem que resolver uns try/catch ai no meio
public class Client extends Application{

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    private int playerId;
    private int playerTurn;
    
    private Domino game;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    private Client(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        
        playerId = ois.readInt();
        System.out.println("Connected as player " + playerId);
        
        playerTurn = ois.readInt();
        System.out.println("Turn of player: " + playerTurn);
        
        game = (Domino) ois.readObject();
    }

    private void start() throws IOException, ClassNotFoundException {
        while (true) {
            if (playerTurn == playerId) {
              // turno p/ jogar
              
              //Buttons.enable
            } else {
              //espera
              
              //Buttons.disable
              game = (Domino) ois.readObject();
              playerTurn = ois.readInt();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String ip = "localhost";
        int port = 42069;

        Client client = new Client( InetAddress.getByName(ip), port);
        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();
        
        launch(args);
    }
}