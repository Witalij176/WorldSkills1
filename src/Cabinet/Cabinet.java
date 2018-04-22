package Cabinet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

public class Cabinet {
    int id;
    Statement statement;
    Stage stage;
    boolean edit=false;

    @FXML TextField name;
    @FXML TextField login;
    @FXML PasswordField passwd;
    @FXML TextField mail;
    @FXML TextField phone;
    @FXML Button editButton;

    public void init(Statement statement, int id){
        stage=(Stage)login.getScene().getWindow();
        this.statement=statement;
        this.id=id;
        try {
            ResultSet resultSet = statement.executeQuery("select name, login, password, role, email, number from users where id="+id+";");
            resultSet.next();
            name.setText(resultSet.getString("name"));
            login.setText(resultSet.getString("login"));
            passwd.setText(resultSet.getString("password"));
            mail.setText(resultSet.getString("email"));
            phone.setText(resultSet.getString("number"));
            switch (resultSet.getString("role")){
                case "directors": stage.setTitle("Добро пожаловать директор!"); break;
                case "managers": stage.setTitle("Добро пожаловать менеджер!"); break;
                case "customers": stage.setTitle("Добро пожаловать клиент!");
            }
            resultSet.close();
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "Не могу подключится к базе!").showAndWait();
        }
    }

    public void editButtonClick(ActionEvent actionEvent) {
        if(!edit) {
            edit=true;
            editButton.setText("Сохранить");
            name.setEditable(true);
            login.setEditable(true);
            passwd.setEditable(true);
            mail.setEditable(true);
            phone.setEditable(true);
        }
        else{

            if(name.getText().equals("")||login.getText().equals("")||passwd.getText().equals("")){
                new Alert(Alert.AlertType.ERROR, "Поля имя, логин, и пароль не должны быть пустыми!").showAndWait();
                return;
            }
            if(passwd.getText().length()<5 || passwd.getText().length()>10){
                new Alert(Alert.AlertType.ERROR, "Пароль от 5 до 10").showAndWait();
                return;
            }
            try{
                statement.executeUpdate(String.format("update users set name='%s', login='%s', password='%s', email='%s', number='%s' where id='%s';", name.getText(), login.getText(), passwd.getText(), mail.getText(), phone.getText(), id));
                new Alert(Alert.AlertType.INFORMATION, "Данные обновлены!").showAndWait();
            }
            catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Сам догадайся что неверно").showAndWait();
                return;
            }

            edit=false;
            editButton.setText("Редактировать");
            name.setEditable(false);
            login.setEditable(false);
            passwd.setEditable(false);
            mail.setEditable(false);
            phone.setEditable(false);
        }
    }

    public void exitButtonClick(ActionEvent actionEvent) {
        stage.close();
    }
}
