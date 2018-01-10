package com.example.chen.tools;

import android.os.Handler;
import com.example.chen.http.*;

/**
 * Created by laixl on 2017/12/27.
 */

public class ConnectServer {

     public static void Connect(final String path, final Object content,final Handler mHanlder){

         new Thread() {
             public void run() {
                 String response = HttpUtil.doPostRequest(path, content);
                 android.os.Message message = android.os.Message.obtain();
                 message.obj = response;
                 mHanlder.sendMessage(message);
             }
         }.start();
   }
}
