package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Post implements Serializable{

   

    private String writer;
    private String title;
    private String description;
    private PrivacyStatus status;
    private Date post_delivered_time;
    private int number_of_like;
    private int number_of_repost;
    
   
    public int getNumber_of_like() {
        return number_of_like;
    }

   
    public void setNumber_of_like(int number_of_like) {
        this.number_of_like = number_of_like;
    }

    
    public int getNumber_of_repost() {
        return number_of_repost;
    }

    public void setNumber_of_repost(int number_of_repost) {
        this.number_of_repost = number_of_repost;
    }
    
    public Date getPost_delivered_time() {
        return post_delivered_time;
    }

  
    public void setPost_delivered_time(Date post_delivered_time) {
        this.post_delivered_time = post_delivered_time;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PrivacyStatus getStatus() {
        return status;
    }

    public void setStatus(PrivacyStatus status) {
        this.status = status;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(title, post.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return writer+","+title;
    }
}
