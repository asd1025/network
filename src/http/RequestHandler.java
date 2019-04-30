package http;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

 
public class RequestHandler extends Thread {
	private static final String DOCUENT_ROOT="./webapp";
	private Socket socket;
	
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	} 
	
	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );
			
			// get IOStream
			OutputStream outputStream = socket.getOutputStream();
			BufferedReader br=new BufferedReader(new  InputStreamReader(socket.getInputStream(),"utf-8"));
			String request=null;
			while(true) {
				String line=br.readLine();
				//브라우저가 연결을 끊으면..
				if(line==null) break;
				
				// Header의 첫번쨰 라인만 처리
				if(request==null) {
					request=line;
					System.out.println("@@@@@@ "+request);
				}
				// Request Header만 읽음
				if("".equals(line)) break;
				
			}
 			String[] tokens = request.split(" ");
			if("GET".contentEquals(tokens[0])) {
				consoleLog("request: "+tokens[1]);
				responseStaticResource(outputStream,tokens[1],tokens[2]);
			}else {  // POST , PUT ,  DELETE , HEAD , CONNECT
					// 와 같은 Method는 무시
				consoleLog("Bad Request : "+tokens[1]);
				
			}
			
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
//			outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
//			outputStream.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );
//			outputStream.write( "\r\n".getBytes() );
//			outputStream.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );

		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	public void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException {
		if("/".equals(url)) {
			url="/index.html";
		}
//		try {
//			FileInputStream fs=new FileInputStream(DOCUENT_ROOT+url);
//		} catch (FileNotFoundException e) {// -> 메모리에 객체 정보가 많이 추가 되어있어서 좋은 로직은 아님. (어디서 에러났고 무슨 에러며 등등)
//			e.printStackTrace();
//		}
		File file=new File(DOCUENT_ROOT+url);
		if(file.exists()==false) {
			/* 응답예시
			 * HTTP/1.1 404 FILE NOT FOUND\r\n (\r: 캐리지리턴. 커서가 맨 앞으로/ \n : 아래줄로 )
			 * Content-Type:text/html; charset=utf-8\r\n
			 */
			  
//				outputStream.write(("HTTP/1.1 404 FILE NOT FOUND\r\n").getBytes("UTF-8"));
//				outputStream.write(("Content-Type:text/html; charset=utf-8\r\n").getBytes("UTF-8"));
//				outputStream.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );
				response404Error(outputStream,protocol);
				return;
		}
		
		
		//nio
		byte[] body=Files.readAllBytes(file.toPath());
		String contentType=Files.probeContentType(file.toPath());
		
		System.out.println(file.toPath());
		//응답
		outputStream.write( (protocol+" 200 OK\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ("Content-Type:"+contentType+ "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		//outputStream.write( ("link rel='icon' href='./favicon.ico'").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes() );
		outputStream.write( body);

		
	}

	public void  response404Error(OutputStream outputStream, String protocol) {
		try {
			 
			File file=new File(DOCUENT_ROOT+"/error/404.html");
			byte[] body=Files.readAllBytes(file.toPath());
			String contentType=Files.probeContentType(file.toPath());
			outputStream.write( (protocol+" 404 FILE NOT FORUND\r\n").getBytes( "UTF-8" ) );
			outputStream.write( ("Content-Type:"+contentType+ "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
			outputStream.write( "\r\n".getBytes() );
			outputStream.write( body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
}
