package org.share.odies.core.proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Invoker. (API/SPI, Prototype, ThreadSafe)
 *
 */
public interface Invoker  {
    Log logger = LogFactory.getLog(Invoker.class);
    Object invoke(Invocation invocation) throws Throwable;



}