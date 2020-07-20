package com.sgm.login.config;

import com.alibaba.druid.util.StringUtils;
import com.sgm.login.service.UserService;
import com.sgm.login.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    protected Log log = LogFactory.getLog(getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String clientId = request.getParameter("clientId");
        String clientSecret = request.getParameter("clientSecret");
        log.info("userName:"+username);
        log.info("password:"+password);
        log.info("clientId:"+clientId);
        log.info("clientSecret"+clientSecret);
        //获取 ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId 不存在"+clientId);
            //判断  方言  是否一致
        }else if (!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配"+clientId);
        }
        //密码授权 模式, 组建 authentication
        TokenRequest tokenRequest = new TokenRequest(new HashMap<String,String>(),clientId,clientDetails.getScope(),"password");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        log.info("token:"+ JSONUtil.toJSON(token));
        //根据用户名和token先查询是否已经存在
        Integer count = userService.countLogin(username,token.getValue());
        if(null != count && count > 0){
            log.info("用户："+username+"-"+" token:"+token.getValue()+"已经存在");
        }else{
            //根据用户名称和tokenId保存 登录信息
            userService.saveLogin(username,token.getValue());
        }
        //登录次数 +1
        userService.addLogin(username);
        //将 authention 信息打包成json格式返回
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JSONUtil.toJSON(token));
        response.sendRedirect("/main.html?tokenId="+token.getValue());
    }
}
