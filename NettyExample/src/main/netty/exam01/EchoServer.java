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
		// NioEventLoopGroup Ŭ���� �������� �μ��� ���� ���ڴ� ������ �׷� ������ ������ �ִ� ������ ���� �ǹ��Ѵ�.
		// 1�� �����Ͽ����Ƿ� �θ� ������ �׷��� ���� ������� �����Ѵ�.
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		// NioEventLoopGroup Ŭ������ �μ� ���� �����ڸ� ȣ���ߴ�. 
		// �μ� ���� �����ڴ� ����� ������ ���� ���� ���ø����̼��� �����ϴ� �ϵ���� �ھ� ���� �������� �����Ѵ�.
		try {
			ServerBootstrap b = new ServerBootstrap();
			// �μ� ���� �����ڷ� ServerBootStrap ��ü ����
			b.group(bossGroup, workerGroup)
			// group, Channel�� ���� �޼���� ��ü�� �ʱ�ȭ
			// b.group ServerBootStrap ��ü�� ���� ���ø����̼��� ����� �� ������ �׷��� ����
			// ù��° �μ��� �θ� �������̴�. �θ� ������� Ŭ���̾�Ʈ ���� ��û�� ������ ����Ѵ�.
			// �ι�° �μ��� ����� ���Ͽ� ���� I/O ó���� ����ϴ� �ڽ� �������̴�.
			.channel(NioServerSocketChannel.class)
			// ���� ������ ����� ��Ʈ��ũ ����� ��带 �����Ѵ�. ���⼭�� NioServerSocketChannel Ŭ������ �����߱� ������
			// NIO ���� �����Ѵ�.
			.childHandler(new ChannelInitializer<SocketChannel>() {
				//ChannelInitializer�� Ŭ���̾�Ʈ�κ��� ����� ä���� �ʱ�ȭ �� ���� �⺻ ������ ������ �߻� Ŭ������.
				@Override
				public void initChannel(SocketChannel ch) {
					// TODO Auto-generated method stub
					// initChannel �޼���� Ŭ���̾�Ʈ ���� ä���� ������ �� �ڵ����� ȣ�� �ȴ�. �̶� ä�� ������������
					// ������ �����ϰ� �ȴ�.
					ChannelPipeline p = ch.pipeline();
					// ä�� ���������� ��ü�� �����Ѵ�.
					p.addLast(new EchoServerHandler());
					// ���ӵ� Ŭ���̾�Ʈ�κ��� ���ŵ� �����͸� ó���� �ڵ鷯�� �����Ѵ�.
					// ä�� ���������ο� EchoServerHandler Ŭ������ ����Ѵ�.
					// EchoServerHandler Ŭ������ ���Ŀ� Ŭ���̾�Ʈ�� ������ �����Ǿ��� �� ������ ó���� ����Ѵ�.
					
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


