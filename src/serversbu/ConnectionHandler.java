package serversbu;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import Model.Post;
import Model.PrivacyStatus;



public class ConnectionHandler {
    ObjectInputStream inputStream; 
    ObjectOutputStream outputStream;
    public static Map<String,Map<String,String>> cache=new ConcurrentHashMap<>();
    public String username;
    public String file_name = "UERS_F.dat";
    public String v ="_follower.dat";
    public String v2 ="_following.dat";
    public String of ="_follower.dat";
    public String gf ="_following.dat";;
    
    public ConnectionHandler(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream=inputStream;
        this.outputStream=outputStream;
        
    }

    public void close() {
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException ex) {
          
        }
      
    }
//
//    public void sendMessage(Message message) {
//        try {
//            outputStream.writeObject(message);
//        } catch (IOException ex) {
//        
//        }
//    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
     return outputStream;
    }
    public void listen(){
        boolean userexist = false;
       
        while(true){
            try {
                Object obj = inputStream.readObject();
                if(obj instanceof String){
                    String str = obj.toString();
                    int cut=  str.indexOf("],");
                    String cu=str.substring(cut+2);
                    String[] cmd = str.split(",");
                    if(cmd[0].equalsIgnoreCase("[create_user]")){
             
                      
                            File registred_user = new File(file_name);
                            if(registred_user.exists()){
                                FileInputStream fin = new FileInputStream(registred_user);
                                byte[] byt = new byte[fin.available()];
                                fin.read(byt);
                                String file_content = new String(byt);//check for unique
                                String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                   if(a[0].equalsIgnoreCase(cmd[1])){
                                      outputStream.writeObject(new String("User is already available \n"));
                                      outputStream.flush();
                                      userexist = true;
                                      break;
                                    }
                                    
                                }
                                
                                
                            }
                            if(userexist==false){
                                FileOutputStream fot = new FileOutputStream(file_name, true);
                                String wa = cmd[1];
                                cu = cu+"\n";
                                fot.write(cu.getBytes());
                                fot.close();
                              //  String of ="_follower.dat";
                              //  File foo = new File(cu+of);
//                                FileOutputStream fot1 = new FileOutputStream(foo, true);
                               FileOutputStream fot1 = new FileOutputStream(wa+"_follower.dat", true);
                                System.out.println("serve");
                           
                                fot1.close();
//                                String gf ="_following.dat";
//                                File mf = new File(cu+gf);
                                 FileOutputStream fot2 = new FileOutputStream(wa+"_following.dat", true);
//                                   FileOutputStream fot2 = new FileOutputStream(mf, true);
                           
                                fot2.close();
                                System.out.println("se11rve");
                                outputStream.writeObject(new String("registred,"+cmd[6]));
                                outputStream.flush();
                                System.out.println(cmd[1]+" register "+cmd[6]);
                                System.out.println(new java.util.Date().toString());
                            }
                            else{
                                userexist=false;
                            }
                        // save to file beshe
                    }
                    else if(cmd[0].equalsIgnoreCase("[Login_user]")){
                               FileInputStream fin = new FileInputStream(file_name);
                                byte[] byt = new byte[fin.available()];
                                fin.read(byt);
                                String file_content = new String(byt);
                               String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  if(a[0].equals(cmd[1])){
                                      if (a[3].equals(cmd[2])) {
                                          outputStream.writeObject(new String("Succeed,"+a[5]));
                                          
                                          outputStream.flush();
                                          userexist = true;
                                          System.out.println(cmd[1]+" Login");
                                          System.out.println(new java.util.Date().toString());
                                          break;
                                          
                                      }
                                      
                                  }
                                  
                                }
                                if(userexist==false){
                                      outputStream.writeObject(new String("usernme or password is incorrect\n"));
                                      outputStream.flush();
                                      
                                }
                                else{
                                userexist=false;
                               }
                        
                    }
                    else if(cmd[0].equalsIgnoreCase("get_user_info")){
                            
                                FileInputStream fin = new FileInputStream(file_name);
                                byte[] byt = new byte[fin.available()];
                                fin.read(byt);
                                String file_content = new String(byt);
                               String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  
                                  if(a[0].equals(cmd[1])){
                                       String usnn=cmd[1];
                                      // System.out.println("folo11");
//                                      File vf = new File(usnn+v);
//                                      
//                                         FileInputStream finn = new FileInputStream(vf);
System.out.println("serve");
                                       FileInputStream finn = new FileInputStream(usnn+"_follower.dat");
                                         System.out.println("folo12");
                                        byte[] byt1 = new byte[finn.available()];
                                        finn.read(byt1);
                                        String followers = new String(byt1);
                                        String[] asw1;
                                        if(followers.trim().length()>0){
                                        asw1 = followers.split("\n");
                                        }
                                        else{
                                           asw1=new String[0];
                                        }
//                                        File vfo = new File(usnn+v2);
                                        
                                        FileInputStream followi = new FileInputStream(usnn+"_following.dat");
//                                       FileInputStream followi = new FileInputStream(usnn+vfo);
                                        byte[] byt2 = new byte[followi.available()];
                                        followi.read(byt2);
                                        String following = new String(byt2);
                                        String[] asw2;
                                        if(following.trim().length()>0){
                                        asw2 = following.split("\n");
                                        }
                                        else{
                                           asw2=new String[0];
                                        }
                                        
                                        
                                        outputStream.writeObject(new String(a[1]+","+a[2]+","+a[4]+","+a[5]+","+asw1.length+","+asw2.length));
                                        outputStream.flush();
                                        userexist = true;
                                        break;
                                  }
                                  
                                }
                                if(userexist==true){
                                       userexist=false;
                                      
                                }
                              
                             }
                    else if(cmd[0].equalsIgnoreCase("[get_user_List]")){
                        String users = "";
                         FileInputStream fin = new FileInputStream(file_name);
                                byte[] byt = new byte[fin.available()];
                                fin.read(byt);
                                String file_content = new String(byt);
                               //  System.out.println(file_content);
                               String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  users+=a[0]+",";   
                                }
                                //System.out.println(users);
                                outputStream.writeObject(new String(users));
                                outputStream.flush();

                        
                    }
                    
                    else if(cmd[0].equalsIgnoreCase("[New_Post]")){
                        
                        String pst = cmd[1];
                        String pme = cmd[2];
                       // System.out.println(pst+" 9999  "+pme);
                       String usnn=cmd[3];
                       Post pos = new Post();
                       pos.setTitle(pst);
                       pos.setDescription(pme);
                       pos.setWriter(usnn);
                       pos.setStatus(PrivacyStatus.PUBLIC);
                       FileOutputStream fop = new FileOutputStream("post.dat",true);
                        
                         fop.write((pos.getWriter()+","+pos.getTitle()+","+pos.getDescription()+","+pos.getStatus()+"\n").getBytes());
                         fop.close();
                          outputStream.writeObject(new String("post is delivered successfuly"));
                                        outputStream.flush();
                         
                              
                           
                                
                      
                    }
                        
             
                }
                ///age like 
               // System.out.println(inputStream.readObject());
                
            } catch (IOException ex) {
                System.out.println(ex+"conioex");
               
            } catch (ClassNotFoundException ex) {
             System.out.println(ex+"concnotfo");
            }
        }
    }
}
