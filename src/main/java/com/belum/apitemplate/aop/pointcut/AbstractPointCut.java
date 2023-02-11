package com.belum.apitemplate.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by bel-sahn on 7/29/19
 */
public abstract class AbstractPointCut {

//region HELPER METHODS
    /** Packages to exclude from AOP aspects */
    @Pointcut("!within(com.belum.apitemplate.*.filters..*)")
    public void excludePackages(){}

    /** Classes to exclude from AOP aspects */
    @Pointcut("!within(com.belum.apitemplate.*.utils)")
    public void excludeClasses(){}

    /** Components to include in AOP aspects */
    @Pointcut("within(com.belum.apitemplate.*..*) && excludeClasses() && excludePackages()")
    public void allComponents() {}
//endregion
}
