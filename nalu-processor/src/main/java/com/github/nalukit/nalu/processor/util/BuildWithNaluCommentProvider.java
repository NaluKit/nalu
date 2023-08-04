package com.github.nalukit.nalu.processor.util;

import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.squareup.javapoet.CodeBlock;

public class BuildWithNaluCommentProvider {

  public static BuildWithNaluCommentProvider INSTANCE = new BuildWithNaluCommentProvider();

  private BuildWithNaluCommentProvider() {
  }

  public CodeBlock getGeneratedComment() {
    String sb = "Build with Nalu version >>" + ProcessorConstants.PROCESSOR_VERSION + "<< at " + ProcessorConstants.BUILD_TIME;
    return CodeBlock.builder()
                    .add(sb)
                    .build();
  }

}
