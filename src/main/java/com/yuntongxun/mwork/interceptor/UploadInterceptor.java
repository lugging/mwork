package com.yuntongxun.mwork.interceptor;

import com.yuntongxun.mwork.config.InterceptorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liugang
 */
@Slf4j
@Component
public class UploadInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private InterceptorProperties interceptorProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ( interceptorProperties.getUpload().isEnabled() ){
            // 白名单
            List<String> suffixList = interceptorProperties.getUpload().getAllowFileExtensions();
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> files = multipartRequest.getFileMap();
                int size = files.entrySet().stream().filter( stringMultipartFileEntry ->
                        checkFile(stringMultipartFileEntry.getKey(), suffixList)
                ).collect(Collectors.toList()).size();
                return size > 0 ? true : false;
            }
        }
        return true;
    }

    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName,List<String> list) {
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        return list.contains(suffix);
    }
}