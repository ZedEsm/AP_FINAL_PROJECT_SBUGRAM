
package Controller;

import Model.PageLoader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class RegistreController{
  public TextField name;
  public TextField lname;
  public TextField usn;
  public TextField psw;
  public TextField A_Q;
  public DatePicker brd;
  public ImageView img;
  public String picname;
  public Label Q_A;
  public String from_Server ;
  
   ObjectInputStream in;
   ObjectOutputStream out;
   public void init(){
         try {
                                     
                                     Socket clientsocket =new Socket("localhost",2020);
                                     out = new ObjectOutputStream(clientsocket.getOutputStream());
                                     in=new ObjectInputStream(clientsocket.getInputStream());
                                      out.writeObject("select_random_Q,");
                                      out.flush(); 
                                      Object os =in.readObject();
                                      from_Server = os.toString();
                                      Q_A.setText(from_Server);
                                      
                                      
         }catch(Exception e){
             System.out.println(e);
         }
   }
   
  
  public void registre(ActionEvent actionevent){
      
  
        if(picname!=null){
             
           if(name.getText().trim().length()>0){
               
                if(lname.getText().length()>0){                   
                    if(brd.getValue()!=null){
                        try{
                          if(brd.getValue().toString().length()>0){
                                                          
                        if(usn.getText().trim().length()>0){
                            
                           if(psw.getText().length()>=8){
                               
                               if(psw.getText().matches("^[a-zA-Z0-9]*$")){
                                   if(A_Q.getText().length()>0){
                                  try {
                                     
                                     Socket clientsocket =new Socket("localhost",2020);
                                     out = new ObjectOutputStream(clientsocket.getOutputStream());
                                     in=new ObjectInputStream(clientsocket.getInputStream());

                                   
                                      String str ="[create_user],"+usn.getText()+","+name.getText()+","+lname.getText()+","+psw.getText()+","+brd.getValue().toString()+","+picname+","+from_Server+","+A_Q.getText();
                                      
                                    
                                      out.writeObject(str);
                                      out.flush(); 
                                      Object os =in.readObject();
                                      String from_Server = os.toString();
                                      if(from_Server.startsWith("registred")){
                                                 FXMLLoader loader;
           
           try {
       loader = new FXMLLoader(getClass().getResource("/View/timeline.fxml"));
                Parent root=loader.load();
                    TimelineController tm = loader.getController();
                    tm.init(usn.getText());
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(usn.getText());
                    String pnme=from_Server.split(",")[1];
                    pnme=pnme.split("///")[1];
                    pnme=pnme.replaceAll("%20"," ");
                    stage.getIcons().add(new Image(Paths.get(pnme).toUri().toString()));
                    stage.show();
                   ((Node)(actionevent.getSource())).getScene().getWindow().hide();
           } catch (IOException ex) {
               System.out.println(ex);
           }
                                        //  new PageLoader().load("timeline");
                                      }
                                      else{
                                            Alert alert = new Alert(Alert.AlertType.ERROR,from_Server);
                                            alert.setTitle("User available");
                                            alert.showAndWait();
                                      }
                                      
                                                                          
                                  
                                    }catch (Exception ex) {
                                      System.out.println(ex);
                                    }
                                   }
                                else{
                                    Alert alert = new Alert(Alert.AlertType.ERROR,"ANSWER THE QUESTION");
                                    alert.setTitle("FIRST ANSWER THE QUESTION :)");
                                    alert.showAndWait();
                                }
                                }
                                else{
                                    Alert alert = new Alert(Alert.AlertType.ERROR,"Use 8 characters or more for your password");
                                    alert.setTitle("Choose better password!");
                                    alert.showAndWait();
                                }
                           }
                           else{
                                Alert alert = new Alert(Alert.AlertType.ERROR,"Enter a password");
                                alert.setTitle("Password not entered!");
                                alert.showAndWait();
                           }
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Enter your username");
                            alert.setTitle("UserName not entered");
                            alert.showAndWait();
                        }  
                        }
                        
                           
                
                         }
                        catch(Exception ex){
                          System.out.println("99");
                          Alert alert = new Alert(Alert.AlertType.ERROR,"Enter your birthday");
                          alert.setTitle("BirthDay not entered");
                          alert.showAndWait();         
                        }
                    }
                     
                    else{
                        System.out.println("98a9a9");
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Enter your birthday");
                        alert.setTitle("BirthDay not entered");
                        alert.showAndWait();         
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Enter a lastname");
                    alert.setTitle("LastName not entered!");
                    alert.showAndWait();
                }  
            }   
            else{
                 Alert alert = new Alert(Alert.AlertType.ERROR,"Enter a name");
                 alert.setTitle("Name not entered!");
                 alert.showAndWait();
            }   
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Select an image");
            alert.setTitle("Image not found!");
            alert.showAndWait();
        }
      
  
  }
     public void choose_img(ActionEvent actionEvent){
               FileChooser fileChooser = new FileChooser();
               File selectedFile = fileChooser.showOpenDialog(null);

               if (selectedFile != null) {
                              img.setImage((new Image(Paths.get(selectedFile.getPath().toString()).toUri().toString())));
                              picname=Paths.get(selectedFile.getPath().toString()).toUri().toString();
                }
     }
         public void Back_To_Login(ActionEvent actionEvent){
        
             Parent root;
           try {
               root = FXMLLoader.load(getClass().getResource("/View/FXMLDocument.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                     stage.getIcons().add(new Image(Paths.get("87390.png").toUri().toString()));

                    stage.setScene(scene);
                    stage.show();
                   ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
           } catch (IOException ex) {
               System.out.println(ex);
           }
     }

    
}
