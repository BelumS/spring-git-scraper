package com.belum.apitemplate.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bel-sahn on 7/30/19
 */
@Component
public class AuditFilter extends OncePerRequestFilter {
//region PROPERTIES
//endregion

//region CONSTRUCTORS
//endregion

//region GETTERS/SETTERS
//endregion

    //region HELPER METHODS
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
//endregion
}
