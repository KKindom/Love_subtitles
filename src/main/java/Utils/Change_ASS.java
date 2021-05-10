package Utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Utils.Change_SRT.XF_ARR;
import static Utils.WebITS.MT;
public class Change_ASS {
    public static void main(String[] args) {


        ASSTo_ARR("E:\\桌面\\测试视频\\测试双语字幕.ass",1);
//        List<sub_base> data=new ArrayList<sub_base>();
//        try {
//            data=XF_ARR("C:\\\\au_result\\\\au_result.txt",1);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        try {
//            data=MT(data,"en","ru",2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Requset_listToAss("E:\\桌面\\测试双语字幕",data,2);

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
    /**
     * ass字幕文件转字幕数组
     * @param assfile_path 字幕文件地址
     * @param type 字幕类型 1为单语 2 为双语
     * @return 返回编辑好的数据
     */
    public static List<sub_base> ASSTo_ARR(String assfile_path, int type)
    {
        List<sub_base> srtList = new ArrayList<sub_base>();
        String header=null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    assfile_path),"UTF-8"));
            String readline = null;
            sub_base subBase=null;
            StringBuffer buffer = new StringBuffer();
            //获取头部
            while((readline = br.readLine())!=null)
            {
                if (readline.indexOf("Dialogue: 0")<0)
                    buffer.append(readline.toString()+"\\n");
                else
                    {
                    header=buffer.toString();
                    buffer.setLength(0);
                    break;
                }
            }
            System.out.println("头部完成！");
            //获取内容
            while (readline.indexOf("Dialogue: 0") >= 0)
            {
                    subBase= convert(readline);
                    srtList.add(subBase);
                    if((readline = br.readLine())==null)
                    break;
            }
            System.out.println("转换完成！");
            br.close();
        } catch (IOException e) {
            System.out.println("对不起没有字幕");
        }
        for (int i = 0; i < srtList.size(); i++) {
            System.out.println(srtList.get(i).getStart_t() + "="
                    + srtList.get(i).getEnd_t() + "\n"
                    + srtList.get(i).getData());
        }



//        File file=new File("E:\\桌面\\au_result");
//        if(!file.exists()){//如果文件夹不存在
//            file.mkdir();//创建文件夹
//        }
//        try{//异常处理
//            //如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
//            BufferedWriter bw=new BufferedWriter(new FileWriter("E:\\桌面\\au_result\\au_result_ass.txt"));
//            //bw.write(header);
//            bw.write(srtList.toString());//写入切片后文件路径
//            bw.close();//一定要关闭文件
//        }catch(IOException e){
//            e.printStackTrace();
//        }



        return srtList;
    }
    //将行中的时间戳转换成毫秒
    private static sub_base convert(String line)
    {
        if (line.indexOf("Dialogue: 0") >= 0)
        {
            String[] tmp = line.split(",");
            String t0 = conv(tmp[1]);
            String t1 = conv(tmp[2]);
            String []txt=tmp[9].split("\\\\N");
            String result=null;
            try {
                result= txt[1].replaceAll("\\{.*\\}", "");
            }
           catch (Exception e)
           {
               System.out.println(e);
           }
            return new sub_base(Integer.parseInt(t0),Integer.parseInt(t1),txt[0],result);
        }
        return  null;
    }
    //处理单个时间转换成毫秒 0:00:00.00-->毫秒
    private static String conv(String str) {
        String[] tmp = str.split("\\.");
        String times = tmp[0].trim();
        String ms = tmp[1].trim()+"0";
        if (ms.substring(0, 1).equals("0")) {
            if (ms.substring(1, 2).equals("0")) {
                if (ms.substring(2, 3).equals("0")) {
                    ms = "0";
                } else {
                    ms = ms.substring(2);
                }
            } else {
                ms = ms.substring(1);
            }
        }
        int w = Integer.parseInt(ms);
        String[] tm = times.split(":");
        String hh = tm[0];
        String mm = tm[1];
        String ss = tm[2];
        //处理小时
        if (hh.substring(0, 1).equals("0")) {
                hh = "0";
        }
        else {
            hh = hh.substring(0);
        }
        if (mm.substring(0, 1).equals("0")) {
            if (mm.substring(1, 2).equals("0")) {
                mm = "0";
            } else {
                mm = mm.substring(1);
            }
        }
        if (ss.substring(0, 1).equals("0")) {
            if (ss.substring(1, 2).equals("0")) {
                ss = "0";
            } else {
                ss = ss.substring(1);
            }
        }
        int h = Integer.parseInt(hh) * 60 * 60 * 1000;
        int m = Integer.parseInt(mm) * 60 * 1000;
        int s = Integer.parseInt(ss) * 1000;
        return String.valueOf(h + m + s + w);
    }
}