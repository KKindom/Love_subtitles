package Utils;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static Utils.Change_SRT.SRT_SUBBASE;
import static Utils.Change_SRT.XF_ARR;


/**
 * @program: untitled
 * @description: 测试视频合成字字幕效果
 * @author: kkindom
 * @create: 2021-02-25 14:50
 **/
public class SUB_Demo
{


    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args)  {
        //预加载资源
        try {
            System.out.println("start:");
            FFmpegFrameGrabber.tryLoad();
            FFmpegFrameRecorder.tryLoad();
            System.out.println("end:");
        }
        catch (Exception e)
        {

        }
        //拿到字幕数组
        List<sub_base> arr=new ArrayList<>();
        try {
            arr=  XF_ARR("E:\\桌面\\马飞凡-功能演示视频\\result.txt",1);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println(arr);
        try {
            Draw_Sub(arr,"E:\\桌面\\马飞凡-功能演示视频.mp4","E:\\桌面\\cs.mp4",50,"Default");
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        }


//        // 设置源视频、加字幕后的视频文件路径
//        FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault("E:\\桌面\\1.mp4");
//        grabber.start();
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("E:\\桌面\\2.mp4",
//                grabber.getImageWidth(),grabber.getImageHeight(), grabber.getAudioChannels());
//
//        // 视频相关配置，取原视频配置
//        recorder.setFrameRate(grabber.getFrameRate());
//        recorder.setVideoCodec(grabber.getVideoCodec());
//        recorder.setVideoBitrate(grabber.getVideoBitrate());
//        // 音频相关配置，取原音频配置
//        recorder.setSampleRate(grabber.getSampleRate());
//        recorder.setAudioCodec(grabber.getAudioCodec());
//
//
//
//        //处理字幕数组
//        //获得视频帧率
//        int fps=(int)grabber.getFrameRate()+1;
//        int kk=1000/fps;
//        System.out.println(arr.size());
//        for (int i=0;i<arr.size();i++)
//        {
//           int st=arr.get(i).start_t/kk;
//           int ed=arr.get(i).end_t/kk;
//           arr.get(i).start_t=st;
//           arr.get(i).end_t=ed;
//        }
//
//
//        recorder.start();
//        System.out.println("准备开始推流...");
//        Java2DFrameConverter converter = new Java2DFrameConverter();
//        Frame frame=null;
//        int i = 0,j = 0,end=0,st=0;
//        end=arr.get(0).end_t;
//        st=arr.get(0).start_t;
//        while ((frame = grabber.grab()) != null) {
//            // 从视频帧中获取图片
//            if (frame.image != null)
//            {
//
//                BufferedImage bufferedImage = converter.getBufferedImage(frame);
//
//                if(i>=st&&i<=end)
//                {
//                    // 对图片进行文本合入
//                    bufferedImage = addSubtitle(bufferedImage, arr.get(j).data);
//
//                }
//                // 视频帧赋值，写入输出流
//                recorder.record(converter.getFrame(bufferedImage));
//                i++;
//                if(i>=end)
//                {
//                    j++;
//                    end=arr.get(j).end_t;
//                    st=arr.get(j).start_t;
//                }
//            }
//
//            // 音频帧写入输出流
//            if(frame.samples != null) {
//                recorder.record(frame);
//            }
//        }
//        System.out.println("推流结束...");
//        grabber.stop();
//        recorder.stop();
    }
    /**
     * 视频预览处理
     * @param inputfile 输入视频
     * @param outputfile 输出视频
     * @param arr 字幕数组
     * @param size 字幕大小
     * @param font_type 字体类型
     * @return 返回编辑好的数据
     */
    /**
     * 视频预览处理
     * @param inputfile 输入视频
     * @param outputfile 输出视频
     * @param arr 字幕数组
     * @param size 字幕大小
     * @param font_type 字体类型
     * @return 返回编辑好的数据
     */

    public static void Draw_Sub(List<sub_base> arr,String inputfile,String outputfile,int size,String font_type)throws FrameGrabber.Exception, FrameRecorder.Exception
    {

        // 设置源视频、加字幕后的视频文件路径
        FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(inputfile);
        grabber.start();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputfile,
                grabber.getImageWidth(),grabber.getImageHeight(), grabber.getAudioChannels());

        // 视频相关配置，取原视频配置
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setVideoCodec(grabber.getVideoCodec());
        recorder.setVideoBitrate(grabber.getVideoBitrate());
        // 音频相关配置，取原音频配置
        recorder.setSampleRate(grabber.getSampleRate());
        recorder.setAudioCodec(grabber.getAudioCodec());

        //处理字幕数组
        //获得视频帧率
        int fps=(int)grabber.getFrameRate();
        int kk=1000/fps;
        System.out.println(arr.size());
        for (int i=0;i<arr.size();i++)
        {
            int st=arr.get(i).start_t/kk;
            int ed=arr.get(i).end_t/kk;
            arr.get(i).start_t=st;
            arr.get(i).end_t=ed;
        }


        recorder.start();
        System.out.println("准备开始推流...");
        Java2DFrameConverter converter = new Java2DFrameConverter();
        Frame frame=null;
        int i = 0,j = 0,end=0,st=0;
        end=arr.get(0).end_t;
        st=arr.get(0).start_t;
        while ((frame = grabber.grab()) != null) {
            // 从视频帧中获取图片
            if (frame.image != null)
            {

                BufferedImage bufferedImage = converter.getBufferedImage(frame);

                if(i>=st&&i<=end)
                {
                    // 对图片进行文本合入
                    bufferedImage = addSubtitle(bufferedImage, arr.get(j).data,arr.get(j).data2,size,font_type);

                }
                // 视频帧赋值，写入输出流
                recorder.record(converter.getFrame(bufferedImage));
                i++;
                if(i>=end)
                {
                        if(j+1<arr.size())
                        {
                            j++;
                            end = arr.get(j).end_t;
                            st = arr.get(j).start_t;
                        }

                }
            }

            // 音频帧写入输出流
            if(frame.samples != null) {
                recorder.record(frame);
            }
        }
        System.out.println("推流结束...");
        grabber.stop();
        recorder.stop();
    }
    /**
     * 图片添加文本
     *
     * @param bufImg 某一帧图片
     * @param subTitleContent1 第一字幕
     * @param subTitleContent2 第二字幕
     * @param size              文本大小
     * @return
     */
    private static BufferedImage addSubtitle(BufferedImage bufImg, String subTitleContent1,String subTitleContent2,int size,String font_type) {

        // 添加字幕时的时间
        Font font = new Font(font_type, Font.BOLD, size);
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        Graphics2D graphics = bufImg.createGraphics();
        //graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        //设置图片背景
        graphics.drawImage(bufImg, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);

        //消除文字锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //消除画图锯
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 计算文字长度，计算居中的x点坐标
        int widthX = (bufImg.getWidth() - metrics.stringWidth(subTitleContent1)) / 2;
        int widthX2= (bufImg.getWidth() - metrics.stringWidth(subTitleContent2)) / 2;
        graphics.setColor(Color.white);
        graphics.setFont(font);
        graphics.drawString(subTitleContent1, widthX, bufImg.getHeight() - size*2);
        graphics.drawString(subTitleContent2, widthX2, bufImg.getHeight() - size);
        graphics.dispose();
        return bufImg;
    }
}
