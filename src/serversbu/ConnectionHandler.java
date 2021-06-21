package serversbu;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import Model.Post;
import Model.PrivacyStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


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
                                fin.close();
                               String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  
                                  if(a[0].equals(cmd[1])){
                                       String usnn=cmd[1];
                                       FileInputStream finn = new FileInputStream(usnn+"_follower.dat");
                                         System.out.println("folo12==="+finn.available());
                                        byte[] byt1 = new byte[finn.available()];
                                        finn.read(byt1);
                                        String followers = new String(byt1);
                                        finn.close();
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
                                        System.out.println("fpppp==="+followi.available());
                                        byte[] byt2 = new byte[followi.available()];
                                        followi.read(byt2);
                                        String following = new String(byt2);
                                        followi.close();
                                        String[] asw2;
                                        if(following.trim().length()>0){
                                        asw2 = following.split("\n");
                                        }
                                        else{
                                           asw2=new String[0];
                                        }
                                        
                                        System.out.println("fpppp===9999999");
                                        outputStream.writeObject(new String(a[1]+","+a[2]+","+a[4]+","+a[5]+","+asw1.length+","+asw2.length+","+a[3]));
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
                             
                               String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  users+=a[0]+",";   
                                }
                             
                                outputStream.writeObject(new String(users));
                                outputStream.flush();

                        
                    }
                    
                    else if(cmd[0].equalsIgnoreCase("[New_Post]")){
                        
                        String pst = cmd[1];
                        String pme = cmd[2];
                      
                       String usnn=cmd[3];
                       Post pos = new Post();
                       pos.setTitle(pst);
                       pos.setDescription(pme);
                       pos.setWriter(usnn);
                       pos.setStatus(PrivacyStatus.PUBLIC);
                       FileOutputStream fop = new FileOutputStream("post.dat",true);
                        
                         fop.write((pos.getWriter()+","+pos.getTitle()+","+pos.getDescription()+","+pos.getStatus()+","+(new Date()).toString()+"\n").getBytes());
                         fop.close();
                          outputStream.writeObject(new String("post is delivered successfuly"));
                                        outputStream.flush();
                         
                              
                           
                                
                      
                    }
                     else if(cmd[0].equalsIgnoreCase("choose_follower")){
                           String follower_usn = cmd[1];
                           String main_usn = cmd[2];
                            FileInputStream fi = new FileInputStream(main_usn+"_following.dat");  
                                        byte[] byt1 = new byte[fi.available()];
                                        fi.read(byt1);
                                        String followers = new String(byt1);
                                        fi.close();
                                        if(followers.indexOf(follower_usn)<0){
                                        
                                            FileOutputStream finn = new FileOutputStream(main_usn+"_following.dat",true);
                                         
                                            finn.write((follower_usn+"\n").getBytes());
                                            finn.close();

                                             FileOutputStream finn1 = new FileOutputStream(follower_usn+"_follower.dat",true);
                                 
                                            finn1.write((main_usn+"\n").getBytes());
                                            finn1.close();
                                             outputStream.writeObject("follower added succssesfully");
                                             outputStream.flush();
                            
                                        }
                                        else{
                                             outputStream.writeObject("you already have this user as follower");
                                             outputStream.flush();
                                        }
                           
                     }
                   else if(cmd[0].equalsIgnoreCase("select_following_list")){
                        String main_usn = cmd[1];
                            FileInputStream fi = new FileInputStream(main_usn+"_following.dat");  
                                        byte[] byt1 = new byte[fi.available()];
                                        fi.read(byt1);
                                        String following_list = new String(byt1);
                                        following_list.trim();
                                        fi.close();//following haro khondim
                                        FileInputStream fip = new FileInputStream("post.dat");
                                        byte[] byt2 = new byte[fip.available()];
                                        fip.read(byt2);//post haro khondim
                                        String posts = new String(byt2);
                                        fip.close();
                                        ArrayList<Post> post_list = new ArrayList<Post>();
                                        String[] post_line=posts.split("\n");
                                        for (int i = 0; i < post_line.length; i++) {
                                          String current_post = post_line[i];
                                          String[] current_post_details = current_post.split(",");
                                          if(current_post_details[0].equals(main_usn)){
                                              Post pos = new Post();
                                              pos.setWriter(current_post_details[0]);
                                              pos.setTitle(current_post_details[1]);
                                              pos.setPost_delivered_time(new Date(current_post_details[4]));
                                              pos.setDescription(current_post_details[2]);
                                              if(current_post_details[3].equals(PrivacyStatus.PUBLIC.toString())){
                                                  pos.setStatus(PrivacyStatus.PUBLIC);
                                              }
                                              else{
                                                  pos.setStatus(PrivacyStatus.PRIVATE);
                                                  
                                              }
                                              post_list.add(pos);
                                          }
                                          
                                          else if(following_list.indexOf(current_post_details[0])>=0){
                                              
                                                Post following_post = new Post();
                                                following_post.setWriter(current_post_details[0]);
                                                following_post.setTitle(current_post_details[1]);
                                                following_post.setPost_delivered_time(new Date(current_post_details[4]));
                                                following_post.setDescription(current_post_details[2]);
                                                if(current_post_details[3].equals(PrivacyStatus.PUBLIC.toString())){
                                                    following_post.setStatus(PrivacyStatus.PUBLIC);
                                                }
                                                else{
                                                    following_post.setStatus(PrivacyStatus.PRIVATE);

                                                }
                                                post_list.add(following_post);
              
                                              //code folling post ezaf
                                          }
                                        }
                                        System.out.println(post_list.toString());
                                        Object[] post_array=post_list.toArray();
                                        System.out.println(post_array.length);
                                        for (int i = 0; i < post_array.length; i++) {
                                            for (int j = 0; j < post_array.length-1; j++) {
                                                
                                                Post p1 = (Post)post_array[j];
                                                Post p2 = (Post)post_array[j+1];
                                                if(p1.getPost_delivered_time().before(p2.getPost_delivered_time())){
                                                    Object temp = post_array[j];
                                                    post_array[j] =post_array[j+1];
                                                    post_array[j+1]=temp;
                                                }
                                                
                                            }
                                        }
                                        Post[] xL = new Post[post_array.length];
                                     
                                        outputStream.writeInt(xL.length);
                                        outputStream.flush();
                                           for (int i = 0; i <xL.length; i++) {
                                              xL[i]=(Post)post_array[i];
                                               outputStream.writeObject(xL[i]);
                                               outputStream.flush();
                                        }
                                        System.out.println("////////");
                                       
                                        System.out.println("++++++");
                                        
                            
                   }
                      else if(cmd[0].equalsIgnoreCase("[update_user_inform]")){
                          File registred_user = new File(file_name);
                            if(registred_user.exists()){
                                FileInputStream fin;
                              try {
                                  fin = new FileInputStream(registred_user);
                                  byte[] byt = new byte[fin.available()];
                                 fin.read(byt);
                                 String file_content = new String(byt);//check for unique
                                 fin.close();
                                 int y=file_content.indexOf(cmd[1]);
                                 int xa = file_content.indexOf("\n",y);
                                 String before =file_content.substring(0,y);
                                 String after = file_content.substring(xa+1);
                                 file_content=before+after;
                                 file_content+=cmd[1]+","+cmd[2]+","+cmd[3]+","+cmd[4]+","+cmd[5]+","+cmd[6]+"\n";
                                 FileOutputStream fot = new FileOutputStream(file_name, true);
                                  System.out.println(file_content);
                                fot.write(file_content.getBytes());
                                fot.close();
                                  outputStream.writeBytes("change user info successfully done");
                                  outputStream.flush();
                                  //agar yeki bod suucc


                                
//                               String[] j=   file_content.split("\n");
//                                  for (int i = 0; i < j.length; i++) {
//                                     String[] comma=  j[i].split(",");
//                                     
//                                      
//                                }
                                
                                 
                                 
                              } catch (Exception ex) {
                                  System.out.println(ex+"00");
                              }
                      }
                      }
                   
                        
             
                }
                ///age like 
               // System.out.println(inputStream.readObject());
                
            } catch (IOException ex) {
                System.out.println(ex+"conioex");
               
            } catch (ClassNotFoundException ex) {
             System.out.println(ex+"concnotfo");
            }
            catch (Exception ex) {
                System.out.println(ex+"*****x");
               
            }
        }
    }
}
