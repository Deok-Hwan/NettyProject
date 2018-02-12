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
			// ���� ���ø����̼��� ��Ʈ��Ʈ�� ������ �ٸ��� �̺�Ʈ ���� �׷��� �ϳ��� �����Ǿ���. 
			// Ŭ���̾�Ʈ ���ø����̼��� ������ �޸� ������ ����� �ϳ��� �����ϱ� ������ �̺�Ʈ ���� �׷��� �ϳ���.
			.channel(NioSocketChannel.class)
			// Ŭ���̾�Ʈ ���ø����̼��� �����ϴ� ä���� ������ �����Ѵ�. ���⼭�� NIO ���� ä���� NioSocketChannel Ŭ������ �����ߴ�.
			// �� ������ ����� Ŭ���̾�Ʈ�� ���� ä���� NIO�� �����ϰ� �ȴ�.
			.handler(new ChannelInitializer<SocketChannel>() {
				// Ŭ���̾�Ʈ ���ø����̼��̹Ƿ� ä�� ������������ ������ �Ϲ� ���� ä�� Ŭ������ SocketChannel�� �����Ѵ�.
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler());
					
				}
			});
			
			ChannelFuture f = b.connect("localhost",8888).sync();
			// ChannelFuture ��ü�� sync �޼���� ChannelFuture ��ü�� ��û�� �Ϸ� �� ������ ����Ѵ�.
			// �� ��û�� �����ϸ� ���ܸ� ������. �� connect �޼����� ó���� �Ϸ� �� ������ ���� �������� �������� �ʴ´�.
			
			f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
	}
}
