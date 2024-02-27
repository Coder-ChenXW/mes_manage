package org.chenxw.utils;

import org.slf4j.Logger;

public class CuzLogger extends CuzLoggerDecorator{

    public CuzLogger(Logger logger) {
        super(logger);
    }

    @Override
    public void debug(String msg) {
        super.logger.debug(msg + "【cao!】");
    }

    @Override
    public void debug(String format, Object... arguments) {
        super.logger.debug(format + "【cao!】", arguments);
    }

    @Override
    public void info(String msg) {
        super.logger.info(msg + "【cao!】");
    }

    @Override
    public void info(String format, Object arg) {
        super.logger.info(format + "【cao!】", arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        super.logger.info(format + "【cao!】", arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        super.logger.info(format + "【cao!】", arguments);
    }

    @Override
    public void warn(String msg) {
        super.logger.warn(msg + "【cao!】");
    }

    @Override
    public void warn(String format, Object... arguments) {
        super.logger.warn(format + "【cao!】", arguments);
    }

    @Override
    public void error(String msg) {
        super.logger.error(msg + "【cao!】");
    }

    @Override
    public void error(String format, Object... arguments) {
        super.logger.error(format + "【cao!】", arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        super.logger.error(msg + "【cao!】", t);
    }
}
