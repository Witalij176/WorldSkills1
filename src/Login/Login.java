package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;
import java.sql.*;

public class Login{
    Stage stage;
    Statement statement;

    @FXML TextField login;
    @FXML PasswordField passwd;

    public byte status=0;
    public int id;

    public void clickRegButton(ActionEvent actionEvent) {
        status=1;
        stage.close();
    }

    public void clickConnectButton(ActionEvent actionEvent) {
        if(login.getText().equals("")||login.getText().equals("")){
            new Alert(Alert.AlertType.ERROR, "Строки не могут быть пустыми!").showAndWait();
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery(String.format("select id, login, password from users where login='%s' and password='%s';", login.getText(), passwd.getText()));
            resultSet.next();
            id=resultSet.getInt("id");
            if(!login.getText().equals(resultSet.getString("login")) || !passwd.getText().equals(resultSet.getString("password")))
                throw new SQLException();
            stage.close();
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "Логин или пароль не верны").showAndWait();
        }
    }

    public void init(Statement statement){
        stage=(Stage)login.getScene().getWindow();
        this.statement=statement;
        stage.setOnCloseRequest(we->{status=-1; stage.close();});
    }
}