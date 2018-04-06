package netty.exam01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NonBlockingServer {
	private Map<SocketChannel,List<byte[]>> keepDataTrack = new HashMap<>();
	private ByteBuffer buffer = ByteBuffer.allocate(2*1024);
	
	private void startEchoServer(){
		try
		(
				//�ڹ� 1.7���� ���� ������ ������μ� try ����� ���� �� �Ұ�ȣ �ȿ��� ����� �ڿ��� �ڵ����� ������ �ش�.
				//���� try ����� ������������ ������ catch ������� �̵��ϴ��� �ڿ��� ���������� ���� �ȴ�.
				//�ڹ� 1.6 �������� �ݵ�� finally ������ �ۼ��ؼ� �ڿ��� �����ؾ� �ߴ�.
				Selector selector = Selector.open();
				// �ڹ� NIO ������Ʈ ���� �ϳ��� Selector�� �ڽſ��� ��ϵ� ä�ο� ���� ������ �߻��ߴ��� �˻��ϰ�, ���� ������
				// �߻��� ä�ο� ���� ������ �����ϰ� ���ش�.
				ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
				// ���ŷ ������ ServerSocket�� �����Ǵ� ����ŷ ������ ���� ���� ä���� �����Ѵ�.
				// ���ŷ ���ϰ� �ٸ��� ���� ä���� ���� ����ϰ� ����� ��Ʈ�� ���ε� �Ѵ�.
		){
			if((serverSocketChannel.isOpen()) && (selector.isOpen())){
				// ������ Selector �� ServerSocketChannel ��ü�� ���������� �����Ǿ����� Ȯ���Ѵ�.
				serverSocketChannel.configureBlocking(false);
				// ���� ä���� ���ŷ ����� �⺻���� tre��. ������ ����ŷ ���� �������� ������ ���ŷ ���� �����Ѵ�.
				serverSocketChannel.bind(new InetSocketAddress(8888));
				// Ŭ���̾�Ʈ�� ������ ����� ��Ʈ�� �����ϰ�, ������ ServerSocketChannel ��ü�� �Ҵ��Ѵ�.
				
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
				// ServerSocketChannel ��ü�� Selector ��ü�� ����Ѵ�. Selector�� ������ �̺�Ʈ�� ���� ��û�� �ش��ϴ�
				// SelectionKey.OP_ACCEPT �̴�.
				System.out.println("���� �����");
				
				while(true){
					selector.select();
					// Selectior�� ��ϵ� ä�ο��� ���� ������ �߻��ߴ��� �˻��Ѵ�. Selector�� �ƹ��� I/O �̺�Ʈ�� �߻�����
					// ������ ������� �� �κп��� ���ŷ�� �ȴ�.
					// I/O �̺�Ʈ�� �߻����� �ʾ��� �� ���ŷ�� �ǰ��ϰ� �ʹٸ� selectNow �޼��带 ����ϸ� �ȴ�.
					Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
					
					while(keys.hasNext()){
						// Selector �� ��ϵ� ä�� �߿��� I/O �̺�Ʈ�� �߻��� ä�ε��� ����� ��ȸ�Ѵ�.
						SelectionKey key = (SelectionKey) keys.next();
						
						keys.remove();
						// I/O �̺�Ʈ�� �߻��� ä�ο��� ������ �̺�Ʈ�� �����Ǵ� ���� �����ϱ� ���Ͽ� ��ȸ�� ��Ͽ��� �����Ѵ�.
						if(!key.isValid()){
							continue;
						}
						
						if(key.isAcceptable()){
							// ��ȸ�� I/O �̺�Ʈ�� ������ ���� ��û���� Ȯ���Ѵ�. ���� ���� ��û �̺�Ʈ��� ���� ó�� �޼���� �̵��Ѵ�.
							this.acceptOP(key, selector);
						}
						else if(key.isReadable()){
							// ��ȸ�� I/O �̺�Ʈ�� ������ ������ �������� Ȯ���Ѵ�. 
							this.readOP(key);
						}
						else if(key.isWritable()){
							// ��ȸ�� I/O �̺�Ʈ�� ������ ������ �������� Ȯ���Ѵ�.
							this.writeOP(key);
						}
						else{
							System.out.println("���� ������ �������� ���߽��ϴ�.");
						}
					}
				}
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	}
	
	private void acceptOP(SelectionKey key, Selector selector)throws IOException{
		// ���� ��û �̺�Ʈ�� �߻��� ä���� �׻� ServerSocketChannel �̹Ƿ� �̺�Ʈ�� �߻��� ä���� ServerSocketChannel�� ĳ�����Ѵ�.
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
		// ServerSocketChannel�� ����Ͽ� Ŭ���̾�Ʈ�� ������ �����ϰ� ����� ���� ä���� �����´�.
		SocketChannel socketChannel = serverSocketChannel.accept();
		// Ŭ���̾�Ʈ ���� ä���� ����ŷ ���� �����Ѵ�.
		socketChannel.configureBlocking(false);
		
		System.out.println("Ŭ���̾�Ʈ ����� : " + socketChannel.getRemoteAddress());
		
		keepDataTrack.put(socketChannel, new ArrayList<byte[]>());
		// Ŭ���̾�Ʈ ���� ä���� Selector�� ����Ͽ� I/O �̺�Ʈ�� �����Ѵ�.
		socketChannel.register(selector, SelectionKey.OP_READ);
	}
	
	private void readOP(SelectionKey Key){
		try{
			SocketChannel socketChannel = (SocketChannel) Key.channel();
			buffer.clear();
			
			int numRead = -1;
			try{
				numRead = socketChannel.read(buffer);
			}catch(IOException e){
				System.err.println("������ �б� ���� !");
			}
			
			if(numRead == -1){
				this.keepDataTrack.remove(socketChannel);
				System.out.println("Ŭ���̾�Ʈ ���� ���� : "+ socketChannel.getRemoteAddress());
				socketChannel.close();
				Key.cancel();
				return;
			}
			byte [] data = new byte[numRead];
			System.arraycopy(buffer.array(), 0, data, 0, numRead);
			System.out.println(new String(data, "UTF-8") + " from " + socketChannel.getRemoteAddress());
			
			doEchoJob(Key,data);
		}catch(IOException ex){
			System.err.println(ex);
		}
	}
	
	private void writeOP(SelectionKey key) throws IOException 
	{
		SocketChannel socketChannel = (SocketChannel)key.channel();
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		Iterator<byte[]> its = channelData.iterator();
		
		while (its.hasNext()){
			byte[] it = its.next();
			its.remove();
			socketChannel.write(ByteBuffer.wrap(it));
		}
		key.interestOps(SelectionKey.OP_READ);
	}
	
	private void doEchoJob(SelectionKey key, byte[] data) {
		SocketChannel socketChannel = (SocketChannel)key.channel();
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		channelData.add(data);
		
		key.interestOps(SelectionKey.OP_WRITE);
	}
	
	public static void main(String[] args) {
		NonBlockingServer main = new NonBlockingServer();
		main.startEchoServer();
	}
}
