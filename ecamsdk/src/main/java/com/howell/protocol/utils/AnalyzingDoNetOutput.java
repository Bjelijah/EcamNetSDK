package com.howell.protocol.utils;







import com.howell.bean.soap.Device;

import java.util.ArrayList;

/**
 * soap string util
 */
public class AnalyzingDoNetOutput {
    private static int findPosition(String strAll, String str) {
        return strAll.indexOf(str);
    }

    private static boolean estimateBool(String bool) {
        if (bool.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Device> analyzing(String str) {
        int j = 0;
        String[] s = str.split(" \\}\\;");
        ArrayList<Device> list = new ArrayList<Device>();
        for (int i = 0; i < s.length - 1; i++) {
            String[] s2 = s[i].split("; ");
            String devID = s2[j].substring(findPosition(s2[j], "DevID=")
                    + "DevID=".length(), s2[j].length());
            int ChannelNo = Integer.parseInt(s2[j + 1].substring(
                    "ChannelNo=".length(), s2[j + 1].length()));
            String name = s2[j + 2].substring("Name=".length(),
                    s2[j + 2].length());
            String online = s2[j + 3].substring("OnLine=".length(),
                    s2[j + 3].length());
            String ptzFlag = s2[j + 4].substring("PtzFlag=".length(),
                    s2[j + 4].length());
            Device d = new Device(devID, ChannelNo, name, estimateBool(online),
                    estimateBool(ptzFlag));
            list.add(d);
        }
        return list;
    }

    public static String[] analyzingIPandPort(String str) {
        String[] s = str.split("\\; ");
        String[] ret = { s[7], s[8] };
        return ret;
    }
}
