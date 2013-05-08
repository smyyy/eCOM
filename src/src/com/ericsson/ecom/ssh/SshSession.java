package com.ericsson.ecom.ssh;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ericsson.ecom.R;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSubsystem;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * User: eranibl
 * Date: 2013-04-30
 * Time: 15:38
 * To change this template use File | Preferences | File Templates.
 */
public class SshSession  {
    private static final String LOG = "eCOM - SshSession";
    private String server;
    private int port;
    private String user;
    private String password;
    private String subsystem;
    private String prompt;

    private Session session;
    private Channel channel;

    public SshSession(String server, int port, String user, String password){
        this.server = server;
        this.port=port;
        this.user=user;
        this.password=password;
        this.subsystem="shell";
        this.prompt="";
    }

    public SshSession(String server, String subsystem, String user, String password){
        this.server = server;
        this.port=22;
        this.subsystem=subsystem;
        this.user=user;
        this.password=password;
        this.prompt="";
    }


    public void login() {
        Log.d(LOG, "user: " + user);
        Log.d(LOG, "password: " + password);
        Log.d(LOG, "server: " + server);
        Log.d(LOG, "port: " + port);
        Log.d(LOG, "prompt: " + prompt);
        new LoginTask().execute(new LoginData(server, port, user, password));
    }

    public String executeCommand(String command) {
        String reply = new String();
        MessageData md =  new MessageData(command,reply);
        new ExecuteTask().execute(md);
        return md.getReply();

    }

    public void disconnect(){
        channel.disconnect();
        session.disconnect();
    }

    class LoginData implements Serializable {
        String server, usr, pwd;
        int port;

        LoginData(String server, int port, String usr, String pwd) {
            this.server = server;
            this.port = port;
            this.usr = usr;
            this.pwd = pwd;
        }
    }

    class LoginTask extends AsyncTask<LoginData, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            Log.i(LOG, subsystem + " starting");
        }

        @Override
        protected Boolean doInBackground(LoginData... params) {
            try {
                LoginData d = params[0];

                JSch jsch = new JSch();
                session=jsch.getSession(d.usr, d.server, d.port);
                session.setPassword(d.pwd);

                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");

                session.setConfig(config);
                session.connect();

                if (subsystem != "cli"){
                    channel=session.openChannel("subsystem");
                    ((ChannelSubsystem)channel).setSubsystem(subsystem);
                    ((ChannelSubsystem)channel).setPty(true);
                } else {
                    channel=session.openChannel("cli");
                }

                channel.setInputStream(null);
                channel.connect();
                Log.i(LOG, "LoginActivity succeeded to: " +  server );
                return true;
            } catch (Exception e) {
                Log.i(LOG, "LoginActivity failed: ", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean successful) {
            if (successful) {
                Log.i(LOG, subsystem + " started");
            } else {
                Log.i(LOG, subsystem + "failed to  start");
            }
        }
    }

    class MessageData implements Serializable {
        private String message;
        private String reply;

        MessageData(String message, String reply) {
            this.message = message;
            this.reply = reply;
        }

        public String getMessage(){
            return message;
        }

        public String getReply(){
            return reply;
        }

    }

    class ExecuteTask extends AsyncTask<MessageData, Void, String> {
          private StringBuffer response = new StringBuffer();
            private MessageData messageData;

        @Override
        protected void onPreExecute() {
            Log.i(LOG, "Executing command");
        }

        @Override
        protected String doInBackground(MessageData... params) {
            try {
                messageData = params[0];
                Log.i(LOG, "Request: " + messageData.getMessage());

                OutputStream out = channel.getOutputStream();
                out.write(messageData.getMessage().getBytes());
                out.flush();

                InputStream in = channel.getInputStream();
                InputStreamReader rin = new InputStreamReader(in);
                BufferedReader bin = new BufferedReader(rin);

                String res = "";
                byte array[];
                int num;
                do {
                    if ((num = in.available()) > 0) {
                        array = new byte[num];
                        num = in.read(array);
                        String ret = new String(array, 0, num);
                        res += ret;

                        if (in.available() == 0)
                            break;
                    }
                } while (true);


                return res;





//                int cnt = 0;
//                boolean t = true;

//                while(true){
//                    boolean ready=false;
//                    while((ready = bin.ready()) == true){
//                        Log.i(LOG, ready == true ? "ready": "not ready");
//                       if ((cnt = bin.read()) >= 0){
//                            Log.i(LOG, cnt >= 0 ? "positive": "negative");
//                            response.append((char) cnt);
//                            Log.i(LOG, "Received: " + (char) cnt);
//                        } else{
//                            return response.toString();
//                        }
//                        Log.i(LOG, "next");
//                    }
//                    Log.i(LOG, "outer next");
//                }
            } catch (Exception e) {
                Log.i(LOG, "Cli failed: ", e);
                return "";
            }
        }


        @Override
        protected void onPostExecute(String reply) {
            Log.i(LOG, "Received: " + reply);
            messageData.reply = reply;
        }
    }
}
