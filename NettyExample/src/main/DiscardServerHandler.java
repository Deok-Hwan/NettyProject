import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DiscardServerHandler extends SimpleChannelInboundHandler<Object>{
		@Override
		public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
			// channelRead() 메서드가 자동으로 실행된다.
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// TODO Auto-generated method stub
			cause.printStackTrace();
			ctx.close();
		}
	}