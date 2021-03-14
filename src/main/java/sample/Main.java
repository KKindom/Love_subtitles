package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.show();

        //设置鼠标监听
        Button b1= (Button) root.lookup("#button1");
        b1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("b1的单击事件");
            }
        });
        b1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("fuck");
                Main2 second=new Main2();
            }
        });
        System.out.println(b1.getText());
        //配置menubar的宽度
//        MenuBar menuBar= (MenuBar) root.lookup("#menubar1");
//        menuBar.setPrefWidth(primaryStage.getWidth());
//        System.out.println("fuck");
//        //合成的MP4E:\桌面\Mytask
//        String mp4SavePath = "E:\\桌面\\Mytask\\img.mp4";
//        //图片地址 这里面放了22张图片
//        String img = "E:\\桌面\\kktest";
//        int width = 1600;
//        int height = 900;
//        //读取所有图片
//        File file = new File(img);
//        File[] files = file.listFiles();
//        Map<Integer, File> imgMap = new HashMap<Integer, File>();
//        int num = 0;
//        for (File imgFile : files) {
//            imgMap.put(num, imgFile);
//            num++;
//        }
//        createMp4(mp4SavePath, imgMap, width, height);
    
    }


    public static void main(String[] args) throws FrameRecorder.Exception {

        launch(args);

    }
     static void createMp4(String mp4SavePath, Map<Integer, File> imgMap, int width, int height) throws FrameRecorder.Exception {
        //视频宽高最好是按照常见的视频的宽高  16：9  或者 9：16
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mp4SavePath, width, height);
        //设置视频编码层模式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        //设置视频为25帧每秒
        recorder.setFrameRate(25);
        //设置视频图像数据格式
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFormat("mp4");
        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            //录制一个22秒的视频
            for (int i = 0; i < 22; i++) {
                BufferedImage read = ImageIO.read(imgMap.get(i));
                //一秒是25帧 所以要记录25次
                for (int j = 0; j < 25; j++) {
                    recorder.record(converter.getFrame(read));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后一定要结束并释放资源
            recorder.stop();
            recorder.release();
        }
    }

}
