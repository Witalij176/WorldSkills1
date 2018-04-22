import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.*;

public class Main extends Application {
    int id;

    Statement statement;

    byte login() throws IOException{
        Stage stage=new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Login/Login.fxml"));
        Parent root = fxmlLoader.load();
        Login.Login login = fxmlLoader.getController();
        stage.setTitle("Вход в систему");
        stage.setScene(new Scene(root, 600, 500));
        login.init(statement);
        stage.setResizable(false);
        stage.showAndWait();
        id=login.id;
        return login.status;
    }

    void registration() throws IOException{
        Stage stage=new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Registration/Registration.fxml"));
        Parent root=fxmlLoader.load();
        Registration.Registration registration=fxmlLoader.getController();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(root, 609, 559));
        registration.init(statement, id);
        stage.showAndWait();
    }

    void cabinet() throws IOException{
        Stage stage=new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Cabinet/Cabinet.fxml"));
        Parent root=fxmlLoader.load();
        Cabinet.Cabinet cabinet=fxmlLoader.getController();
        stage.setTitle("Добро пожаловать!");
        stage.setScene(new Scene(root, 609, 473));
        cabinet.init(statement, id);
        stage.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Connect connect=new Connect();
            statement=connect.statement;
            loop:while(true)
                switch (login()){
                    case 1: registration(); continue;
                    case -1: break loop;
                    default: cabinet();
                }
            connect.disconnect();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Не могу соединится с сервером!").showAndWait();
            return;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}