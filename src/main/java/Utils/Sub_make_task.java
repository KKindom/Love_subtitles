package Utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Data;
import lombok.SneakyThrows;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

import java.util.ArrayList;
import java.util.List;

import static Utils.Change_ASS.Requset_listToAss;
import static Utils.Change_SRT.XF_ARR;
import static Utils.Change_SRT.XF_SRT;
import static Utils.LfasrSDKDemo.businessExtraParams;
import static Utils.Video_task.Video_Au;
import static Utils.WebITS.MT;

/**
 * @program: untitled
 * @description: 生成字幕线程
 * @author: kkindom
 * @create: 2021-04-06 11:17
 **/
@Data
public class Sub_make_task  extends Service<Number> {
    //输入文件位置，保存文件前缀，源语言，需要翻译语言 音频地址
    String in_savesubpath,save_prepath,orgin,first,au_path;
    //输入文件类型 选择字幕类型
    int sub_type_n,file_type;
    boolean sub_main;
    //获取字幕数组地址
    List<sub_base> sub_list=null;
    //接口信息
    public static Api_Key api_key=new Api_Key();
    LfasrSDKDemo lfasrSDKDemo=new LfasrSDKDemo();
    WebITS my_webITS=new WebITS();
    //初始化
    public Sub_make_task()
    {
        lfasrSDKDemo.set_all(api_key.my_lfasr);
        my_webITS.set_all(api_key.my_webits);
    }

    @Override
    protected Task<Number> createTask() {
        Task<Number> mytask = new Task<Number>() {
            @Override
            protected Number call() throws Exception {
               try {
                   //如果是视频文件
                   if (file_type == 1)
                   {
                       //视频转音频
                       this.updateProgress(0.1, 1);
                       Video_Au(in_savesubpath, save_prepath + ".mp3");
                       this.updateProgress(0.3, 1);
                       au_path = save_prepath + ".mp3";
                   }
                   else
                   {
                       //如果是音频文件
                       au_path = in_savesubpath;
                   }
                       //音频转讯飞结果
                       this.updateProgress(0.4, 1);
                       lfasrSDKDemo.businessExtraParams(au_path, save_prepath);
                       this.updateProgress(0.6, 1);
                       //讯飞结果转arr数组
                       sub_list = XF_ARR(save_prepath + "\\result.txt", 1);
                       this.updateProgress(0.7, 1);
                       //如果转译需要翻译
                       if (!orgin.equals(first))
                           sub_list = my_webITS.MT(sub_list, orgin, first, 2);
                       this.updateProgress(0.75, 1);
                       //arr数组转字幕文件
                       //若为srt字幕文件
                       if (sub_type_n == 1) {
                           if (sub_main) {
                               XF_SRT("", save_prepath + ".srt", sub_list, 2);
                               this.updateProgress(1, 1);
                           } else
                               {
                               XF_SRT("", save_prepath + ".srt", sub_list, 1);
                               this.updateProgress(1, 1);
                           }
                           this.updateMessage("true");
                       }
                       //否则输出ass字幕文件
                       else {
                           if (sub_main) {
                               Requset_listToAss(save_prepath + ".ass", sub_list, 2);
                               this.updateProgress(1, 1);
                           } else {
                               Requset_listToAss(save_prepath + ".ass", sub_list, 1);
                               this.updateProgress(1, 1);
                           }
                           this.updateMessage("true");
                       }

               }
               //异常处理
                catch (Throwable throwable) {
                   throwable.printStackTrace();
                   this.updateMessage("false");
                   return null;
               }
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
