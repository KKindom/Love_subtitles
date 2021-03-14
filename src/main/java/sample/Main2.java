package sample;

import Utils.Mytask;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import org.bytedeco.javacv.FrameRecorder;

/**
 * @program: untitled
 * @description: 测试
 * @author: kkindom
 * @create: 2021-01-07 10:39
 **/
public class Main2 extends Application {

    public Main2() {
        super();
        Stage stage = new Stage();
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/testTask.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();}

    public static void main(String[] args) throws FrameRecorder.Exception
    {
//        Mytask mytask=new Mytask();
//        Thread thread=new Thread(mytask);
//        thread.start();
        launch(args);

    }
}
