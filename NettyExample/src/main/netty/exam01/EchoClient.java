package netty.exam01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public final class EchoClient {
	public static void main(String[] args) throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			// 서버 애플리케이션의 부트스트랩 설정과 다르게 이벤트 루프 그룹이 하나만 설정되었다. 
			// 클라이언트 애플리케이션은 서버와 달리 서버에 연결된 하나만 존재하기 때문에 이벤트 루프 그룹이 하나다.
			.channel(NioSocketChannel.class)
			// 클라이언트 애플리케이션이 생성하는 채널의 종류를 설정한다. 여기서는 NIO 소켓 채널인 NioSocketChannel 클래스를 설정했다.
			// 즉 서버에 연결된 클라이언트의 소켓 채널은 NIO로 동작하게 된다.
			.handler(new ChannelInitializer<SocketChannel>() {
				// 클라이언트 애플리케이션이므로 채널 파이프라인의 설정에 일반 소켓 채널 클래스인 SocketChannel을 설정한다.
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler());
					
				}
			});
			
			ChannelFuture f = b.connect("localhost",8888).sync();
			// ChannelFuture 객체의 sync 메서드는 ChannelFuture 객체의 요청이 완료 될 때까지 대기한다.
			// 단 요청이 실패하면 예외를 던진다. 즉 connect 메서드의 처리가 완료 될 때까지 다음 라인으로 진행하지 않는다.
			
			f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
	}
}
