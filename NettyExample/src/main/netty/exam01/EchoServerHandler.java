package netty.exam01;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// �������� ������ �̷������ �� ��Ƽ�� �ڵ����� ȣ���ϴ� �޼����.
		// TODO Auto-generated method stub
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
		// ���ŵ� �����͸� ������ �ִ� ��Ƽ�� ����Ʈ ���� ��ü�κ��� ���ڿ� �����͸� �о�´�.
		System.out.println("������ ���ڿ� [" + readMessage + ']');
		// ���Ÿ� ���ڿ��� �ַܼ� ���
		ctx.write(msg);
		// ctx �� ChannelHandlerContext �������̽��� ��ü�μ� ä�� ���������ο� ���� �̺�Ʈ�� ó���Ѵ�.
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// channelRead �̺�Ʈ�� ó���� �Ϸ� �� �� �ڵ����� ����Ǵ� �̺�Ʈ �޼���μ� ä�� ���������ο� ����� ���۸� �����ϴ�
		// flush �޼��带 ȣ���Ѵ�.
		ctx.flush();
	}
}
