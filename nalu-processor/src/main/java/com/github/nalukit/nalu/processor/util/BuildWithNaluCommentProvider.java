package com.github.nalukit.nalu.processor.util;

import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.squareup.javapoet.CodeBlock;

import java.util.Objects;

public class BuildWithNaluCommentProvider {
  
  private static BuildWithNaluCommentProvider instance;
  
  private BuildWithNaluCommentProvider() {
  }
  
  public static BuildWithNaluCommentProvider get() {
    if (Objects.isNull(instance)) {
      instance = new BuildWithNaluCommentProvider();
    }
    return instance;
  }
  
  public CodeBlock getGeneratedComment() {
    String sb = "Build with Nalu version >>" +
                ProcessorConstants.PROCESSOR_VERSION +
                "<< at >>" +
                ProcessorConstants.BUILD_TIME +
                "<<";
    return CodeBlock.builder()
                    .add(sb)
                    .build();
  }
  
}
