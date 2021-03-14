import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title : SDK 调用实例</p>
 * <p>Description : </p>
 * <p>Date : 2020/4/20 </p>
 *
 * @author : hejie
 */
public class LfasrSDKDemo {
    private static final String APP_ID = "6037361c";
    private static final String SECRET_KEY = "c715236ba3bd1cdf36383aa5382b7776";


    //音频文件路径
    //1、绝对路径：D:\......\demo-3.0\src\main\resources\audio\lfasr.wav
    //2、相对路径：./resources/audio/lfasr.wav
    //3、通过classpath：
    private static final String AUDIO_FILE_PATH = "E:\\桌面\\kkindom.mp3";

    /**
     * 注意：同时只能执行一个 示例
     *
     * @param args a
     * @throws InterruptedException e
     */
    public static void main(String[] args) throws InterruptedException {
        // 示例-1：标准用法
        //standard();

        // 示例-2：使用扩展业务参数
        businessExtraParams();

        // 示例-3：使用网络代理
        //netProxy();

        // 示例-4：使用性能调优参数
        //performance();
    }

    /**
     * 简单 demo 样例
     *
     * @throws InterruptedException e
     */
    private static void standard() throws InterruptedException {
        //1、创建客户端实例
        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);
        System.out.println(AUDIO_FILE_PATH);
        //2、上传
        Message task = lfasrClient.upload(AUDIO_FILE_PATH);
        String taskId = task.getData();

        System.out.println("转写任务 taskId：" + taskId);

        //3、查看转写进度
        int status = 0;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            status = object.getInteger("status");
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        System.out.println("转写结果: \n" + result.getData());

        File file=new File("C:\\au_result");
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        try{//异常处理
            //如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\au_result\\au_result.txt"));
            bw.write(result.getData());//写入切片后文件路径
            bw.close();//一定要关闭文件
        }catch(IOException e){
            e.printStackTrace();
        }


        //退出程序，关闭线程资源，仅在测试main方法时使用。
        System.exit(0);
    }

    /**
     * 带有业务参数，调用样例
     *
     * @throws InterruptedException e
     */
    private static void businessExtraParams() throws InterruptedException {
        //1、创建客户端实例
        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);

        //2、上传
        //2.1、设置业务参数
        Map<String, String> param = new HashMap<String, String>(16);
        //是否开启分词：默认 false
        //param.put("has_participle","true");
        //转写结果中最大的候选词个数：默认：0，最大不超过5
        //param.put("max_alternatives","2");

        //是否开启角色分离：默认为false
        //param.put("has_seperate","true");
        //发音人个数，可选值：0-10，0表示盲分：默认 2
        //param.put("speaker_number","3");
        //角色分离类型 1-通用角色分离；2-电话信道角色分离：默认 1
        //param.put("role_type","1");

        //语种： cn-中文（默认）;en-英文（英文不支持热词）
        param.put("language", "cn");
        //垂直领域个性化：法院-court；教育-edu；金融-finance；医疗-medical；科技-tech
        //param.put("pd","finance");

        Message task = lfasrClient.upload(
                AUDIO_FILE_PATH
                , param);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);

        //3、查看转写进度
        int status = 0;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            status = object.getInteger("status");
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        System.out.println("转写结果: \n" + result.getData());

        File file=new File("C:\\au_result");
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        try{//异常处理
            //如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\au_result\\au_result.txt"));
            bw.write(result.getData());//写入切片后文件路径
            bw.close();//一定要关闭文件
        }catch(IOException e){
            e.printStackTrace();
        }


        //退出程序，关闭线程资源，仅在测试main方法时使用。
        System.exit(0);
    }

    /**
     * 设置网络代理，调用样例
     *
     * @throws InterruptedException e
     */
    private static void netProxy() throws InterruptedException {
        //1、创建客户端实例, 设置网络代理
        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY, "http://x.y.z/");
        //LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);


        //2、上传
        //2.1、设置业务参数
        Map<String, String> param = new HashMap<String, String>(16);
        //语种： cn-中文（默认）;en-英文（英文不支持热词）
        param.put("language", "cn");
        //垂直领域个性化：法院-court；教育-edu；金融-finance；医疗-medical；科技-tech
        //param.put("pd","finance");

        Message task = lfasrClient.upload(
                AUDIO_FILE_PATH
                , param);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);


        //3、查看转写进度
        int status = 0;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            status = object.getInteger("status");
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        System.out.println("转写结果: \n" + result.getData());


        //退出程序，关闭线程资源，仅在测试main方法时使用。
        System.exit(0);
    }

    /**
     * 性能调优参数，调用样例
     *
     * @throws InterruptedException e
     */
    private static void performance() throws InterruptedException {
        //1、创建客户端实例, 设置性能参数
        LfasrClient lfasrClient =
                LfasrClient.getInstance(
                        APP_ID,
                        SECRET_KEY,
                        10, //线程池：核心线程数
                        50, //线程池：最大线程数
                        50, //网络：最大连接数
                        10000, //连接超时时间
                        30000, //响应超时时间
                        null);
        //LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);


        //2、上传
        //2.1、设置业务参数
        Map<String, String> param =new HashMap<String, String>(16);
        //语种： cn-中文（默认）;en-英文（英文不支持热词）
        param.put("language", "cn");
        //垂直领域个性化：法院-court；教育-edu；金融-finance；医疗-medical；科技-tech
        //param.put("pd","finance");

        Message task = lfasrClient.upload(
                AUDIO_FILE_PATH
                , param);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);


        //3、查看转写进度
        int status = 0;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            status = object.getInteger("status");
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        System.out.println("转写结果: \n" + result.getData());
        //退出程序，关闭线程资源，仅在测试main方法时使用。
        System.exit(0);
    }

}
