
package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProfileController{
    public Label nome_etprenom;
    public Label usnnom;
    public Label brthd;
    public Label followers_conter;
    public Label following_counter;
    public ImageView img;
    ObjectOutputStream out;
    ObjectInputStream in ;
    public String usn;
    public String mainusername;
    
    public void init(String s,String musn){
        usn=s;
        mainusername=musn;
        System.out.println(usn+"50"+musn);
        iner();
        
    }
    public void showmenu(MouseEvent me){
        try {
               FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));
                Parent root=loader.load();
                MenuController mt = loader.getController();
                mt.init(mainusername);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(mainusername);
                    stage.show();
                   ((Node)(me.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void Follow_action(ActionEvent act){
           Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("choose_follower,"+usn+","+mainusername);
                 out.flush();
                  Object os;
                  os = in.readObject();
                  String from_Server = os.toString();
                   System.out.println(from_Server+"f");
                  if(from_Server.equals("follower added succssesfully")){
                       Alert alert = new Alert(Alert.AlertType.INFORMATION,from_Server);
                       alert.setTitle("You Started following "+usn);
                       alert.showAndWait();
                       iner();
                  }
                  else if(from_Server.equals("you already have this user as follower")){
                       Alert alert = new Alert(Alert.AlertType.INFORMATION,from_Server);
                       alert.setTitle(usn+" is in your following list");
                       alert.showAndWait();
                  }
                  else{
                       Alert alert = new Alert(Alert.AlertType.ERROR,from_Server);
                       alert.setTitle("following not accepted");
                       alert.showAndWait(); 
                  }
                  //System.out.println(from_Server+"f");
        }
        catch(Exception ex){
            System.out.println(ex+"follow_action");
        }
    }
  
     public void following(ActionEvent a){
    }
     public void block_action(ActionEvent a){
     }
      public void mute_action(ActionEvent a){}
       public void repost_action(ActionEvent a){}
        public void seemore_action(ActionEvent a){}
        public void like_action(ActionEvent a){}
    
    public void iner() {
          Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("get_user_info,"+usn);
                 out.flush();
                  Object os;
                  os = in.readObject();
                  String from_Server = os.toString();
                  System.out.println(from_Server+"f");
                         String[] parameters=from_Server.split(",");
                         nome_etprenom.setText(parameters[0]+" "+parameters[1]);
                         usnnom.setText(usn);
                         brthd.setText(parameters[2]);
                         String pnme=parameters[3];
                         pnme=pnme.split("///")[1];
                         pnme=pnme.replaceAll("%20"," ");
                        img.setImage(new Image(Paths.get(pnme).toUri().toString()));
                        String follower = parameters[4];
                        String following = parameters[5];
                        //System.out.println(parameters[5]+"888");
                        followers_conter.setText(follower);
                       following_counter.setText(following);
                        // Image m =new Image(Paths.get(pnme).toUri().toString()); 
                         
                         System.out.println(pnme);
                        // img.setImage(m);
                         
                         
                              
               
        } catch (Exception ex) {
            System.out.println(ex+"prfex");
        }
    }
}
//         FXMLLoader loader;
//           
//           try {
//               loader = new FXMLLoader(getClass().getResource("/View/profile.fxml"));
//                Parent root=loader.load();
//                    ProfileController tm = loader.getController();
//                    tm.init(usn);
//                    Scene scene = new Scene(root);
//                    Stage stage = new Stage();
//                    stage.setScene(scene);
//                    stage.setTitle(usn);
//                    String pnme=from_Server.split(",")[1];
//                    pnme=pnme.split("///")[1];
//                    pnme=pnme.replaceAll("%20"," ");
//                  
//                    stage.getIcons().add(new Image(Paths.get(pnme).toUri().toString()));
//                    stage.show();
//                   ///((Node)(a.getSource())).getScene().getWindow().hide();
//           } catch (IOException ex) {
//               System.out.println(ex);
//           }
//        Socket clientsocket;
//        try {
//             clientsocket = new Socket("localhost",2020);
//             out = new ObjectOutputStream(clientsocket.getOutputStream());
//             in = new ObjectInputStream(clientsocket.getInputStream());
//                 out.writeObject("get_user_info,"+usn);
//                 out.flush();
//                  Object os;
//                  os = in.readObject();
//                  String from_Server = os.toString();
//                  System.out.println(from_Server+"f");
//                         String[] parameters=from_Server.split(",");
//                         nome_etprenom.setText(parameters[0]+" "+parameters[1]);
//                         usnnom.setText(usn);
//                         brthd.setText(parameters[2]);
//                         String pnme=parameters[3];
//                         pnme=pnme.split("///")[1];
//                         pnme=pnme.replaceAll("%20"," ");
//                        img.setImage(new Image(Paths.get(pnme).toUri().toString()));
//                        String follower = parameters[4];
//                        String following = parameters[5];
//                        //System.out.println(parameters[5]+"888");
//                        followers_conter.setText(follower);
//                       following_counter.setText(following);
//                        // Image m =new Image(Paths.get(pnme).toUri().toString()); 
//                         
//                         System.out.println(pnme);
//                        // img.setImage(m);
//                         
//                         
//                              
//               
//        } catch (Exception ex) {
//            System.out.println(ex+"prfex");
//        }
//              System.out.println("444prfex");
//   
//
//    }
//}
