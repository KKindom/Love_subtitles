package sample;

import Utils.AppModel;
import Utils.DialogBuilder;
import Utils.Sub_change_task;
import Utils.Sub_make_task;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: untitled
 * @description: 生成字幕功能控制器
 * @author: kkindom
 * @create: 2021-03-30 14:38
 **/
public class Host_controller_makesub
{
    //controller之间传递消息
    // 必须static 类型
    public  static AppModel sub_make_msg = new AppModel();

    String in_savesubpath,save_prepath,orgin,first;
    //输入文件类型 选择字幕类型
    int sub_type_n,file_type;
    boolean sub_main=false;
    Map<Integer,String> sub_type_list=new HashMap<Integer,String>();
    Sub_make_task sub_make_task=new Sub_make_task();
    //反悔按钮
    Boolean flag=false;
    //绑定组件
    @FXML
    JFXComboBox sub_orgin,sub_need,sub_type;
    @FXML
    JFXButton file_in,stop_b,start_b,pre_b;
    @FXML
    JFXRadioButton sub_dou;
    @FXML
    JFXProgressBar pbr;
    //控制显示预览视频
    @FXML
    Pane pre_video,video_content;
    //初始化操作
    @FXML
    private void initialize()
    {
        sub_orgin.getItems().addAll("默认(原字幕)","中文","英文");
        sub_need.getItems().addAll("默认(原字幕)","中文","英文","日文","韩文","俄语","法语","德语","藏语","西班牙语","葡萄牙语");
        sub_type.getItems().addAll("默认(SRT)","ASS");
        sub_orgin.getSelectionModel().selectFirst();
        sub_need.getSelectionModel().selectFirst();
        sub_type.getSelectionModel().selectFirst();
        orgin="auto";
        sub_type_n=1;
        first="cn";
        String  sub_list[]={"auto","cn","en","ja","ko","ru","fr","de","ti","es","pt"};
        //初始化
        for(int i=0;i<sub_list.length;i++)
        {
            sub_type_list.put(i,sub_list[i]);
        }
        //绑定监听
        sub_orgin.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            orgin=sub_type_list.get(Integer.parseInt(newv.toString()));
            System.out.println(newv.toString());
            System.out.println("原语言："+orgin);
        });
        sub_need.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            first=sub_type_list.get(Integer.parseInt(newv.toString()));
            System.out.println(newv.toString());
            System.out.println("翻译语言："+first);

        });
        sub_type.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            sub_type_n=newv.toString().equals("0")?1:2;
            System.out.println("选择字幕类型"+sub_type_n);
        });
        //添加任务结果消息监听
        sub_make_task.messageProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //下载提示
                if(newValue.equals("true"))
                {
                    new DialogBuilder(start_b).setTitle("温馨提醒").setMessage("生成字幕成功！\n ").setNegativeBtn("了解","#ff3333").create();
                }
                else
                {
                    pbr.setProgress(0);
                    new DialogBuilder(start_b).setTitle("温馨提醒").setMessage("生成字幕失败！请联系我：").setHyperLink("http://zmk.pw/").setNegativeBtn("了解", "#ff3333").create();
                }
            }
        });

        //消息监听
        //返回监听配合关闭相关界面
        sub_make_msg.backProperty().addListener(new ChangeListener<Boolean>() {
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
        }

    //开始生成字幕
    public void start_makesub(ActionEvent actionEvent)
    {
        //添加反悔按钮   下载须知
        new DialogBuilder(file_in).setPositiveBtn("我在考虑", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
                flag=true;
            }
        }, "#ff3333").setNegativeBtn("我意已决","#ff4444").setTitle("温馨提醒").setMessage("当使用合成字幕功能时，该功能需要联网且保证网络通畅，且受到讯飞服务器影响，若失败请检查网络或查看讯飞账号是否有余额！").create();
        //是否返回
        if(!flag)
        {
            sub_main = sub_dou.isSelected() ? true : false;
            sub_make_task.setSub_main(sub_main);
            sub_make_task.setFile_type(file_type);
            sub_make_task.setFirst(first);
            sub_make_task.setOrgin(orgin);
            sub_make_task.setIn_savesubpath(in_savesubpath);
            sub_make_task.setSave_prepath(save_prepath);
            sub_make_task.setSub_type_n(sub_type_n);
            sub_make_task.start();
            flag=false;
        }
        else
        {
            return;
        }

    }
    //停止生成字幕
    public void cancel_makesub(ActionEvent actionEvent)
    {
        pbr.setProgress(0);
        if(sub_make_task.isRunning())
        {
            sub_make_task.cancel();
        }
        else{
            sub_make_task.cancel();
        }
        sub_make_task= new Sub_make_task();
    }
    //保存/选择文件路径选择
    public void chose_path(ActionEvent actionEvent)
    {
        // 输入所选择文件的路径
        FileChooser fileChooser=new FileChooser();
        Stage stage = (Stage) file_in.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        in_savesubpath =file.getPath();
        //检查
        if(check_file(in_savesubpath)==0)
        {
            new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("对不起，你选择的文件有误。\n 本程序仅支持mp4/avi/3gp/rmvb格式视频！\n 本程序仅支持MP3/acc/wma格式音频！").setNegativeBtn("了解", "#ff3333").create();
            in_savesubpath="";
            return;
        }
        file_in.setText(in_savesubpath);
        System.out.print(file.getPath());
    }
    //检查后缀是否为视频/音频
    public int check_file(String file_path)
    {
        String []pre_Filename_Extension=file_path.split("\\.");
        save_prepath=pre_Filename_Extension[pre_Filename_Extension.length-2];
        System.out.println(save_prepath);
        String Filename_Extension=pre_Filename_Extension[pre_Filename_Extension.length-1];
        System.out.println(Filename_Extension);
        if(Filename_Extension.equalsIgnoreCase("mp4")|Filename_Extension.equalsIgnoreCase("avi")|Filename_Extension.equalsIgnoreCase("rmvb")|Filename_Extension.equalsIgnoreCase("3gp"))
        {
            file_type=1;
            return 1;
        }
        if(Filename_Extension.equalsIgnoreCase("mp3")|Filename_Extension.equalsIgnoreCase("wma")|Filename_Extension.equalsIgnoreCase("aac"))
        {
            file_type=2;
            return 2;
        }
        save_prepath="";
        file_type=0;
        return 0;
    }
    public void pre_downsub(ActionEvent actionEvent)
    {
        // 获取结果界面控制器
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Host_interface_Video.fxml"));
        Host_controller_video control = (Host_controller_video) loader.getController();
        // 设置结果界面内容
        control.model.setpath_video(in_savesubpath);
        control.model.setpath_sub("E:\\桌面\\测试视频\\测试字幕.srt");
        control.model.setback_type(2);
        System.out.println("makesub消息发送成功！");
        pre_video.setVisible(true);
        video_content.setVisible(false);
        pbr.setProgress(0);
    }

}
