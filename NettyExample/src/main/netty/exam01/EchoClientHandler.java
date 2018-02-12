package netty.exam01;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// channelActive 이벤트는 ChannelInboundHandler에 정의된 이벤트로서 소켓 채널이 최초 활성화 되었을 때 실행된다..
		// TODO Auto-generated method stub
		String sendMessage = "Hello, Netty!";
		
		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(sendMessage.getBytes());
		
		StringBuilder builder = new StringBuilder();
		builder.append("전송한 문자열 [");
		builder.append(sendMessage);
		builder.append("]");
		
		System.out.println(builder.toString());
		ctx.writeAndFlush(messageBuffer);
		// wirteAndFlush 메서드는 내부적으로 데이터 기록과 전송의 두 가지 메서드를 호출한다. 
		// 첫 번째는 채널에 데이터를 기록하는 write 메서드며 두번째는 채널에 기록된 데이터를 서버로 전송하는 flush 메서드다.
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 서버로부터 수신된 데이터가 있을 때 호출되는 네티 이벤트 메서드다.
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
		// 서버로부터 수신된 데이터가 저장된 msg 객체에서 문자열 데이터를 추출한다.
		
		StringBuilder builder = new StringBuilder();
		builder.append("수신한 문자열 [");
		builder.append(readMessage);
		builder.append("]");
		
		System.out.println(builder.toString());
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 수신된 데이터를 모두 읽었을 때 호출되는 이벤트 메서드다. channelRead 메서드의 수행이 완료되고 나서 자동으로 호출된다.
		ctx.close();
		// 수신된 데이터를 모두 읽은 후 서버와 연결된 채널을 닫는다. 이후 데이터 송수신 채널은 닫히게 되고 클라이언트 프로그램은 종료 된다.
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
}
