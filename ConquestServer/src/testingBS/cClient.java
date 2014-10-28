package testingBS;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;

public class cClient {

	public static void main(String args[]) {
	    Client client = new Client();
	    new Thread(client).start();
	    try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Kryo kryo = client.getKryo();
	    kryo.register(SomeRequest.class);
	    kryo.register(SomeResponse.class);

	    SomeRequest request = new SomeRequest();
	    request.text = "Here is the request";
	    client.sendTCP(request);
	    
	    client.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof SomeResponse) {
	              SomeResponse response = (SomeResponse)object;
	              System.out.println(response.text);
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
