/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.TimelineController;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.time.LocalDate;
import java.time.Month;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author My Laptop
 */
public class UPDATE_USER_INFOController {

    public TextField name;
  public TextField lname;
  public TextField usn;
  public TextField psw;
  public DatePicker brd;
  public ImageView img;
  public String picname;
  public String username;
    
   ObjectInputStream in;
   ObjectOutputStream out;
    public void init(String s){
        username=s;
        System.out.println(username);
       iner();
        
    }
   public void save(ActionEvent a){
        if(picname!=null){
             
           if(name.getText().trim().length()>0){
               
                if(lname.getText().length()>0){                   
                    if(brd.getValue()!=null){
                        try{
                          if(brd.getValue().toString().length()>0){
                                                          
                        if(usn.getText().trim().length()>0){
                            
                           if(psw.getText().length()>=8){
                               
                               if(psw.getText().matches("^[a-zA-Z0-9]*$")){
                                  try {
                                     
                                     Socket clientsocket =new Socket("localhost",2020);
                                     out = new ObjectOutputStream(clientsocket.getOutputStream());
                                     in=new ObjectInputStream(clientsocket.getInputStream());

                                   
                                      String str ="[update_user_inform],"+usn.getText()+","+name.getText()+","+lname.getText()+","+psw.getText()+","+brd.getValue().toString()+","+picname;
                                      
                                    
                                      out.writeObject(str);
                                      out.flush(); 
                                      Object os =in.readObject();
                                      String from_Server = os.toString();
                                      if(from_Server.startsWith("registred")){
                                                 FXMLLoader loader;
           
        
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
    public void iner() {
        Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("get_user_info,"+username);
                 out.flush();
                  Object os;
                  os = in.readObject();
                  String from_Server = os.toString();
                  System.out.println(from_Server+"f");
                         String[] parameters=from_Server.split(",");
                         name.setText(parameters[0]);
                         lname.setText(parameters[1]);
                         usn.setText(username);
                        // brdsetText(parameters[2]);
                         System.out.println(parameters[2]);
                         String[] zaman = parameters[2].split("-");
                    
                        brd.setValue(LocalDate.of(Integer.parseInt(zaman[0]),Integer.parseInt(zaman[1]),Integer.parseInt(zaman[2])));
                       
                         String pnme=parameters[3];
                         pnme=pnme.split("///")[1];
                         pnme=pnme.replaceAll("%20"," ");
                         picname=pnme;
                       img.setImage(new Image(Paths.get(pnme).toUri().toString()));
                       psw.setText(parameters[6]);
                       
                         
                         
                              
               
        } catch (Exception ex) {
            System.out.println(ex+"prfex");
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
}