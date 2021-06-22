package Controller;

import Model.Post;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PostItem extends ListCell<Post> {
    public String usn;
    public PostItem(String s){
        usn=s;
    }

    @Override
    public void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new PostItemController(post).init(usn));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
