package netty.exam01;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 데이터의 수신이 이루어졌을 때 네티가 자동으로 호출하는 메서드다.
		// TODO Auto-generated method stub
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
		// 수신된 데이터를 가지고 있는 네티의 바이트 버퍼 객체로부터 문자열 데이터를 읽어온다.
		System.out.println("수신한 문자열 [" + readMessage + ']');
		// 수신만 문자열을 콘솔로 출력
		ctx.write(msg);
		// ctx 는 ChannelHandlerContext 인터페이스의 객체로서 채널 파이프라인에 대한 이벤트를 처리한다.
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// channelRead 이벤트의 처리가 완료 된 후 자동으로 수행되는 이벤트 메서드로서 채널 파이프라인에 저장된 버퍼를 전송하는
		// flush 메서드를 호출한다.
		ctx.flush();
	}
}
