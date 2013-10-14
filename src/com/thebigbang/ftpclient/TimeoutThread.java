/*This file is part of FTPClientLib.
 *
 * FTPClientLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * CustomPages is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) Meï-Garino Jérémy
*/
package com.thebigbang.ftpclient;

import java.util.concurrent.TimeoutException;

/**
 *
 * @author Meï-Garino Jérémy
 * Created by thebigbang on 14/10/13.
 */
class TimeoutThread extends Thread{
    private int _timeout;
    private boolean stop=false;
    private FTPTimeout _listener;
    TimeoutThread(int timeOut)
    {
        _timeout=timeOut;

    }TimeoutThread(int timeOut,FTPTimeout listener)
    {
        _timeout=timeOut;
        _listener=listener;
    }
    @Override
    public void run(){
        int current=0;
        while (current<=_timeout)
        {
            if(stop){
                return;
            }
            try {
                this.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            current++;
        }
        if(_listener!=null)_listener.Occurred(new TimeoutException("The connection to the ftp server has TimeOut"));
    }
    public void Stop()
    {
        stop=true;
        super.stop();
    }
}
