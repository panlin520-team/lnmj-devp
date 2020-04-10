package com.lnmj.common.utils;

import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/5/28 13:50
 * @Description:
 */
public class ProductCheckUtil {
    public static ResponseResult ProductCheck(@Validated Object object, BindingResult bindingResult) {
        int count = bindingResult.getErrorCount();
        HashMap<Object, Object> map = new HashMap<>();
        if (count > 0) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < count; i++) {
                FieldError error = fieldErrors.get(i);
                map.put("errorField"/*error.getField()*/, error.getDefaultMessage());
            }
        }
        return ResponseResult.error(new Error(1, map.get("errorField")));
    }
}
