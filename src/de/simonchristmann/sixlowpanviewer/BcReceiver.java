package de.simonchristmann.sixlowpanviewer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;

public class BcReceiver extends Thread {
	private int port;
	private String iface;
	private MulticastSocket socket;
	private String message;
	private boolean active = true;
	private DatagramPacket packet;
	private byte[] data;
	
	void BcViewer() {
		//setDaemon(true);
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	public void setInterface(String iface) {
		this.iface = iface;
	}
	
	private void initServer() {
		try {
			// TODO Exception if port and interface not specified
			socket = new MulticastSocket(this.port);
			socket.setNetworkInterface(NetworkInterface.getByName(this.iface));
			socket.joinGroup(Inet6Address.getByName("ff02::1"));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Server could not be started. Port "+this.port+" might be already in use.");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.initServer();
		
		while(active){
			try {
				message = null;
				data = new byte[1024];
				packet = new DatagramPacket(data, 1024);
				
				socket.receive(packet);
				message = new String(packet.getData(),"UTF-8");
				message = message.trim();
				System.out.println(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		socket.close();
	}

	public void close() {
		System.out.print("Close receiver socket.. ");
		active = false;
		//System.out.println("closed");
	}
}
