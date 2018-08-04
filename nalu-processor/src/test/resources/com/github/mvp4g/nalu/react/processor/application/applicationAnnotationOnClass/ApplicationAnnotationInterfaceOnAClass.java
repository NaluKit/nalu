package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOnClass;

import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;

@Application(eventBus = MockEventBus.class)
public class ApplicationAnnotationInterfaceOnAClass
  implements IsApplication {
}