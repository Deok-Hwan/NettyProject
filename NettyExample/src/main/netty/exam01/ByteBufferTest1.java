package netty.exam01;

import java.nio.ByteBuffer;
/**
 * 
 * ����Ʈ ���۰� ��ĥ ��� 
 * nio.BufferOverflowException�� �߻��Ѵ�.
 *
 */
public class ByteBufferTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("����Ʈ ���� �ʱ갪 : " + firstBuffer);
		
		byte[] source = "Hello World".getBytes();
		firstBuffer.put(source);
		System.out.println("11����Ʈ ��� �� " + firstBuffer);
	}

}
