package netty.exam01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); 
		// NioEventLoopGroup 클래스 생성자의 인수로 사용된 숫자는 스레드 그룹 내에서 생성할 최대 스레드 수를 의미한다.
		// 1을 설정하였으므로 부모 스레드 그룹은 단일 스레드로 동작한다.
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		// NioEventLoopGroup 클래스의 인수 없는 생성자를 호출했다. 
		// 인수 없는 생성자는 사용할 스레드 수를 서버 애플리케이션이 동작하는 하드웨어 코어 수를 기준으로 결정한다.
		try {
			ServerBootstrap b = new ServerBootstrap();
			// 인수 없는 생성자로 ServerBootStrap 객체 생성
			b.group(bossGroup, workerGroup)
			// group, Channel과 같은 메서드로 객체를 초기화
			// b.group ServerBootStrap 객체에 서버 어플리케이션이 사용할 두 스레드 그룹을 설정
			// 첫번째 인수는 부모 스레드이다. 부모 스레드는 클라이언트 연결 요청의 수락을 담당한다.
			// 두번째 인수는 연결된 소켓에 대한 I/O 처리를 담당하는 자식 스레드이다.
			.channel(NioServerSocketChannel.class)
			// 서버 소켓이 사용할 네트워크 입출력 모드를 설정한다. 여기서는 NioServerSocketChannel 클래스로 설정했기 때문에
			// NIO 모드로 동작한다.
			.childHandler(new ChannelInitializer<SocketChannel>() {
				//ChannelInitializer는 클라이언트로부터 연결된 채널이 초기화 될 때의 기본 동작이 지정된 추상 클래스다.
				@Override
				public void initChannel(SocketChannel ch) {
					// TODO Auto-generated method stub
					// initChannel 메서드는 클라이언트 소켓 채널이 생성될 때 자동으로 호출 된다. 이때 채널 파이프라인의
					// 설정을 수행하게 된다.
					ChannelPipeline p = ch.pipeline();
					// 채널 파이프라인 객체를 생성한다.
					p.addLast(new EchoServerHandler());
					// 접속된 클라이언트로부터 수신된 데이터를 처리할 핸들러를 지정한다.
					// 채널 파이프라인에 EchoServerHandler 클래스를 등록한다.
					// EchoServerHandler 클래스는 이후에 클라이언트의 연결이 생성되었을 때 데이터 처리를 담당한다.
					
				}
			});
		
			ChannelFuture f = b.bind(8888).sync();
			// 부트스트랩 클래스의 bind 메서드로 접속할 포트를 지정한다.
			f.channel().closeFuture().sync();
		}
		finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}

}


