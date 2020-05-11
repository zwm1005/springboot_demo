package com.shsxt.tmall;

import com.alibaba.fastjson.JSON;
import com.shsxt.base.ResultInfo;
import com.shsxt.tmall.exception.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理
 */

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        ModelAndView mv = new ModelAndView();
        //设置默认错误信息
        mv.setViewName("error");
        mv.addObject("code",400);
        mv.addObject("msg","系统异常,请稍后再试...");

        //强转 避免出错
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            ResponseBody responseBody = hm.getMethod().getDeclaredAnnotation(ResponseBody.class);
            //判断是否为视图转发异常
            if(responseBody==null){
                //方法响应内容为视图
                if(e instanceof ParamsException){
                    ParamsException pe = (ParamsException) e;
                    mv.addObject("code",pe.getCode());
                    mv.addObject("msg",pe.getMsg());

                    //return mv;
                }

                return mv;
            }else{
                //方法响应为JSON
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统错误,请稍后再试...");
                if(e instanceof ParamsException){
                    ParamsException pe = (ParamsException) e;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.setCharacterEncoding("UTF-8");
                PrintWriter writer = null;
                try {
                    writer = httpServletResponse.getWriter();
                    writer.write(JSON.toJSONString(resultInfo));
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }finally{
                    if(writer!=null){
                        writer.close();
                    }
                }
                return null;
            }
        }else{
            return mv;
        }
    }
}
