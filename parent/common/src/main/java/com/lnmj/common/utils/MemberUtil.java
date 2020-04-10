package com.lnmj.common.utils;

import com.lnmj.common.Enum.base.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/4/22 16:20
 * @Description:
 */
public class MemberUtil {
  /*  //dataInput转换到Map
    public Map dataInputToMap(IDataInput dataInput){
        HttpServletRequest request = dataInput.getRequest();
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
            System.out.println(name+":"+value);
        }
        return returnMap;
    }*/

    //参数：当前要获取的状态code，以及你枚举类
    public static <T extends BaseEnum> T getEnum(Integer code , Class<T> enumStatus){
        //循环遍历你的枚举类中的所有code值，相等则返回给调用者
        for (T each:enumStatus.getEnumConstants()) {
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
    //获取枚举类中的所有数据（code,desc）
    public static <T extends BaseEnum> Map<Integer, String> getEnumToMap(Class<T> baseEnum ){
        Map<Integer,String> map = new HashMap<>();
        for(T each:baseEnum.getEnumConstants()){
            map.put(each.getCode(),each.getDesc());
        }
        return map;
    }

//        Date currentDate = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formatCurrentDate = sdf.format(currentDate);
}
