package serversbu;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import Model.Post;
import Model.PrivacyStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
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
             
                      CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
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
                                        user_list.add(asw[i]);
                                        outputStream.writeObject(new String("User is already available \n"));
                                        outputStream.flush();
                                        userexist = true;
                                        break;
                                    }
                                }
                       
                            }
                            if(user_list.size()==0){
                                
                                FileOutputStream fot = new FileOutputStream(file_name, true);
                                String wa = cmd[1];
                                cu = cu+"\n";
                                fot.write(cu.getBytes());
                                fot.close();
                          
                               FileOutputStream fot1 = new FileOutputStream(wa+"_follower.dat", true);
                                //out.println("serve");
                           
                                fot1.close();
                            
                                 FileOutputStream fot2 = new FileOutputStream(wa+"_following.dat", true);
                         
                                
                                fot2.close();
                                FileOutputStream user_posts = new FileOutputStream(wa+"_posts.dat",true);
                                user_posts.close();
                                 FileOutputStream fotm = new FileOutputStream(wa+"_mute.dat", true);
                                //out.println("serve");
                           
                                fotm.close();
                                //out.println("se11rve");
                                String question = cmd[7];
                                String answer = cmd[8];
                                String fiile_name = cmd[1]+"_question.dat";
                                FileOutputStream user_question = new FileOutputStream(fiile_name);
                                user_question.write((question+","+answer).getBytes());
                                user_question.close();
                                outputStream.writeObject(new String("registred,"+cmd[6]));
                                outputStream.flush();
                                System.out.println(cmd[1]+" register "+cmd[6]);
                                System.out.println("time: "+new Date().toString());
                                userexist=false;///****
                            }
                            else{
                                userexist=false;
                            }
                        // save to file beshe
                    }
                     else if(cmd[0].equalsIgnoreCase("check_usn")){//password 
                        CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                        FileInputStream fin = new FileInputStream(file_name);
                        byte[] byt = new byte[fin.available()];
                        fin.read(byt);
                        String file_content = new String(byt);
                        String[] asw = file_content.split("\n");
                        for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  
                                  if(a[0].equals(cmd[1])){
                                      user_list.add(a[0]);
                                       userexist = true;
                                       break;
                                  }
                        }
                 
                        if(user_list.size()==0){
                                      outputStream.writeObject(new String("usernme is incorrect\n"));
                                      outputStream.flush();
                                      userexist=false;
                           }
                           else{
                               
                                userexist=false;
                                FileInputStream fiqn = new FileInputStream(user_list.get(0)+"_question.dat");
                                byte[] bytqs = new byte[fiqn.available()];
                                fiqn.read(bytqs);
                                String file_contentqs = new String(bytqs);
                                outputStream.writeObject(file_contentqs);
                                outputStream.flush();
                                
                            }
                          

                     }
                      else if(cmd[0].equalsIgnoreCase("get_password")){
                          CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                          String usn=cmd[1];
                          FileInputStream fin = new FileInputStream(file_name);
                        byte[] byt = new byte[fin.available()];
                        fin.read(byt);
                        String file_content = new String(byt);
                        String[] asw = file_content.split("\n");
                        String oldpass="";
                        for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                 
                                  if(a[0].equals(cmd[1])){
                                       user_list.add(a[0]);
                                  user_list.add(a[3]);
                                      userexist = true;
                                        oldpass=a[3];
                                        break;
                                  }
                        }
                    
                        if(user_list.size()==0){
                                      outputStream.writeObject(new String("usernme is incorrect\n"));
                                      outputStream.flush();
                                      userexist=false;
                        }
                        else{
                               
                                userexist=false;
                                   outputStream.writeObject(user_list.get(1));
                                      outputStream.flush();
                             }
                          
                      }
                    else if(cmd[0].equalsIgnoreCase("[Login_user]")){
                            CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                               FileInputStream fin = new FileInputStream(file_name);
                                byte[] byt = new byte[fin.available()];
                                fin.read(byt);
                                String file_content = new String(byt);
                               String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                     String[] a= asw[i].split(",");
                                if(a[0].equals(cmd[1])){
                                    user_list.add(a[0]);    
                                    user_list.add(a[3]);  }
                                }
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  if(user_list.get(0).equals(cmd[1])){
                                      if (user_list.get(1).equals(cmd[2])) {
                                          outputStream.writeObject(new String("Succeed,"+a[5]));
                                          
                                          outputStream.flush();
                                          userexist = true;
                                          System.out.println("action: "+" connect,Login");
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
                         CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                            
                                FileInputStream fin = new FileInputStream(file_name);
                                byte[] byt = new byte[fin.available()];
                                fin.read(byt);
                                String file_content = new String(byt);
                                fin.close();
                               String[] asw = file_content.split("\n");
                                for (int i = 0; i < asw.length; i++) {
                                  String[] a= asw[i].split(",");
                                  
                                  if(a[0].equals(cmd[1])){
                                      user_list.add(a[0]);
                                       String usnn=cmd[1];
                                       FileInputStream finn = new FileInputStream(user_list.get(0)+"_follower.dat");
                                         //out.println("folo12==="+finn.available());
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
                                        
                                        FileInputStream followi = new FileInputStream(user_list.get(0)+"_following.dat");
//                                       FileInputStream followi = new FileInputStream(usnn+vfo);
                                        //out.println("fpppp==="+followi.available());
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
                                        
                                        //out.println("fpppp===9999999");
                                        if(user_list.size()>0){
                                            outputStream.writeObject(new String(a[1]+","+a[2]+","+a[4]+","+a[5]+","+asw1.length+","+asw2.length+","+a[3]));
                                            outputStream.flush();
                                            userexist = true;
                                            break;
                                        }
                                  }
                                  
                                }
                                
                                if(userexist==true){
                                       userexist=false;
                                      
                                }
                              
                            }
                            else if(cmd[0].equalsIgnoreCase("[get_user_List]")){
                                CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                                String users = "";
                                FileInputStream fin = new FileInputStream(file_name);
                                        byte[] byt = new byte[fin.available()];
                                        fin.read(byt);
                                        String file_content = new String(byt);

                                       String[] asw = file_content.split("\n");
                                        for (int i = 0; i < asw.length; i++) {
                                          String[] a= asw[i].split(",");
                                          users+=a[0]+","; 
                                          user_list.add(a[0]);
                                        }
                                        

                                        outputStream.writeObject(new String(users));
                                        outputStream.flush();

                        
                    }
                    else if(cmd[0].equalsIgnoreCase("select_random_Q")){
                         CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                         FileInputStream fin = new FileInputStream("questions.dat");
                         byte[] byt = new byte[fin.available()];
                         fin.read(byt);
                         String file_content = new String(byt);
                         fin.close();
                         String[] asw = file_content.split("\n");
                         int max = asw.length;
                         int question_numb = (int)Math.floor(max*Math.random());
                         
                         user_list.add(asw[question_numb]);
                         outputStream.writeObject(user_list.get(0));
                         outputStream.flush();
                         
                        
                    }
                 
                    else if(cmd[0].equalsIgnoreCase("[New_Post]")){
                         
                 
                              
                           
                                
                     
                          CopyOnWriteArrayList<Post> post_list = new CopyOnWriteArrayList<Post>();
                        String pst = cmd[1];
                        String pme = cmd[2];
                      
                       String usnn=cmd[3];
                       Post pos = new Post();
                       pos.setTitle(pst);
                       pos.setDescription(pme);
                       pos.setWriter(usnn);
                       pos.setStatus(PrivacyStatus.PUBLIC);
                       pos.setNumber_of_like(0);
                       pos.setNumber_of_repost(0);
                       post_list.add(pos);
                                
                       FileOutputStream fop = new FileOutputStream(usnn+"_posts.dat",true);
                         Post p =post_list.get(0);
                         fop.write((p.getWriter()+","+p.getTitle()+","+p.getDescription()+","+p.getStatus()+","+(new Date()).toString()+","+p.getNumber_of_like()+","+p.getNumber_of_repost()+"\n").getBytes());
                         fop.close();
                   
                             FileOutputStream fop1 = new FileOutputStream(p.getWriter()+"_"+p.getTitle()+"_comments.dat");
                             fop1.close();
                          outputStream.writeObject(new String("post is delivered successfuly"));
                          outputStream.flush();
                                                
                      
                    }
                     else if(cmd[0].equalsIgnoreCase("choose_follower")){
                            CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                       
                           user_list.add(cmd[1]);
                           user_list.add(cmd[2]);
                            FileInputStream fi = new FileInputStream(user_list.get(1)+"_following.dat");  
                                        byte[] byt1 = new byte[fi.available()];
                                        fi.read(byt1);
                                        String followers = new String(byt1);
                                        fi.close();
                                        if(followers.indexOf(user_list.get(0))<0){
                                        
                                            FileOutputStream finn = new FileOutputStream(user_list.get(1)+"_following.dat",true);
                                         
                                            finn.write((user_list.get(0)+"\n").getBytes());
                                            finn.close();

                                             FileOutputStream finn1 = new FileOutputStream(user_list.get(0)+"_follower.dat",true);
                                 
                                            finn1.write((user_list.get(1)+"\n").getBytes());
                                            finn1.close();
                                             outputStream.writeObject("follower added succssesfully");
                                             outputStream.flush();
                            
                                        }
                                        else{
                                                FileInputStream fi5 = new FileInputStream(user_list.get(1)+"_following.dat");  
                                                byte[] byt5 = new byte[fi5.available()];
                                                fi5.read(byt5);
                                                String followers5 = new String(byt5);//list follower
                                                fi5.close();
                                                int IDX = followers5.indexOf(user_list.get(0));
                                                int Idx1 = followers5.indexOf("\n",IDX);
                                                String before = followers5.substring(0,IDX);
                                                String after = followers5.substring(Idx1+1);
                                                 FileOutputStream finn1 = new FileOutputStream(user_list.get(1)+"_following.dat");
                                                 finn1.write((before+after).getBytes());
                                                 finn1.close();
                                                 
                                                  FileInputStream fi6 = new FileInputStream(user_list.get(0)+"_follower.dat");  
                                                byte[] byt6 = new byte[fi6.available()];
                                                fi6.read(byt6);
                                                String followers6 = new String(byt6);
                                                fi6.close();
                                                int IDX6 = followers6.indexOf(user_list.get(1));
                                                int Idx16 = followers6.indexOf("\n",IDX6);
                                                String before6 = followers6.substring(0,IDX6);
                                                String after6 = followers6.substring(Idx16+1);
                                                 FileOutputStream finn16 = new FileOutputStream(user_list.get(0)+"_follower.dat");
                                                 finn16.write((before6+after6).getBytes());
                                                 finn16.close();
                                            
                                             outputStream.writeObject("you unfollow "+user_list.get(0));
                                             outputStream.flush();
                                            System.out.println("action: follow, unfollow");
                                            System.out.println("["+cmd[1]+"]"+" action");
                                            System.out.println("message: "+"["+cmd[2]+"]");  
                                            System.out.println("time: "+new Date().toString());
                                        }
                           
                     }
                   else if(cmd[0].equalsIgnoreCase("select_following_list")){
                        
                        CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                      //  String main_usn = cmd[1];
                      user_list.add(cmd[1]);
                      FileInputStream fi = new FileInputStream(user_list.get(0)+"_following.dat");  
                        byte[] byt1 = new byte[fi.available()];
                        fi.read(byt1);
                        String following_list = new String(byt1);
                        following_list.trim();
                        fi.close();//following haro khondim
                        
                        FileInputStream fip = new FileInputStream(user_list.get(0)+"_posts.dat");
                        byte[] byt2 = new byte[fip.available()];
                        fip.read(byt2);//post ha khodesh ro khondim
                        String posts = new String(byt2);
                        fip.close();
                         //out.println("serbbbbbbbbb33333");
                       // ArrayList<Post> post_list = new ArrayList<Post>();
                        CopyOnWriteArrayList<Post> post_list = new CopyOnWriteArrayList<Post>();
                        String[] post_line=posts.split("\n");
                        //out.println("serbbbbbbbbb32223333***"+post_line.length);
                        if(post_line.length>0){
                            for (int i = 0; i < post_line.length; i++) {
                                String current_post = post_line[i];
                                if(current_post.trim().length()>0){
                                          String[] current_post_details = current_post.split(",");
                                          Post pos = new Post();
                                          pos.setWriter(current_post_details[0]);
                                          pos.setTitle(current_post_details[1]);
                                          pos.setPost_delivered_time(new Date(current_post_details[4]));
                                          pos.setDescription(current_post_details[2]);
                                          pos.setNumber_of_like(Integer.parseInt(current_post_details[5]));
                                          pos.setNumber_of_repost(Integer.parseInt(current_post_details[6]));
                                          if(current_post_details[3].equals(PrivacyStatus.PUBLIC.toString())){
                                                  pos.setStatus(PrivacyStatus.PUBLIC);
                                          }
                                          else{
                                                  pos.setStatus(PrivacyStatus.PRIVATE);
                                                  
                                          }
                                          post_list.add(pos);
                                }
                            }//tah for
                        }
                        //out.println("serversbu000000000000000000");
                        String[] following_file=following_list.split("\n");
                        if(following_file.length>0){
                          for (int i = 0; i < following_file.length; i++) {
                            String following_name = following_file[i];
                            if(following_name.trim().length()>0){
                             //out.println(following_name);
                            FileInputStream fip3 = new FileInputStream(following_name+"_posts.dat");
                            
                            byte[] byt3 = new byte[fip3.available()];
                            fip3.read(byt3);//post ha khodesh ro khondim
                            String posts3 = new String(byt3);
                            fip3.close();
                           //out.println(following_name);
                            String[] post3_line=posts3.split("\n");
                            if(post3_line.length>0){
                               for ( int j = 0; j < post3_line.length; j++) {
                                    String current_post = post3_line[j];
                                    if(current_post.trim().length()>0){
                                          String[] current_post_details = current_post.split(",");
                                          Post pos = new Post();
                                          pos.setWriter(current_post_details[0]);
                                          pos.setTitle(current_post_details[1]);
                                          pos.setPost_delivered_time(new Date(current_post_details[4]));
                                          pos.setDescription(current_post_details[2]);
                                            pos.setNumber_of_like(Integer.parseInt(current_post_details[5]));
                                          pos.setNumber_of_repost(Integer.parseInt(current_post_details[6]));
                                          if(current_post_details[3].equals(PrivacyStatus.PUBLIC.toString())){
                                                  pos.setStatus(PrivacyStatus.PUBLIC);
                                          }
                                          else{
                                                  pos.setStatus(PrivacyStatus.PRIVATE);
                                                  
                                          }
                                          post_list.add(pos);
                                    }
                                } //tah for 
                            }
                          }
                          }
                        }
                  //      System.out.println(post_list.toString());
                        Object[] post_array=post_list.toArray();
                        //out.println(post_array.length);
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
                        //out.println(xL);
                        outputStream.writeInt(xL.length);
                        outputStream.flush();
                        for (int i = 0; i <xL.length; i++) {
                            xL[i]=(Post)post_array[i];
                            outputStream.writeObject(xL[i]);
                            outputStream.flush();
                        }
                 System.out.println(cmd[1]+" get posts list");
                        System.out.println("time: "+new Date().toString());
//                                        
                            
                    }
                    else if(cmd[0].equalsIgnoreCase("[update_user_inform]")){
                          CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                          user_list.add(cmd[1]);
                          user_list.add(cmd[2]);
                          user_list.add(cmd[3]);
                          user_list.add(cmd[4]);
                          user_list.add(cmd[5]);
                          user_list.add(cmd[6]);
                         
                          File registred_user = new File(file_name);
                            if(registred_user.exists()){
                                FileInputStream fin;
                              try {
                                  fin = new FileInputStream(registred_user);
                                  byte[] byt = new byte[fin.available()];
                                 fin.read(byt);
                                 String file_content = new String(byt);//check for unique
                                 fin.close();
                                 int y=file_content.indexOf(user_list.get(0));
                                 int xa = file_content.indexOf("\n",y);
                                 String before =file_content.substring(0,y);
                                 String after = file_content.substring(xa+1);
                                 file_content=before+after;
                                 file_content+=user_list.get(0)+","+user_list.get(1)+","+user_list.get(2)+","+user_list.get(3)+","+user_list.get(4)+","+user_list.get(5)+"\n";
                                 FileOutputStream fot = new FileOutputStream(file_name);
                                 // System.out.println(file_content);
                                fot.write(file_content.getBytes());
                                fot.close();
                                  outputStream.writeObject("change user info successfully done");
                                  outputStream.flush();
                                  //agar yeki bod suucc
                                 
                              } catch (Exception ex) {
                                  System.out.println(ex);
                              }
                            }
                    }
                    else if(cmd[0].equalsIgnoreCase("increase_like")){
                        CopyOnWriteArrayList<String> like_list = new CopyOnWriteArrayList<String>();
                        like_list.add(cmd[1]);
                       // String post_title = cmd[2];
                        like_list.add(cmd[2]);
                        FileInputStream fip = new FileInputStream(like_list.get(0)+"_posts.dat");
                        byte[] byt2 = new byte[fip.available()];
                        fip.read(byt2);//post haro khondim
                        String posts = new String(byt2);
                        fip.close();
                        int x= posts.indexOf(like_list.get(0)+","+like_list.get(1));
                        int y =posts.indexOf("\n",x);
                        String befor =posts.substring(0, x);
                        String after = posts.substring(y+1);
                        String current_post=posts.substring(x,y);
                        String[] current_post_items = current_post.split(",");
                        int Like = Integer.parseInt(current_post_items[5]);
                        Like++;
                        current_post_items[5]=Like+"";
                        int Repost = Integer.parseInt(current_post_items[6]);
                        current_post_items[6]=Repost+"";
                        String line="";
                        for (int i = 0; i < current_post_items.length; i++) {
                             if(i<current_post_items.length-1){
                                line+=current_post_items[i]+",";
                             }
                             else{
                                  line+=current_post_items[i]+"\n";
                             }
                            
                        }
                        String edited_file = befor+line+after;
                        FileOutputStream finn = new FileOutputStream(like_list.get(0)+"_posts.dat");
                           
                         finn.write(edited_file.getBytes());
                         finn.close(); 
                          outputStream.writeInt(Like);
                         outputStream.flush();
                          outputStream.writeInt(Repost);
                         outputStream.flush();
                        
                    }  
                    else if(cmd[0].equalsIgnoreCase("[add_comments]")){
                         CopyOnWriteArrayList<String> comment_list = new CopyOnWriteArrayList<String>();
             
                        comment_list.add(cmd[1]);
                      
                          comment_list.add(cmd[2]);
                  
                         comment_list.add(cmd[3]);
                      
                        comment_list.add(cmd[4]);
                         if(comment_list.get(3).trim().length()>0){
                        FileOutputStream finn = new FileOutputStream(comment_list.get(1)+"_"+comment_list.get(2)+"_comments.dat",true);
                        
                        String file_content = comment_list.get(0)+","+comment_list.get(3)+"\n";
                        
                           
                         finn.write(file_content.getBytes());
                         finn.close();             
                         outputStream.writeObject("your comment saved successfully");
                         outputStream.flush();
                         }
                         else{
                             outputStream.writeObject("your comment is null");
                         outputStream.flush();
                         }

                        
                    }
                    else if(cmd[0].equalsIgnoreCase("repost")){
                         CopyOnWriteArrayList<String> repost_list = new CopyOnWriteArrayList<String>();
                         repost_list.add(cmd[1]);
                         repost_list.add(cmd[2]);
                         repost_list.add(cmd[3]);
//                  
                        FileInputStream fip = new FileInputStream(repost_list.get(0)+"_posts.dat");
                        byte[] byt2 = new byte[fip.available()];
                        fip.read(byt2);
                        String posts = new String(byt2);
                        fip.close();
                        int x= posts.indexOf(repost_list.get(0)+","+repost_list.get(1));
                        int y =posts.indexOf("\n",x);
                        String befor =posts.substring(0, x);
                        String after = posts.substring(y+1);
                        String current_post=posts.substring(x,y);
                        String[] current_post_items = current_post.split(",");
                        int Repost = Integer.parseInt(current_post_items[6]);
                        Repost++;
                        current_post_items[6]=Repost+"";
                        int Like = Integer.parseInt(current_post_items[5]);
                       current_post_items[5]=Like+"";
                       
                        String line="";
                        for (int i = 0; i < current_post_items.length; i++) {
                             if(i<current_post_items.length-1){
                                line+=current_post_items[i]+",";
                             }
                             else{
                                  line+=current_post_items[i]+"\n";
                             }
                            
                        }
                        String edited_file = befor+line+after;
                        FileOutputStream finn = new FileOutputStream(repost_list.get(0)+"_posts.dat");
                           
                         finn.write(edited_file.getBytes());
                         finn.close();  
                       FileOutputStream finn1 = new FileOutputStream(repost_list.get(2)+"_posts.dat",true);
                       finn1.write(line.getBytes());
                       finn1.close();
                       
                          outputStream.writeInt(Like);
                         outputStream.flush();
                         outputStream.writeInt(Repost);
                         outputStream.flush();
                        
                    } 
                    else if(cmd[0].equalsIgnoreCase("view_comments")){
                          CopyOnWriteArrayList<String> vcomment_list = new CopyOnWriteArrayList<String>();
                        // repost_list.add(cmd[1]);
                        
                        vcomment_list.add(cmd[1]);
                        vcomment_list.add(cmd[2]);
                        String post_t = cmd[2];
                        
                        
                        FileInputStream fip = new FileInputStream(vcomment_list.get(0)+"_"+vcomment_list.get(1)+"_comments.dat");
                        byte[] byt2 = new byte[fip.available()];
                        fip.read(byt2);
                        String comments = new String(byt2);
                        fip.close();
                     
                          outputStream.writeObject(comments);
                         outputStream.flush();
                        
                        
                    }
                      else if(cmd[0].equalsIgnoreCase("delete_account")){
                          CopyOnWriteArrayList<String> deleteac_list = new CopyOnWriteArrayList<String>();
                          
                        deleteac_list.add(cmd[1]);
                        deleteac_list.add("UERS_F.dat");
                        File f = new File(deleteac_list.get(0)+"_posts.dat");
                        f.delete();
                        f = new File(deleteac_list.get(0)+"_following.dat");
                        f.delete();
                        f = new File(deleteac_list.get(0)+"_follower.dat");
                        f.delete();
                          f = new File(deleteac_list.get(0)+"_question.dat");
                        f.delete();
                        File g = new File("D:\\java\\ServerSBU");
                        File[] glist= g.listFiles();
                        for (int i = 0; i < glist.length; i++) {
                             File file = glist[i];
                             if(file.getName().startsWith(deleteac_list.get(0)) && file.getName().endsWith("_comments.dat")){
                                 file.delete();
                             }
                            // System.out.println(file.getName());
                        }
                        FileInputStream fip = new FileInputStream(deleteac_list.get(1));
                        byte[] byt2 = new byte[fip.available()];
                        fip.read(byt2);
                        String users= new String(byt2);
                        fip.close();
                        int IDX = users.indexOf(deleteac_list.get(0));
                        int Idx1 = users.indexOf("\n",IDX);
                        String before = users.substring(0,IDX);
                        String after = users.substring(Idx1+1);
                        FileOutputStream finn1 = new FileOutputStream(deleteac_list.get(1));
                        finn1.write((before+after).getBytes());
                        finn1.close();
                        String Other_users=before+after;
                        
                        String[] asw = Other_users.split("\n");
                    
                        for (int i = 0; i < asw.length; i++) {
                            
                            String[] a= asw[i].split(",");
                            String user=a[0];   
                            FileInputStream ufg = new FileInputStream(user+"_following.dat");
                            byte[] bytu = new byte[ufg.available()];
                            ufg.read(bytu);
                            String file_content_ufg= new String(bytu);
                            ufg.close();
                            if(file_content_ufg.contains(deleteac_list.get(0))){
                                int idx1 = file_content_ufg.indexOf(deleteac_list.get(0));
                                int idx2 = file_content_ufg.indexOf("\n",idx1);
                                String beforeg = file_content_ufg.substring(0,idx1);
                                String afterg = file_content_ufg.substring(idx2+1);
                                FileOutputStream fing = new FileOutputStream(user+"_following.dat");
                                fing.write((beforeg+afterg).getBytes());
                                fing.close();
                            }
                            ufg = new FileInputStream(user+"_follower.dat");
                            bytu = new byte[ufg.available()];
                            ufg.read(bytu);
                            file_content_ufg= new String(bytu);
                            ufg.close();
                            if(file_content_ufg.contains(deleteac_list.get(0))){
                                int idx1 = file_content_ufg.indexOf(deleteac_list.get(0));
                                int idx2 = file_content_ufg.indexOf("\n",idx1);
                                String beforeg = file_content_ufg.substring(0,idx1);
                                String afterg = file_content_ufg.substring(idx2+1);
                                FileOutputStream fing = new FileOutputStream(user+"_follower.dat");
                                fing.write((beforeg+afterg).getBytes());
                                fing.close();
                            }
                        }
                         outputStream.writeObject("your account deleted");
                         outputStream.flush();
                        
                                
                        
                        
                        
                      }
                       else if(cmd[0].equalsIgnoreCase("Mute")){
                           System.out.println("Co6ww");  
                           boolean  ffm =false;
                        CopyOnWriteArrayList<String> user_list = new CopyOnWriteArrayList<String>();
                         user_list.add(cmd[2]);//alimainusn
                        user_list.add(cmd[1]);
                         FileInputStream finz = new FileInputStream(user_list.get(0)+"_following.dat");//mainuser
                        byte[] bytz = new byte[finz.available()];
                        finz.read(bytz);//following taraf maain user ro khonde
                        
                        String fie_contentz = new String(bytz);//followinga to inan
                        finz.close();
                    if(fie_contentz.indexOf(user_list.get(1))>0){//age adame ke mute mihaym konim to list following hamon hast
                        FileInputStream fin = new FileInputStream(user_list.get(0)+"_mute.dat");//file mute hamon ro mikhonim
                        byte[] byt = new byte[fin.available()];
                        fin.read(byt);
                       
                        String fie_content = new String(byt);//list mute ha
                         fin.close();
                        if(fie_content.trim().length()>0){//age list muta>0 bod
                            String[] mutedl=fie_content.split("\n");
                             for (int i = 0; i <mutedl.length; i++) {
                                String current_muteduser = mutedl[i];
                                if(current_muteduser.equals(user_list.get(1))){//age taraf ke mikhaym mute konim to list muta bod

                                    int IDX = fie_content.indexOf(user_list.get(1));
                                                int Idx1 = fie_content.indexOf("\n",IDX);
                                                String before = fie_content.substring(0,IDX);
                                                String after =fie_content.substring(Idx1+1);
                                                 FileOutputStream finn1 = new FileOutputStream(user_list.get(0)+"_mute.dat");
                                                 finn1.write((before+after).getBytes());
                                                 finn1.close();
                                                 ffm=true;
                                                 break;
                                }
                             }
                             if(ffm==false){//age to list muta nabaod
                                   FileOutputStream fip = new FileOutputStream(user_list.get(0)+"_mute.dat");
                                    fip.write((user_list.get(1).toString()+"\n").getBytes());
                                    outputStream.writeObject( user_list.get(1)+" muted");
                                    outputStream.flush();
                             }
                             else{//age to list muta bod unmute mishe
                                 ffm=false;
                                  outputStream.writeObject( user_list.get(1)+" unmuted");
                                  outputStream.flush();
                             }
                        }
                        else{
                             FileOutputStream fip = new FileOutputStream(user_list.get(0)+"_mute.dat");
                             fip.write((user_list.get(1).toString()+"\n").getBytes());
                             outputStream.writeObject( user_list.get(1)+" muted");
                             outputStream.flush();
                        }
                        }
                     else{
                     }//payam else 
                       }
//                 
                }
      
                
            } catch (IOException ex) {
                try {
                    outputStream.writeObject(ex.getMessage());
                    outputStream.flush();
                } catch (IOException ex1) {
                }
               
            } catch (ClassNotFoundException ex) {
             System.out.println(ex+"concnotfo");
            }
            catch (Exception ex) {
                System.out.println(ex+"*****x");
               
            }
        }
    }
}
