
package serversbu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


  
public class ServerSBU extends Application implements Runnable {
      Thread t =new Thread(this);
    static ServerSocket sc;
    @Override
    public void start(Stage primaryStage) {
        ServerSBU sbuv = new ServerSBU();
         sbuv.Tstart(2020);
    }

    
    public static void main(String[] args) {
        launch(args);
    }
      

    public void Tstart(int port) {
        try {
             
            sc=new ServerSocket(port);
            t.start();
        } catch (IOException ex) {
         System.out.println(ex);
//          cache.clear();
          
        }
    }

        @Override
        public void run() {
         
        while(true){
               try {
                  Socket s=sc.accept();
                  ConnectionHandler c;
                  c = new ConnectionHandler(new ObjectInputStream(s.getInputStream()),new ObjectOutputStream(s.getOutputStream()));
                 new Thread(() -> {
                         c.listen();
                 }).start();


               } catch (IOException ex) {
                   System.out.println(ex);
           }
        }
        }
    
}
