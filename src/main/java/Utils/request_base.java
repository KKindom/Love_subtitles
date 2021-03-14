package Utils;

import lombok.Data;

/**
 * @program: untitled
 * @description: 机器翻译返回类
 * @author: kkindom
 * @create: 2021-02-28 17:45
 **/
@Data
public class request_base
{
   String sid;
   String message;
   int code;
    base data;
    public String dst()
    {
        return this.data.result.trans_result.dst;
    }
};

@Data
class base{

    base_b result;
}
@Data
class base_b{

    String from;
    String to;
    base_a trans_result;
}
@Data
class base_a{
 String dst;
 String src;
}
