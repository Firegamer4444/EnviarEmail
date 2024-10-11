package dad.enviaremail;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // model

    private StringProperty nombreServidor = new SimpleStringProperty();
    private StringProperty puerto = new SimpleStringProperty();
    private BooleanProperty conexionSSL = new SimpleBooleanProperty();
    private StringProperty emailRemitente = new SimpleStringProperty();
    private StringProperty contraseña = new SimpleStringProperty();
    private StringProperty emailDestinatario = new SimpleStringProperty();
    private StringProperty asunto = new SimpleStringProperty();
    private StringProperty mensaje = new SimpleStringProperty();


    // view

    @FXML
    private TextField asuntoText;

    @FXML
    private CheckBox conexionCheckBox;

    @FXML
    private TextField emailFrText;

    @FXML
    private TextField emailToText;

    @FXML
    private TextArea mensajeText;

    @FXML
    private TextField nombreIpText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField puertoText;

    @FXML
    private GridPane root;

    private Alert errorAlert;
    private Alert mensajeEnviadoAlert;

    public RootController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // bindings

        nombreServidor.bindBidirectional(nombreIpText.textProperty());
        puerto.bindBidirectional(puertoText.textProperty());
        conexionSSL.bindBidirectional(conexionCheckBox.selectedProperty());
        emailRemitente.bindBidirectional(emailFrText.textProperty());
        contraseña.bindBidirectional(passwordText.textProperty());
        emailDestinatario.bindBidirectional(emailToText.textProperty());
        asunto.bindBidirectional(asuntoText.textProperty());
        mensaje.bindBidirectional(mensajeText.textProperty());

        // alertas

        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("No se pudo enviar el email.");

        mensajeEnviadoAlert = new Alert(Alert.AlertType.INFORMATION);

    }

    public GridPane getRoot() {
        return root;
    }

    @FXML
    void onCerrarAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onEnviarAction(ActionEvent event) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(nombreServidor.getValue());
            email.setSmtpPort(Integer.parseInt(puerto.getValue()));
            email.setAuthenticator(new DefaultAuthenticator(emailRemitente.getValue(), contraseña.getValue()));
            email.setSSLOnConnect(conexionSSL.getValue());
            email.setFrom(emailRemitente.getValue());
            email.setSubject(asunto.getValue());
            email.setMsg(mensaje.getValue());
            email.addTo(emailDestinatario.getValue());
            email.send();
        } catch (EmailException e) {
            errorAlert.setContentText(e.getLocalizedMessage());
            errorAlert.show();
            throw new RuntimeException(e);
        }
        mensajeEnviadoAlert.setHeaderText("Mensaje enviado con éxito a '" + emailDestinatario.getValue() + "'.");
        mensajeEnviadoAlert.show();
    }

    @FXML
    void onVaciarAction(ActionEvent event) {
        nombreServidor.set("");
        puerto.set("");
        conexionSSL.set(false);
        emailRemitente.set("");
        contraseña.set("");
        emailDestinatario.set("");
        asunto.set("");
        mensaje.set("");
    }


}
