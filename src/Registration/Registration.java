package Registration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Statement;

public class Registration {
    int id;
    Stage stage;
    Statement statement;

    @FXML TextField name;
    @FXML TextField login;
    @FXML TextField passwd1;
    @FXML TextField passwd2;
    @FXML TextField mail;
    @FXML TextField phone;

    public void clickExitButton(ActionEvent actionEvent) {
        stage.close();
    }

    public void clickSaveButton(ActionEvent actionEvent) {
        if(name.getText().equals("")||login.getText().equals("")||passwd1.getText().equals("")||passwd2.getText().equals("")){
            new Alert(Alert.AlertType.ERROR, "Поля имя, логин, и пароль не должны быть пустыми!").showAndWait();
            return;
        }
        if(!passwd1.getText().equals(passwd2.getText())){
            new Alert(Alert.AlertType.ERROR, "Пароли не совпадают!").showAndWait();
            return;
        }
        if(passwd1.getText().length()<5 || passwd1.getText().length()>10){
            new Alert(Alert.AlertType.ERROR, "Пароль от 5 до 10").showAndWait();
            return;
        }
        try {
            statement.executeUpdate(String.format("insert into users(name, login, password, role, email, number) values('%s', '%s', '%s', '%s','%s', '%s');",
                    name.getText(), login.getText(), passwd1.getText(), "customers", mail.getText(), phone.getText()));
            new Alert(Alert.AlertType.INFORMATION, "Юзер создан!").showAndWait();
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "Сам догадайся что неверно").showAndWait();
            return;
        }
    }

    public void init(Statement statement, int id){
        this.statement=statement;
        this.id=id;
        stage=(Stage)login.getScene().getWindow();
    }
}