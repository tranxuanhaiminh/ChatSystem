/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatservice;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import networkconnection.BroadcastSender;

/**
 *
 * @author Minh
 */
public class Service {
    BroadcastSender bcast = new BroadcastSender();
    
    
    public void requestPseudos() {
    	try {
			bcast.broadcastToAllUsers("Request pseudos", InetAddress.getByName("255.255.255.255"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
