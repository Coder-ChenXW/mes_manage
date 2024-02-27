package org.chenxw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuzLoggerFactory {
  public static CuzLogger getLogger(Class<?> clazz){
    Logger logger = LoggerFactory.getLogger(clazz);
    return new CuzLogger(logger);
  }
}

