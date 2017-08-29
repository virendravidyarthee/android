package com.virendra.mpd.meshcom.BackgroudTasks;

import org.json.JSONException;
import org.json.JSONObject;


public class Utility {
    public static final int FILE = 1;
    public static final int MESSAGE = 2;
    public static final String IMAGE = "image";

    public static String createMessage(String name, String message, int type, int size, String mediaType) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (type == 1) {
                jsonObject.put("sendername", name);
                jsonObject.put("filetype", "file");
                jsonObject.put("mediatype", mediaType);
                jsonObject.put("message", message);
                jsonObject.put("size", size);

                return jsonObject.toString();

            } else if (type == 2) {
                jsonObject.put("sendername", name);
                jsonObject.put("filetype", "message");
                jsonObject.put("mediatype", mediaType); //was "text" instead of mediaType
                jsonObject.put("message", message);
                jsonObject.put("size", size);

                return jsonObject.toString();
            }
        } catch (JSONException e){}
        return null;
    }


    public static String[] parseMessage(String message) {
        try{
            String response[] = new String[6];
            JSONObject jsonObject = new JSONObject(message);

            response[0] = jsonObject.getString("sendername");
            response[1] = jsonObject.getString("filetype");
            response[2] = jsonObject.getString("mediatype");
            response[3] = jsonObject.getString("message");
            response[4] = jsonObject.getString("size");

            return response;

        } catch (JSONException | ArrayIndexOutOfBoundsException e){}
        return null;
    }



}
