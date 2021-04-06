package Utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * @program: untitled
 * @description: 用于提取音频
 * @author: kkindom
 * @create: 2021-02-16 22:24
 **/
public class Au_task {


    //视频转音频函数 (old 版本)
//    static void work()
//    {
//        //拉取流
//        FFmpegFrameGrabber fg = new FFmpegFrameGrabber("E:\\桌面\\ttt.mp4");
//
//        // 音频录制（输出地址，音频通道）
//        FFmpegFrameRecorder recorder = null;
//
//        int sec = 60;
//        Frame f=null;
//        //之前纯音频文件设置了时间戳有问题，视频没问题。
//        //在JavaCV1.4.1修复了这个问题，因此可以调用
//        try {
//            fg.setTimestamp(sec * 1000000);
//            System.out.println("fuck");
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        }
//
//        if (start(fg))
//        {
//            //录制流配置
//            recorder=new FFmpegFrameRecorder("E:\\桌面\\test2.mp4", 1);
//            recorder.setAudioOption("crf", "0");
//            recorder.setAudioCodec(fg.getAudioCodec());
//            recorder.setAudioBitrate(fg.getAudioBitrate());
//            recorder.setSampleRate(fg.getSampleRate());
//            recorder.setAudioCodecName("libmp3lame");
//            if (start(recorder))
//            {
//                while (true) {
//                    try {
//                        f = fg.grabSamples();
//                    } catch (FrameGrabber.Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        recorder.setTimestamp(fg.getTimestamp());
//                        recorder.record(f);
//                    } catch (FrameRecorder.Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (f == null) {
//                        stop(fg);
//                        stop(recorder);
//                        //System.exit(0);
//                    }
//            }
//        }
//
//        }
//    }


    //切割音频
    public static void Au_cut(String input, String output, Integer segmentTime) throws FrameRecorder.Exception, org.bytedeco.javacv.FrameGrabber.Exception {
        //输入流
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(input);

        grabber.start();


        //录制流
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(output, 1);

        //配置切割长度
        recorder.setFormat("segment");

        if (segmentTime == null) {
            segmentTime = 1000;//默认10秒生成一个文件
        }

        recorder.setOption("segment_time", segmentTime.toString());


        //生成模式：live（实时生成）,cache（边缓存边生成，只支持m3u8清单文件缓存）
        recorder.setOption("segment_list_flags", "live");

        //配置录制器属性
        recorder.setFrameRate(grabber.getFrameRate());//设置帧率
        recorder.setAudioOption("crf", "0");
        recorder.setAudioCodec(grabber.getAudioCodec());
        recorder.setAudioBitrate(grabber.getAudioBitrate());
        recorder.setSampleRate(grabber.getSampleRate());
        recorder.setAudioCodecName("libmp3lame");
        recorder.start();



        Frame frame = null;

        // 只抓取图像画面
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
    }
    public static boolean start(FrameGrabber grabber) {
        try {
            grabber.start();
            return true;
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e2) {
            try {
                System.err.println("首次打开抓取器失败，准备重启抓取器...");
                grabber.restart();
                return true;
            } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
                try {
                    System.err.println("重启抓取器失败，正在关闭抓取器...");
                    grabber.stop();
                } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
                    System.err.println("停止抓取器失败！");
                }
            }

        }
        return false;
    }
    public static boolean start(FrameRecorder recorder) {
        try {
            recorder.start();
            return true;
        } catch (Exception e2) {
            try {
                System.err.println("首次打开录制器失败！准备重启录制器...");
                recorder.stop();
                recorder.start();
                return true;
            } catch (Exception e) {
                try {
                    System.err.println("重启录制器失败！正在停止录制器...");
                    recorder.stop();
                } catch (Exception e1) {
                    System.err.println("关闭录制器失败！");
                }
            }
        }
        return false;
    }
    public static boolean stop(FrameGrabber grabber) {
        try {
            grabber.flush();
            grabber.stop();
            return true;
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
            return false;
        } finally {
            try {
                grabber.stop();
            } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
                System.err.println("关闭抓取器失败");
            }
        }
    }
    public static boolean stop(FrameRecorder recorder) {
        try {
            recorder.stop();
            recorder.release();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                recorder.stop();
            } catch (Exception e) {

            }
        }

    }
    public static void main(String[] args){


        long startTime=System.currentTimeMillis();   //获取开始时间
        //work();

        long endTime=System.currentTimeMillis(); //获取结束时间
        try {
            Au_cut("E:\\桌面\\专业\\东软\\untitled\\src\\main\\resources\\video\\1.mp4", "kkindom%03d.mp4",300);
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

    }
}