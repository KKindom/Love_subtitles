package Utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static Utils.Change_SRT.XF_ARR;
import static Utils.LfasrSDKDemo.businessExtraParams;
import static Utils.SUB_Demo.Draw_Sub;
import static Utils.Video_task.Video_Au;
import static Utils.Video_task.Video_pre;
import static Utils.WebITS.MT;

/**
 * @program: untitled
 * @description: 生成视频字幕线程
 * @author: kkindom
 * @create: 2021-04-07 12:09
 **/
@Data
public class Sub_video_task extends Service<Number> {
    //输入文件位置，保存文件前缀，源语言，需要语言，字幕字体 字体大小
    String in_file_pat="",pre_save_path="",orgin="auto",first="cn",sub_font_type="宋体",au_path,file_suffix;
    //视频质量,线程选择默认视频1 预览2
    int video_qu=0,task=1,sub_font_size=20;
    //接口信息
    public static Api_Key api_key=new Api_Key();
    List<sub_base> sub_list=new ArrayList<>();

    LfasrSDKDemo lfasrSDKDemo=new LfasrSDKDemo();
    WebITS my_webITS=new WebITS();
    //初始化
    public Sub_video_task()
    {
        lfasrSDKDemo.set_all(api_key.my_lfasr);
        my_webITS.set_all(api_key.my_webits);
    }
    @Override
    protected Task<Number> createTask()
    {
        Task<Number> mytask = new Task<Number>() {
            @Override
            protected Number call() throws Exception {
                if(task==1) {
                    //视频字幕线程
                    //处理异常
                    try {
                        //视频转音频
                        this.updateProgress(0.1, 1);
                        //Video_Au(in_file_pat, pre_save_path + ".mp3");
                        this.updateProgress(0.3, 1);
                        //音频转写
                        //音频转讯飞结果
                        this.updateProgress(0.4, 1);
                        //lfasrSDKDemo.businessExtraParams(pre_save_path + ".mp3", pre_save_path);
                        this.updateProgress(0.6, 1);
                        //讯飞结果转arr数组
                        sub_list = XF_ARR(pre_save_path + "\\result.txt", 1);
                        //判断是否需要转译
                        if (!orgin.equals(first))
                            sub_list = my_webITS.MT(sub_list, orgin, first, 2);
                        //画视频字幕文件
                        Draw_Sub(sub_list, in_file_pat, pre_save_path + "."+file_suffix, sub_font_size,sub_font_type);
                        this.updateProgress(1, 1);
                        this.updateMessage("video_done");
                    } catch (Throwable throwable) {
                        this.updateMessage("false");
                        throwable.printStackTrace();
                    }
                }
                //预览视频线程
                else
                {
                    for(int i=0;i<10;i++)
                    {
                        sub_list.add(new sub_base(i*1000,(i+1)*1000,"测试字幕1","测试字幕2"));
                    }
                    this.updateProgress(0.2, 1);
                    Video_pre(in_file_pat,pre_save_path+"p."+file_suffix);
                    this.updateProgress(0.6, 1);
                    Draw_Sub(sub_list, pre_save_path+"p."+file_suffix, pre_save_path+"pre."+file_suffix, sub_font_size,sub_font_type);
                    this.updateProgress(1, 1);
                    this.updateMessage("prevideo_done");
                }
                //画视频文件
                return null;
            }
            };
        return mytask;
    }

    @Override
    protected void executeTask(Task<Number> task) {
        super.executeTask(task);
        task.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("executeTask valueProperty");
            }
        });
    }
    @Override
    protected void ready() {
        super.ready();
        System.out.println("ready"+ Platform.isFxApplicationThread());
    }

    @Override
    protected void scheduled() {
        super.scheduled();
        System.out.println("scheduled "+Platform.isFxApplicationThread());
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("running "+Platform.isFxApplicationThread());
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("succeeded "+Platform.isFxApplicationThread());
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        System.out.println("cancelled "+Platform.isFxApplicationThread());
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("failed "+ Platform.isFxApplicationThread());
    }
}
