package com.thebigbang.ftpclient;

import java.util.concurrent.TimeoutException;

/**
 * Created by thebigbang on 14/10/13.
 */
interface FTPTimeout {
    void Occurred(TimeoutException e);

}
