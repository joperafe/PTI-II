package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Map<String,String> cs = new HashMap<>();
        cs.put("bola","Noticia");

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("App");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        Label interesse = new Label("Introduza interesse:");
        grid.add(interesse,0,1);

        TextField interesseuser = new TextField();
        grid.add(interesseuser,1,1);

        Button btn = new Button("Ok");
        HBox hbBtn = new HBox();
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn,1,2);
        TextArea ta = new TextArea();
        grid.add(ta,0,4);
        final Text actiontarget = new Text();
        grid.add(actiontarget,1,6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String interesseUser = interesseuser.getText();
                System.out.println(interesseUser);
                // Se não existir na CS lança aviso
                if(cs.containsKey(interesseUser)){
                    String text = "";
                    // Introduzir conteúdo da cs
                    for (Map.Entry<String, String> entry_cs : cs.entrySet()) {
                        //System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
                        text = entry_cs.getValue() +/* "    Field "+i+*/"\n";
                    }
                    ta.setText(text);
                }else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Pedido a ser processado...");
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
