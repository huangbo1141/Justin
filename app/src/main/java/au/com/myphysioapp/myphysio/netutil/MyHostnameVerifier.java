package au.com.myphysioapp.myphysio.netutil;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyHostnameVerifier  implements HostnameVerifier {
	
	@Override
	public boolean verify(String hostname, SSLSession session){
		return true;
	}
	
}
