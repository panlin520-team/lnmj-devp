package com.lnmj.common.baseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/5/31 17:49
 * @Description:
 */
public class HttpServletRequestWarpper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();
    @SuppressWarnings("unchecked")
    public HttpServletRequestWarpper(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }
    public HttpServletRequestWarpper(HttpServletRequest request,Map<String, Object[]> otherParams) {
        this(request);
        addAllParameters(otherParams);
    }
    @Override
    public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
        String[] values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    public String[] getParameterValues(String name) {//同上
        return params.get(name);
    }

    /**
     * 增加多个参数
     *
     * @param otherParams 增加的多个参数
     */
    public void addAllParameters(Map<String, Object[]> otherParams) {
        for (Map.Entry<String, Object[]> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }
    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            if(value instanceof String[][]){
                String[] str = new String[((String[][]) value).length];
                for(int i=0;i<((String[][]) value).length;i++){
                    str[i] = "";
                }
                for(int i=0;i<((String[][]) value).length;i++){
                    int j = 0;
                    while(j<(((String[][])value)[i]).length){
                        str[i] += ((String[][])value)[i][j]+",";
                        j++;
                    }
                }
                params.put(name,str);
            }else if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }
}
