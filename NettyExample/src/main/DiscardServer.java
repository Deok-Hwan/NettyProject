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

public class DiscardServer {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); 
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
				@Override
				public void initChannel(SocketChannel ch) {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new DiscardServerHandler());
					// ���ӵ� Ŭ���̾�Ʈ�κ��� ���ŵ� �����͸� ó���� �ڵ鷯�� �����Ѵ�.
				}
			});
		
			ChannelFuture f = b.bind(8888).sync();
			// ��Ʈ��Ʈ�� Ŭ������ bind �޼���� ������ ��Ʈ�� �����Ѵ�.
			f.channel().closeFuture().sync();
		}
		finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}

}


