package com.mh.servlet;

import com.mh.util.SignUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MainServlet extends HttpServlet {

    public static Logger log = null;

    public void init() throws ServletException {
        super.init();
        String path = getServletContext().getRealPath("/");
        String log4jpropfile = path + getInitParameter("log4j");
        if (log4jpropfile != null) {
            PropertyConfigurator.configure(log4jpropfile);
        }
        log = Logger.getLogger(String.valueOf(MainServlet.class));
        log.info("log加载成功了");
    }

    /**
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();

        /*log.info("signature====" + signature);
        log.info("timestamp====" + timestamp);
        log.info("nonce====" + nonce);
        log.info("echostr====" + echostr);
*/
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }

        out.close();
        out = null;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO 消息的接收、处理、响应
    }


}
