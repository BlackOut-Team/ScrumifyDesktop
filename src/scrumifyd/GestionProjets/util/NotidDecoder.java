/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.util;

import com.google.gson.Gson;
import javax.mail.Message;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Amira Doghri
 */
public class NotidDecoder implements Decoder.Text<Message> {
    private static Gson gson = new Gson();
 
    @Override
    public Message decode(String s) throws DecodeException {
        return gson.fromJson(s, Message.class);
    }
 
    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }
 
    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }
 
    @Override
    public void destroy() {
        // Close resources
    }
    
}
