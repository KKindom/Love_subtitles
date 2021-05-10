package sample;

import Utils.Api_Key;
import Utils.AppModel;
import Utils.DialogBuilder;
import Utils.Sub_video_task;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: untitled
 * @description: 视频字幕控制器
 * @author: kkindom
 * @create: 2021-04-01 12:32
 **/
public class Host_controller_subvideo
{
    //controller之间传递消息
    // 必须static 类型
    public  static AppModel sub_video_msg = new AppModel();

    //输入文件位置，保存文件前缀，源语言，需要语言，字幕字体 字体大小 文件后缀
    String in_file_pat="",pre_save_path="",orgin="auto",first="cn",sub_font_type="宋体",file_suffix;
    //字幕文号，视频质量
    int video_qu=0,sub_font_size=20;
    boolean flag=false;
    Sub_video_task sub_video_task=new Sub_video_task();

    @FXML
    JFXButton file_in,st_pre,re_pre,bk_pre;
    @FXML
    JFXComboBox sub_first1,sub_first2,video_q,sub_orgin,sub_first;
    @FXML
    JFXProgressBar pbr;
    //控制显示预览视频
    @FXML
    Pane pre_video,video_content;
    @FXML
    MediaView video;
    MediaPlayer mediaPlayer;
    @FXML
    private void initialize() {
        System.out.println("开始初始化生成字幕视频fxml");
        pbr.setProgress(0);
        //初始化下拉框
        sub_orgin.getItems().addAll("默认(原字幕)", "中文", "英文");
        sub_first.getItems().addAll("默认(原字幕)","中文","英文","日文","韩文","俄语","法语","德语","藏语","西班牙语","葡萄牙语");
        sub_first1.getItems().addAll("宋体", "Arial", "楷书", "微软雅黑", "仿宋");
        sub_first2.getItems().addAll("30px", "35px", "40px", "45px", "50px");
        video_q.getItems().addAll("原画质", "高清", "标清");
        sub_orgin.getSelectionModel().selectFirst();
        sub_first.getSelectionModel().selectFirst();
        sub_first1.getSelectionModel().selectFirst();
        sub_first2.getSelectionModel().selectFirst();
        video_q.getSelectionModel().selectFirst();
        String sub[]={"auto","cn","en","ja","ko","ru","fr","de","ti","es","pt"};
        String sub_f[]={"宋体", "Arial", "楷书", "微软雅黑", "仿宋"};
        //绑定监听
        sub_orgin.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            orgin=sub[Integer.parseInt(newv.toString())];
            System.out.println(newv.toString());
            System.out.println("源语言："+orgin);

        });
        sub_first.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            first=sub[Integer.parseInt(newv.toString())];
            System.out.println(newv.toString());
            System.out.println("翻译语言："+first);

        });
        sub_first1.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            sub_font_type=sub_f[Integer.parseInt(newv.toString())];
            System.out.println(newv.toString());
            System.out.println("选择字幕字体："+sub_font_type);

        });
        sub_first2.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            sub_font_size=Integer.parseInt(newv.toString())*5+30;
            System.out.println(newv.toString());
            System.out.println("选择字幕大小："+sub_font_size);

        });
        video_q.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            video_qu=Integer.parseInt(newv.toString());
            System.out.println(newv.toString());
            System.out.println("视频质量："+video_qu);

        });
        //添加任务结果消息监听
        sub_video_task.messageProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //下载提示
                if(newValue.equals("video_done"))
                {
                    new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("下载成功！\n 文件保存路径为："+pre_save_path).setNegativeBtn("了解","#ff3333").create();
                    pbr.setProgress(0);
                }
                else if (newValue.equals("prevideo_done"))
                {
//                    // 获取结果界面控制器
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Host_interface_Video.fxml"));
//                    Host_controller_video control = (Host_controller_video) loader.getController();
//                    // 设置结果界面内容
//                    control.model.setpath_video(pre_save_path+"pre."+file_suffix);
//                    control.model.setpath_sub("");
//                    control.model.setback_type(1);
//                    pre_video.setVisible(true);
//                    video_content.setVisible(false);
//                     pbr.setProgress(0);
                    s_video(pre_save_path+"pre."+file_suffix);
                    pre_video.setVisible(true);
                    pbr.setProgress(0);
                }
                else
                {
                    pbr.setProgress(0);
                    new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("下载失败！请尝试其他方式:仅生成字幕").setNegativeBtn("了解", "#ff3333").create();

                }
            }
        });
        //设置下载线程监听
        sub_video_task.progressProperty().addListener(new ChangeListener<Number>() {
            //newValue 为 workDone/max
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                pbr.setProgress(newValue.doubleValue());

            }
        });
        //消息监听
        //返回监听配合关闭相关界面
        sub_video_msg.backProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //若点击返回按钮
                if(newValue)
                {
                    pre_video.setVisible(false);
                    video_content.setVisible(true);
                }

            }
        });
        //设置接口信息

    }
    //开始生成字幕视频
    public void start_downsubvideo(ActionEvent actionEvent)
    {

        //检查信息是否完整

        //添加反悔按钮
        new DialogBuilder(file_in).setPositiveBtn("我在考虑", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
                flag=true;
            }
        }, "#ff3333").setNegativeBtn("我意已决","#ff4444").setTitle("温馨提醒").setMessage("当使用视频字幕功能时，完成速度取决于计算机运算能力，若非必要，优先级：字幕下载>仅生成字幕>生成字幕视频！").create();
       //是否返回
        if(!flag)
        {
            sub_video_task.cancel();
            sub_video_task=new Sub_video_task();
            //添加任务结果消息监听
            sub_video_task.messageProperty().addListener(new ChangeListener<String>()
            {
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    //下载提示
                    if(newValue.equals("video_done"))
                    {
                        new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("下载成功！\n 文件保存路径为："+pre_save_path).setNegativeBtn("了解","#ff3333").create();
                        pbr.setProgress(0);
                    }
                    else
                    {
                        pbr.setProgress(0);
                        new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("下载失败！请尝试其他方式:仅生成字幕").setNegativeBtn("了解", "#ff3333").create();
                    }
                }
            });
            //设置下载线程监听
            sub_video_task.progressProperty().addListener(new ChangeListener<Number>() {
                //newValue 为 workDone/max
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    pbr.setProgress(newValue.doubleValue());
                }
            });
            sub_video_task.setTask(1);
            sub_video_task.setVideo_qu(video_qu);
            sub_video_task.setFirst(first);
            sub_video_task.setOrgin(orgin);
            sub_video_task.setIn_file_pat(in_file_pat);
            sub_video_task.setPre_save_path(pre_save_path);
            sub_video_task.setSub_font_size(sub_font_size);
            sub_video_task.setSub_font_type(sub_font_type);
            sub_video_task.setFile_suffix(file_suffix);
            sub_video_task.start();
            flag=false;
        }
        else {
            return;
        }
    }
    //预览字幕视频
    public void pre_downsubvideo(ActionEvent actionEvent)
    {
        sub_video_task.setTask(2);
        sub_video_task.setVideo_qu(video_qu);
        sub_video_task.setFirst(first);
        sub_video_task.setOrgin(orgin);
        sub_video_task.setIn_file_pat(in_file_pat);
        sub_video_task.setPre_save_path(pre_save_path);
        sub_video_task.setSub_font_size(sub_font_size);
        sub_video_task.setSub_font_type(sub_font_type);
        sub_video_task.setFile_suffix(file_suffix);
        sub_video_task.start();
    }
    //停止生成字幕视频
    public void cancel_downsubvideo(ActionEvent actionEvent)
    {
        pbr.setProgress(0);
        if(sub_video_task.isRunning())
        {
            sub_video_task.cancel();
        }
        else{
            sub_video_task.cancel();
        }
        sub_video_task=new Sub_video_task();
    }

    //保存文件路径选择
    public void chose_path(ActionEvent actionEvent)
    {
        // 输入所选择文件的路径
        FileChooser fileChooser=new FileChooser();
        Stage stage = (Stage) file_in.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        in_file_pat =file.getPath();
        //检查
        if(!check_file(in_file_pat))
        {
            new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("对不起，你选择的文件有误，本程序仅支持mp4/avi/rmvb类型视频文件！").setNegativeBtn("了解", "#ff3333").create();
            in_file_pat="";
            return;
        }
        file_in.setText(in_file_pat);

        System.out.print(file.getPath());
    }
    //检查后缀是否为视频文件
    public Boolean check_file(String file_path)
    {
        String []pre_Filename_Extension=file_path.split("\\.");
        pre_save_path=pre_Filename_Extension[pre_Filename_Extension.length-2];
        System.out.println(pre_save_path);
        String Filename_Extension=pre_Filename_Extension[pre_Filename_Extension.length-1];
        System.out.println(Filename_Extension);
        if(Filename_Extension.equalsIgnoreCase("mp4")|Filename_Extension.equalsIgnoreCase("avi")|Filename_Extension.equalsIgnoreCase("rmvb"))
        {
            file_suffix=Filename_Extension;
            return true;
        }
        else
        {
            file_suffix="";
        }
        return false;
    }

    public void start_video(ActionEvent actionEvent)
    {
        mediaPlayer.stop();
        mediaPlayer.play();
    }
    public void s_video(String url)
    {
        System.out.println("start_video");
        String path=pre_save_path+"pre."+file_suffix;
        System.out.println("播放视频路径为："+path);
        Media media = new Media(new File(path).toURI().toString());
         mediaPlayer =new MediaPlayer(media) ;
        //video.setMediaPlayer();
        this.video.setMediaPlayer(mediaPlayer);
        System.out.println(video.getMediaPlayer());
        System.out.println(media.getWidth());
        //调整视频以及字幕位置
        get_vodeo();
        mediaPlayer.setAutoPlay(true);
    }

    public void restart_video(ActionEvent actionEvent)
    {
        mediaPlayer.stop();
        mediaPlayer.play();
    }

    public void back(ActionEvent actionEvent)
    {
        pre_video.setVisible(false);
        video_content.setVisible(true);
        mediaPlayer.stop();
        System.out.println("关闭的"+mediaPlayer);
        System.out.println("关闭的"+video);
        System.out.println("取消状态！");
    }
    void get_vodeo()
    {
        File source = new File(pre_save_path+"pre."+file_suffix);

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
        System.out.println(hight*0.8+real_h);
        real_h=(460-real_h)/2;
        video.setY(real_h);
        System.out.println("视频位置：x："+video.getX()+"y:"+video.getY());
        st_pre.setLayoutY(460-real_h);
        re_pre.setLayoutY(460-real_h);
        bk_pre.setLayoutY(460-real_h);
    }
}
