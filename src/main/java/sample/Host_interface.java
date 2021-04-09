package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;

import javafx.stage.FileChooser;

import java.io.File;


/**
 * @program: untitled
 * @description: 主界面
 * @author: kkindom
 * @create: 2021-02-28 18:34
 **/
public class Host_interface extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Host_interface.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/自适应布局.fxml"));
        primaryStage.setTitle("i字幕");
        primaryStage.setScene(new Scene(root, 800, 500));
        //加入css库
        primaryStage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.show();
        Thread t = new Thread(new Runnable(){
            public void run(){
                // run方法具体重写
                try {
                    System.out.println("start:");
                    FFmpegFrameGrabber.tryLoad();
                    FFmpegFrameRecorder.tryLoad();
                    System.out.println("end:");
                }
                catch (Exception e)
                {

                }
            }});
        t.start();
        //设置鼠标监听
//        Button b1= (Button) root.lookup("#button2");
//        b1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent event) {
//                System.out.println("112233");
//                chooser(primaryStage);
//            }
//        });


    }

    public static void main(String[] args) throws FrameRecorder.Exception
    {

        launch(args);

    }

    // FileChooser实现
    public void chooser(Stage prStage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择需要的打开的视频文件/字幕文件");
        // 初始打开的位置
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File result =fileChooser.showOpenDialog(prStage);
        // 输入所选择文件的路径
        System.out.print(result.getAbsolutePath());

    }


}
