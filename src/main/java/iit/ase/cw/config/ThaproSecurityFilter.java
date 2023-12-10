/*
 * ====================================================================
 * Copyright  (c) : 2021 by Kaleris. All rights reserved.
 * ====================================================================
 *
 * The copyright to the computer software herein is the property of Kaleris
 * The software may be used and/or copied only
 * with the written permission of Kaleris or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package iit.ase.cw.config;

import iit.ase.cw.platform.common.context.model.ThaproApplicationContext;
import iit.ase.cw.platform.common.security.constant.ThaproSecurityConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class ThaproSecurityFilter extends GenericFilterBean {

    private ThaproJwtTokenHandler jwtTokenHandler;

    public ThaproSecurityFilter(ThaproJwtTokenHandler jwtTokenHandler) {
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = httpServletRequest.getHeader(ThaproSecurityConstant.Header.THAPRO_AUTHENTICATED_HEADER);
        if (StringUtils.hasText(jwt)) {
            //if (StringUtils.hasText(jwt) && this.jwtTokenHandler.validateToken(jwt)) {
            ThaproAuthentication authentication = this.jwtTokenHandler.getAuthentication(jwt);
            ThaproApplicationContext.configureContextUser(authentication.getThaproUser());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ThaproApplicationContext.clearContext();
        }
    }
}
