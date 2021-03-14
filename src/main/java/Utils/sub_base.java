package Utils;

import lombok.Data;

/**
 * @program: untitled
 * @description: 字幕基类
 * @author: kkindom
 * @create: 2021-02-25 14:25
 **/
@Data
public class sub_base {
    public int start_t,end_t;
    public String data;
    public sub_base(int st,int et,String dt)
    {
        start_t=st;
        end_t=et;
        data=dt;
    }
}

