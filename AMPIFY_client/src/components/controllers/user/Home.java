package components.controllers.user;

import commonPackages.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import socket.Client;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    private Client client;
    private User user;
    public Button createGroupBtn;

    public void createGroup(ActionEvent actionEvent) throws Exception {

        
    }

    @FXML
    private Label name;
    @FXML
    private ImageView pic;
    public Home(){
        System.out.println("Home constructor is called.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(user.getName());
        Image image = new Image("http://localhost:8080/profile_pics/default.jpg",true);
        pic.setImage(image);
    }

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
