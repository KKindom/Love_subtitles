package Utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Utils.Change_SRT.XF_ARR;
import static Utils.WebITS.MT;
public class Change_ASS {
    public static void main(String[] args) {


        List<sub_base> data=new ArrayList<sub_base>();
        try {
            data=XF_ARR("C:\\\\au_result\\\\au_result.txt",1);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        try {
            data=MT(data,"en","ru",2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Requset_listToAss("E:\\桌面\\测试双语字幕",data,2);

    }
    /**
     * 字幕数组转ass字幕文件
     * @param subBaseList 字幕数组
     * @param type 字幕类型 1为单语 2 为双语
     * @param filename 选择输出字幕位置
     * @return 返回编辑好的数据
     */
    public static void Requset_listToAss(String filename, List<sub_base> subBaseList,int type) {

        File assFile = new File(filename + ".ass");
        FileWriter fileWriter = null;
        Change_ASS todo = new Change_ASS();


        //写ass文件
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(assFile));
            out.write(todo.header());
            Iterator<sub_base> danmukulist_iter = subBaseList.iterator();
            String writeLine = null;
            while (danmukulist_iter.hasNext())
            {

                writeLine = todo.PhraseDialogue(danmukulist_iter.next(),type);
                if (writeLine != null) {
                    //单独写行
                    out.write(writeLine);
                }
            }
            out.flush();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ass文件头部
    public String header() {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd_hh_mm");

        final String scriptInfo = String.format("[Script Info]\n" +
                "Title: GNZ48_%s_kkindom\n" +
                "Original Script: 根据 视频音频自转换\n" +
                "ScriptType: v4.00+\n" +
                "Collisions: Normal\n" +
                "PlayResX: 1280\n" +
                "PlayResY: 720\n" +
                "Timer: 10.0000\n", formater.format(new Date()));

        final String DanmukuStyle = String.format("[V4+ Styles]\n" +
                "Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding\n" +
                "Style: Default,Microsoft YaHei,40,&H00FFFFFF,&H00FFFFFF,&H28533B3B,&H500E0A00,0,0,0,0,100.0,100.0,0.0,0.0,1,2.0,2.0,2,135,135,80,1\n" +
                "Style: Description,PingFang SC,48,&H0AB59BFF,&H000000FF,&H00424242,&H001C1C1C,-1,0,0,0,100.0,100.0,0.0,0.0,1,2.0,2.0,1,75,100,50,1");

        final String Events = String.format("[Events]\n" +
                "Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text\n" +
                "Dialogue: 2,0:00:00.00,0:00:8.00,Description,,0,0,0,,{\\\\fad(200,200)\\\\b1}压制: kkindom{\\\\b}\n" +
                "Dialogue: 2,0:00:00.00,0:00:8.00,Description,,0,0,0,,{\\\\fad(200,200)\\\\b1}视频源: 自提供{\\\\b}\n" +
                "Dialogue: 2,0:00:00.00,0:00:8.00,Description,,0,0,0,,{\\\\fad(200,200)\\\\b1}字幕来源: 讯飞接口{\\\\b}");

        StringBuilder header = new StringBuilder();

        header.append(scriptInfo);
        header.append('\n');
        header.append(DanmukuStyle);
        header.append('\n');
        header.append(Events);
        header.append('\n');

        return header.toString();

    }
    /**
     * 编辑字幕行数据
     * @param danmuku 字幕文件
     * @param type 字幕类型 1为单语 2 为双语
     * @return 返回编辑好的数据
     */
    public String PhraseDialogue(sub_base danmuku,int type) {

        String start;
        String end;
        String text,text2;

            //设置字幕时间戳相关参数
            text = danmuku.data+"\\N";
            text2=danmuku.data2;
            SimpleDateFormat outFormat = new SimpleDateFormat("H:mm:ss.ss");
            outFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            start = outFormat.format(danmuku.start_t);
            end = outFormat.format(danmuku.end_t);


            String line = type==1?
                    String.format("Dialogue: 0,%s.00,%s.00,Default,NTP,0,0,0,%s\n", start, end, text):
                    String.format("Dialogue: 0,%s.00,%s.00,Default,NTP,0,0,0,,%s %s\n",
                start, end, text,text2);
            return line;



    }


}