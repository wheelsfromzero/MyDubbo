package com.v2null.dubbo.client;

import java.util.HashMap;

public class ExistsChannels {
    private static ExistsChannels instance = new ExistsChannels();

    private static HashMap<String, String> channelMsgMap;

    public static ExistsChannels getInstance()
    {
        return instance;
    }

    private ExistsChannels()
    {
        this.channelMsgMap = new HashMap<>();
    }

    public boolean ifChannelExists(String channel)
    {
        if(channelMsgMap.containsKey(channel))
            return true;
        return false;
    }

    public void setChannel(String channel, String message)
    {
        channelMsgMap.put(channel, message);
    }

    public String getMsg(String channel)
    {
        return channelMsgMap.get(channel);
    }

}
