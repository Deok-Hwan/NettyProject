package netty.exam01;

import java.nio.ByteBuffer;
/**
 * 
 * 바이트 버퍼가 넘칠 경우 
 * nio.BufferOverflowException이 발생한다.
 *
 */
public class ByteBufferTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("바이트 버퍼 초깃값 : " + firstBuffer);
		
		byte[] source = "Hello World".getBytes();
		firstBuffer.put(source);
		System.out.println("11바이트 기록 후 " + firstBuffer);
	}

}
