package Utils;


import lombok.SneakyThrows;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.FrameRecorder.Exception;
import java.io.*;
import java.io.File;
/**
 * @program: untitled
 * @description: 处理视频任务
 * @author: kkindom
 * @create: 2021-02-19 14:21
 **/
public class Video_task {

        /**
         * 视频分片文件
         *
         * @param input       视频文件（mp4,flv,avi等等）
         * @param output      视频名称或者名称模板
         * @param frameRate   帧率
         * @param segmentTime 单个分片时长（单位：秒）
         */
        public static void Video_cut(String input, String output, Integer segmentTime, Integer frameRate) throws Exception, org.bytedeco.javacv.FrameGrabber.Exception {
        //提取流
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(input);

        grabber.start();

//        if (width == null || height == null) {
//            width = grabber.getImageWidth();
//            height = grabber.getImageHeight();
//        }
       int  width = grabber.getImageWidth();
        int height = grabber.getImageHeight();
        //配置录制器参数
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(output, width, height, 1);

        recorder.setFormat("segment");

        if (segmentTime == null) {
            segmentTime = 300;//默认300秒生成一个文件
        }

        recorder.setOption("segment_time", segmentTime.toString());

        //生成模式：live（实时生成）,cache（边缓存边生成，只支持m3u8清单文件缓存）
        recorder.setOption("segment_list_flags", "live");


        if (frameRate == null) {
            frameRate = 25;
        }
        recorder.setFrameRate(grabber.getFrameRate());//设置帧率
        //因为我们是直播，如果需要保证最小延迟，gop最好设置成帧率相同或者帧率*2
        //一个gop表示关键帧间隔，假设25帧/秒视频，gop是50，则每隔两秒有一个关键帧，播放器必须加载到关键帧才能够开始解码播放，也就是说这个直播流最多有2秒延迟
        //recorder.setGopSize(grabber.getFrameRate());//设置gop
        //recorder.setVideoQuality(1.0); //视频质量
        recorder.setVideoBitrate(grabber.getVideoBitrate());//码率,10kb/s

//		recorder.setVideoCodecName("h264");//设置视频编码
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);//这种方式也可以
//		recorder.setAudioCodecName("aac");//设置音频编码，这种方式设置音频编码也可以
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);//设置音频编码

        recorder.start();


        Frame frame = null;

        // 只抓取图像画面
        for (; (frame = grabber.grabFrame()) != null; ) {
            try {

                //录制/推流
                recorder.record(frame);

            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }

        recorder.close();//close包含stop和release方法。录制文件必须保证最后执行stop()方法，才能保证文件头写入完整，否则文件损坏。
        grabber.close();//close包含stop和release方法
    }

        /**
         * 视频转音频
         *
         * @param input       输入视频文件（mp4,flv,avi等等）
         * @param output      输出视频名称或者名称模板
         */
        public static void Video_Au(String input, String output) throws Exception, org.bytedeco.javacv.FrameGrabber.Exception {
        //抓取流
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(input);

        grabber.start();
        //录制流
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(output,  1);



        //recorder.setOption("segment_time", segmentTime.toString());

        //生成模式：live（实时生成）,cache（边缓存边生成，只支持m3u8清单文件缓存）
        recorder.setOption("segment_list_flags", "live");



        recorder.setFrameRate(grabber.getFrameRate());//设置帧率

        recorder.setAudioOption("crf", "0");
        recorder.setAudioCodec(grabber.getAudioCodec());
        recorder.setAudioBitrate(grabber.getAudioBitrate());
        recorder.setSampleRate(grabber.getSampleRate());
        recorder.setAudioCodecName("libmp3lame");
        recorder.start();


        Frame frame = null;

        // 只抓取音频
        for (; (frame = grabber.grabSamples()) != null; ) {
            try {

                //录制/推流
                recorder.record(frame);

            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }

        recorder.close();//close包含stop和release方法。录制文件必须保证最后执行stop()方法，才能保证文件头写入完整，否则文件损坏。
        grabber.close();//close包含stop和release方法
            return;
    }
        /**
         * 拼接合成视频（转复用模式，transMuxer），这种方式只支持相同格式，相同编码,相同分辨率的文件格式
         *
         * @param fileListTxt 包含文件列表的txt文件，可以接受多个视频源地址
         * <p>txt内部格式：</p>
         * <p>file 'D:\下载\audio.mp4'</p>
         * <p>file 'D:\下载\audio2.mp4'</p>
         * @param output
         */
        public static void concatFromFileListTxt(String fileListTxt, String output)throws IOException, Exception {

            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(fileListTxt);
            grabber.setFormat("concat");

            //是否检查文件路径，支持的值：0-不检查，1-检查（只接受相对路径，仅支持包含字母，数字，句点，下划线和连字符的字符串，且在开头没有句点，才认为该文件路径是安全的）
            grabber.setOption("safe", "0");

            //自动转换码率，支持的值：1-自动转换，0-不转换，默认1
            grabber.setOption("auto_convert", "1");

            //每个包都设置开始时间和持续时间元数据，默认0
            grabber.setOption("segment_time_metadata", "1");
            grabber.start();

            FFmpegFrameRecorder recorder=new FFmpegFrameRecorder(output,grabber.getImageWidth(),grabber.getImageHeight(),grabber.getAudioChannels());
            recorder.start(grabber.getFormatContext());

            AVPacket packet=null;

            //解封装/解复用
            for (;(packet = grabber.grabPacket()) != null;) {
                //封装/复用
                recorder.recordPacket(packet);
            }
            recorder.stop();
            grabber.stop();
        }


        //视频合并前预处理
         public static Boolean Preparation_before_treatment(String video) {
        //获得视频时间 秒 为单位
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            duration = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        System.out.println("视频时长为："+duration+" 秒");
        //处理视频切片存储位置
        int num=(int)(duration/300);
        System.out.println("切片后文件个位为"+(num)+" 个");
        String[] name={"kkindom00","kkindom0","kkindom"};
        int type=0;
        File file=new File("C:\\video_cut");
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        try{//异常处理
            //如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\video_cut\\video_list.txt"));
            for(int i=0;i<=num;i++) {
                type= i<10?0:(i<100)?1:2;
                bw.write("file 'C:\\video_cut\\"+name[type]+i+".mp4' \r\n");//写入切片后文件路径
            }
            bw.close();//一定要关闭文件
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(duration);
        return true;
    }

        @SneakyThrows
        public static void main(String[] args) throws Exception, org.bytedeco.javacv.FrameGrabber.Exception {
//        long startTime=System.currentTimeMillis();   //获取开始时间
//
        //Preparation_before_treatment("E:\\桌面\\tt.mp4");
        //System.out.println("预处理完成!");
        //Video_cut("E:\\桌面\\tt.mp4","C:\\video_cut\\kkindom%03d.mp4",300,25);
        //System.out.println("切片完成！");
//        concatFromFileListTxt("concat:C:\\video_cut\\video_list.txt","E:\\桌面\\合并.mp4");
//        System.out.println("合并完成！");
//
//        long endTime=System.currentTimeMillis(); //获取结束时间
//        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        Video_Au("E:\\桌面\\1.mp4", "E:\\桌面\\kkindom.mp3");
    }
}
