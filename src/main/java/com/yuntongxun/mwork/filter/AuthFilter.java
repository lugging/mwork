package com.yuntongxun.mwork.filter;

import cn.hutool.crypto.SecureUtil;
import com.yuntongxun.mwork.config.MyConfigProperties;
import com.yuntongxun.mwork.constant.Constants;
import com.yuntongxun.mwork.filter.support.BaseBody;
import com.yuntongxun.mwork.filter.support.SignatureException;
import com.yuntongxun.mwork.util.JsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @program: mwork
 * @description: 鉴权过滤器
 * @author: liugang
 * @create: 2020-05-23 16:17
 **/
@Slf4j
public class AuthFilter implements Filter {

    private MyConfigProperties myConfigProperties;

    public AuthFilter(MyConfigProperties myConfigProperties){
        this.myConfigProperties = myConfigProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ( myConfigProperties.authConfig.isEnabled() ){
            AuthRequest authRequest = new AuthRequest((HttpServletRequest) request);
            chain.doFilter(authRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * 处理鉴权业务逻辑
     */
    class AuthRequest extends HttpServletRequestWrapper {

        private final InputStream delegate;

        /**
         * 时间戳
         */
        private String timeMillis;

        public AuthRequest(HttpServletRequest request) throws IOException {
            super(request);
            delegate = new ByteArrayInputStream(StreamUtils.copyToByteArray(request.getInputStream()));
            timeMillis = request.getHeader(Constants.AUTHORIZATION_HEADER_NAME_TIME_MILLIS);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
        }

        /**
         * 在使用@RequestBody注解的时候，其实框架是调用了getInputStream()方法，所以我们要重写这个方法
         * 此处 处理  验签，处理入参数据
         * @return
         * @throws IOException
         */
        @SneakyThrows
        @Override
        public ServletInputStream getInputStream() {
            return new BodyInputStream(process(delegate, timeMillis));
        }
    }

    private static class BodyInputStream extends ServletInputStream {

        private final InputStream delegate;

        public BodyInputStream(byte[] body) {
            this.delegate = new ByteArrayInputStream(body);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return this.delegate.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return this.delegate.read(b, off, len);
        }

        @Override
        public int read(byte[] b) throws IOException {
            return this.delegate.read(b);
        }

        @Override
        public long skip(long n) throws IOException {
            return this.delegate.skip(n);
        }

        @Override
        public int available() throws IOException {
            return this.delegate.available();
        }

        @Override
        public void close() throws IOException {
            this.delegate.close();
        }

        @Override
        public synchronized void mark(int readlimit) {
            this.delegate.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            this.delegate.reset();
        }

        @Override
        public boolean markSupported() {
            return this.delegate.markSupported();
        }
    }

    /**
     * 处理数据
     * TODO 修改加密规则，比如body报文 非对称解密等
     * 此处暂时使用md5签名，防止报文被篡改
     * @param is
     * @return
     */
    private byte[] process(InputStream is,  String timeMillis) throws SignatureException {
        BaseBody baseBody = JsonUtils.buildGson().fromJson(new BufferedReader(new InputStreamReader(is)), BaseBody.class);
        String body =  baseBody.getBody();
        String sign = baseBody.getSign();
        String calcSign = SecureUtil.md5(body + timeMillis);
        log.info("in {}, calc {}", sign, calcSign);
        if(StringUtils.isEmpty(sign) || StringUtils.isEmpty(calcSign) || !calcSign.equals(sign) ){
            throw new SignatureException(401);
        }
        return body.getBytes();
    }
}
