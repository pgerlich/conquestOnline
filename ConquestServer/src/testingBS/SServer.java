package testingBS;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;

public class SServer {
	
	public static void main(String args[]) throws IOException {
	    Server server = new Server();
	    server.start();
	    server.bind(54555, 54777);
	    
	    Kryo kryo = server.getKryo();
	    kryo.register(SomeRequest.class);
	    kryo.register(SomeResponse.class);
	    
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof SomeRequest) {
	              SomeRequest request = (SomeRequest)object;
	              System.out.println(request.text);

	              SomeResponse response = new SomeResponse();
	              response.text = "Thanks";
	              connection.sendTCP(response);
	           }
	        }
	     });
	}
	
	public static class SomeRequest {
	    public String text;
	 }
	 public static class SomeResponse {
	    public String text;
	 }

}


