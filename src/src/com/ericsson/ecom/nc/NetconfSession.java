package com.ericsson.ecom.nc;

import android.util.Log;
import com.ericsson.ecom.mo.Mo;
import com.ericsson.ecom.mo.MoAttribute;
import com.ericsson.ecom.ssh.SshSession;

import java.util.ArrayList;
import java.util.List;

public class NetconfSession {
    private static final String LOG = "eCOM - Netconf";

    private static final String SSH_SUBSYSTEM = "netconf";   // use this for COM netconf

    public static final String ECIM_NS = "urn:com:ericsson:ecim:";

    public static final String XML_START = "\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    public static final String NETCONF_MESSAGE_END = "]]>]]>\n";
    public static final String NETCONF_HELLO_MESSAGE = XML_START + "<hello xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n"
            + "<capabilities>\n<capability>urn:ietf:params:netconf:base:1.0</capability>\n</capabilities>\n"
            + "</hello>\n" + NETCONF_MESSAGE_END;
    public static final String NETCONF_GET_MESSAGE = XML_START + "<rpc message-id=\"1\" xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n"
            + "<get/>\n" + "</rpc>\n" + NETCONF_MESSAGE_END;
    public static final String NETCONF_CLOSE_MESSAGE = XML_START + "<rpc message-id=\"999\" xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n"
            + "<close-session/>\n" + "</rpc>\n" + NETCONF_MESSAGE_END;
    public static final String NETCONF_CLOSE_REPLY = XML_START + "<rpc-reply xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\" message-id=\"999\">\n"
            + "<ok/>\n" + "</rpc-reply>\n" + NETCONF_MESSAGE_END;

    private static final String RPC_REPLY_TAG_NAME = "rpc-reply";
    public static final String DEFAULT_END = "]]>]]>";
    public static final int DEFAULT_TERMINAL_WIDTH = 0;


    private SshSession sshSession;

    public NetconfSession(String server, int port, String user, String password){
        Log.d(LOG, "LOGIN: " + server + ":" + port);
        sshSession = new SshSession(server,SSH_SUBSYSTEM ,user,password);
    }

    private boolean sendHello(){
        String msgOut =  sshSession.executeCommand(NETCONF_HELLO_MESSAGE);

        return true;
    }

    private boolean sendClose(){
        String msgOut =  sshSession.executeCommand(NETCONF_HELLO_MESSAGE);
        return true;
    }

    public ArrayList<Mo> getMo(Mo mo){

        String msgIn = XML_START
                + "<rpc message-id=\"1\" xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n"
                + "<get/>\n" + "</rpc>" + NETCONF_MESSAGE_END;


        String msgOut =  sshSession.executeCommand(msgIn);
        ArrayList<Mo> mos = new ArrayList<Mo>();


        return mos;

    }


    public ArrayList<MoAttribute> getAttributes(Mo mo){

        String msgIn = XML_START
                + "<rpc message-id=\"1\" xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n"
                + "<get/>\n" + "</rpc>" + NETCONF_MESSAGE_END;

        ArrayList<MoAttribute> attributes = new ArrayList<MoAttribute>();

        return attributes;

    }
}
