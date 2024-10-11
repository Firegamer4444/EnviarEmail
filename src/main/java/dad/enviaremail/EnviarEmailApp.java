package dad.enviaremail;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EnviarEmailApp extends Application {

    private RootController rootController = new RootController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(rootController.getRoot());

        primaryStage.setTitle("Enviar email");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
