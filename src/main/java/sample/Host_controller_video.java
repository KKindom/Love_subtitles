package sample;

import java.io.*;
import java.util.List;

import Utils.AppModel;
import Utils.Pre_video_task;
import Utils.sub_base;
import it.sauronsoftware.jave.EncoderException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import static Utils.Change_SRT.SRT_SUBBASE;

/**
 * @program: untitled
 * @description: 主界面外挂视频播放控制器
 * @author: kkindom
 * @create: 2021-02-28 18:49
 **/
public class Host_controller_video
{

    //controller之间传递消息
    // 必须static 类型
    public  static AppModel model = new AppModel();

    List<sub_base> sub_baseList=null;
    //传来的播放文件以及播放类型 1为视频预览 2为字幕预览
    String pre_video_path,video_type,pre_sub_path;
    int type;
    @FXML
     MediaView video;
    MediaPlayer mediaPlayer;
    @FXML
    Label sub_txt,file_in;
    @FXML
    Pane video_content;
    //字幕任务

    Pre_video_task my_task_sub=new Pre_video_task();

    @FXML
    private void initialize()
    {
        sub_txt.setText("");
        //跨controller监听传递信息
        model.path_videoProperty().addListener((obs, oldText, newText) -> pre_video_path=newText);

        model.path_subProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                pre_sub_path=newValue;
                if(pre_sub_path.equals(""))
                {
                    video(pre_video_path,1);
                }
                else
                {
                    video(pre_video_path,2);
                }
                System.out.println("消息接送成功！");
            }
        });
        System.out.println("初始化视频预览界面");
        System.out.println(video.getFitHeight());

    }

    //播放视频
    public void start_video(ActionEvent actionEvent)
    {

        video(pre_video_path,type);
    }
    /**
     * 播放视频函数
     * @param type 播放类型 1为预览视频 2为预览字幕视频
     * @param file_path 选择播放视频的路径
     * @return 返回编辑好的数据
     */
    public void video(String file_path,int type)
    {
        System.out.println("start_video");
        String path=file_path;
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        video.setMediaPlayer(mediaPlayer);
        System.out.println(media.getWidth());
        //调整视频以及字幕位置
        get_vodeo();
        if(type==2)
        {
            //初始化字幕列表
            sub_baseList = SRT_SUBBASE(model.getPath_sub().get());
            my_task_sub.setSub_list(sub_baseList);
            //字幕任务启动以及监听
            my_task_sub.messageProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    sub_txt.setText(sub_baseList.get(Integer.parseInt(newValue)).data);
                    //System.out.println("ttt");
                }

            });
            my_task_sub.start();
            System.out.println("测试启动线程");
        }
        //启动视频
        mediaPlayer.setAutoPlay(true);
    }

    //重新启动播放视频
    public void restart_video(ActionEvent actionEvent)
    {
        sub_txt.setText("");
        my_task_sub.cancel();
        my_task_sub=new Pre_video_task();
        my_task_sub.setSub_list(sub_baseList);
        my_task_sub.start();
        my_task_sub.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sub_txt.setText(sub_baseList.get(Integer.parseInt(newValue)).data);
                //System.out.println("ttt");
            }

        });
        mediaPlayer.stop();
        mediaPlayer.play();
    }
    //调整视频以及字幕位置
    void get_vodeo()
    {
        File source = new File(pre_video_path);

        Encoder encoder = new Encoder();
        MultimediaInfo m = null;
        try {
            m = encoder.getInfo(source);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        // 视频帧宽高
        int hight=m.getVideo().getSize().getHeight();
        int width=m.getVideo().getSize().getWidth();
        Double real_h=560/(width/(double)hight);
        sub_txt.setLayoutY((int)((460-real_h)/2+real_h*0.8));
        System.out.println(hight*0.8+real_h);
        real_h=(460-real_h)/2;
        video.setY(real_h);
    }
    //返回按钮
    public void back(ActionEvent actionEvent)
    {
        // 获取结果界面控制器
        int back_type=model.getBack_type().intValue();
        //返回字幕视频标志
        if(back_type==1)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Host_interface_subvideo.fxml"));
            Host_controller_subvideo control = (Host_controller_subvideo) loader.getController();
            control.sub_video_msg.setback(true);
        }
        //返回预览字幕标志
        else if(back_type==2)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Host_interface_makesub.fxml"));
            Host_controller_makesub control = (Host_controller_makesub) loader.getController();
            control.sub_make_msg.setback(true);
        }
        mediaPlayer.stop();
        if(my_task_sub.isRunning())
            my_task_sub.cancel();
        System.out.println("取消状态！");
    }
}




