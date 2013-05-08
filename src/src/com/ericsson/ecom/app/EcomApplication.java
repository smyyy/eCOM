package com.ericsson.ecom.app;

//Android

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.ericsson.ecom.R;
import com.ericsson.ecom.ssh.SshSession;

import java.util.HashMap;

//Java

/**
 * DESCRIPTION
 *
 * @author eranibl
 * @date 2013-04-19, 10:50
 */
public class EcomApplication extends Application {
    private static final String SERVER = "server";
    private static final String PORT = "ssh_port";
    private static final String CLI_PORT = "cli_port";
    private static final String NC_PORT = "netconf_port";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static HashMap<String,SshSession>  SessionMap = new HashMap<String, SshSession>();


    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public String getServer() {
        return getPrefs().getString(SERVER, "eranibl");
    }

    public void setServer(String s) {
        save(SERVER, s);
    }

    public int getPort() {
        return Integer.parseInt(getPrefs().getString(PORT, "22"));
    }

    public String getPortCli() {
        return getPrefs().getString(CLI_PORT, "9830");
    }

    public String getPortNc() {
        return getPrefs().getString(NC_PORT, "830");
    }

    public void setPort(String s) {
        save(PORT, s);
    }

    public void setPortCli(String s) {
        save(CLI_PORT, s);
    }

    public void setPortNc(String s) {
        save(NC_PORT, s);
    }

    public String getUsername() {
        return getPrefs().getString(USERNAME, "");
    }

    public void setUsername(String s) {
        save(USERNAME, s);
    }

    public String getPassword() {
        return getPrefs().getString(PASSWORD, "");
    }

    public void setPassword(String s) {
        save(PASSWORD, s);
    }

    public SshSession getSession(String sessionName){
        return SessionMap.get(sessionName);
    }

    public void setSession(String sessionName, SshSession session ){
         SessionMap.put(sessionName, session);
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void save(String name, String value) {
        SharedPreferences.Editor edit = getPrefs().edit();
        edit.putString(name, value);
        edit.apply();
    }

}
