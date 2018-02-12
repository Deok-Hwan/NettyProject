package netty.exam01;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// channelActive �̺�Ʈ�� ChannelInboundHandler�� ���ǵ� �̺�Ʈ�μ� ���� ä���� ���� Ȱ��ȭ �Ǿ��� �� ����ȴ�..
		// TODO Auto-generated method stub
		String sendMessage = "Hello, Netty!";
		
		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(sendMessage.getBytes());
		
		StringBuilder builder = new StringBuilder();
		builder.append("������ ���ڿ� [");
		builder.append(sendMessage);
		builder.append("]");
		
		System.out.println(builder.toString());
		ctx.writeAndFlush(messageBuffer);
		// wirteAndFlush �޼���� ���������� ������ ��ϰ� ������ �� ���� �޼��带 ȣ���Ѵ�. 
		// ù ��°�� ä�ο� �����͸� ����ϴ� write �޼���� �ι�°�� ä�ο� ��ϵ� �����͸� ������ �����ϴ� flush �޼����.
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// �����κ��� ���ŵ� �����Ͱ� ���� �� ȣ��Ǵ� ��Ƽ �̺�Ʈ �޼����.
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
		// �����κ��� ���ŵ� �����Ͱ� ����� msg ��ü���� ���ڿ� �����͸� �����Ѵ�.
		
		StringBuilder builder = new StringBuilder();
		builder.append("������ ���ڿ� [");
		builder.append(readMessage);
		builder.append("]");
		
		System.out.println(builder.toString());
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// ���ŵ� �����͸� ��� �о��� �� ȣ��Ǵ� �̺�Ʈ �޼����. channelRead �޼����� ������ �Ϸ�ǰ� ���� �ڵ����� ȣ��ȴ�.
		ctx.close();
		// ���ŵ� �����͸� ��� ���� �� ������ ����� ä���� �ݴ´�. ���� ������ �ۼ��� ä���� ������ �ǰ� Ŭ���̾�Ʈ ���α׷��� ���� �ȴ�.
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
}
