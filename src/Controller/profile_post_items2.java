package Controller;

import Model.Post;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class profile_post_items2 extends ListCell<Post> {
    public String usn;
    public profile_post_items2(String s){
        usn=s;
    }

    @Override
    public void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new profile_post_items2Controller(post).init(usn));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
