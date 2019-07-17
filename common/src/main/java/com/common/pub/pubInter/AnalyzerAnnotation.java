package com.common.pub.pubInter;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnalyzerAnnotation {

    enum AnalyzerName{ngramAnalyzer, edgeNgramAnalyzer}

    AnalyzerName analyzerName() default  AnalyzerName.ngramAnalyzer;
}
