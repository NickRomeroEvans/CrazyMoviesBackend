package com.thunder.security;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.thunder.controller.User;

//@WebFilter(filterName= "jwt-filter", urlPatterns = { "/api/*" })
public class AuthenticationFilter implements Filter {
  private JWTVerifier jwtVerifier;

  @Autowired
  private UserManager userManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jwtVerifier = new JWTVerifier(
          new Base64(true).decode(AuthClient.CLIENTSECRET),
          AuthClient.CLIENTID);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	String token = getToken(httpRequest);

        try {
            Map<String, Object> decoded = jwtVerifier.verify(token);
            
            User user = new User(); //new User(decoded);
            
            if(decoded.containsKey("email")){
            	user.setEmail((String)decoded.get("email"));
            }
            
            if(decoded.containsKey("name")){
            	user.setName((String) decoded.get("name"));
            }
            
            if(decoded.containsKey("sub")){
            	user.setFacebookId(Long.parseLong(((String) decoded.get("sub")).split("\\|")[1]));
            }
            
            boolean isSignup = checkSignup(httpRequest.getServletPath());
            
            userManager.threadLocalTrack(user);
            //TODO: Check User
            if (!isSignup) {
               User validUser = userManager.validateUser(user);
               userManager.threadLocalTrack(validUser);
            }

            // Do something with decoded information like UserId
            chain.doFilter(request, response);
            
        } catch (Exception e) {
            throw new ServletException("Unauthorized: Token validation failed", e);
        } finally {
        	userManager.threadLocalTrack(null);
        }
    }

    private boolean checkSignup(String servletPath) {
		// TODO Auto-generated method stub
    	return servletPath.contains("signup") || servletPath.contains("loginFB");
	}

	private String getToken(HttpServletRequest httpRequest) throws ServletException {
      String token = null;
        final String authorizationHeader = httpRequest.getHeader("authorization");
        if (authorizationHeader == null) {
            throw new ServletException("Unauthorized: No Authorization header was found");
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2) {
            throw new ServletException("Unauthorized: Format is Authorization: Bearer [token]");
        }

        String scheme = parts[0];
        String credentials = parts[1];

        Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(scheme).matches()) {
            token = credentials;
        }
        return token;
    }

  @Override
  public void destroy() {

  }

}