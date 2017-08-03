package com.framework.fdfs.images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * Created by wangjunwei on 15-5-15.
 */
public class FastDFSFileFactory {
	private static Properties config = new Properties();

	private static List<String> hostlist = null;

	private RoundRobin rr;

	private FastDFSFileFactory() {
		try {
			InputStream in = FastDFSFileFactory.class.getClassLoader().getResourceAsStream("fdfs_client.properties");
			if (in == null) {
				throw new RuntimeException("fdfs_client.properties not exist");
			}
			ClientGlobal.init(in);
			InputStream imgin = FastDFSFileFactory.class.getClassLoader().getResourceAsStream("images.properties");
			config.load(imgin);
			hostlist = new ArrayList<String>();
			rr = new RoundRobin(Integer.parseInt(config.getProperty("server.count", "1")));
			for (int i = 0; i < Integer.parseInt(config.getProperty("server.count", "1")); i++) {
				String url = config.getProperty("server.protocol") + "://"
						+ config.getProperty("server.host." + (i + 1)) + "/";
				// String url =
				// config.getProperty("server.protocol")+"://"+config.getProperty("server.subdomain","");
				// url = url + i +config.getProperty("server.host." + (i+1)) +
				// "/";
				// System.out.println(url);
				hostlist.add(url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	private class RoundRobin {
		private int[] server = null;// 机器序号：权重
		private int cw = 0;
		private int number = -1;// 当前SERVER的序号,开始是-1
		private int max = 1;// 最大权重
		private int gcd = 1;// 最大权重

		public RoundRobin(int server) {
			this.server = new int[server];
			for (int i = 0; i < server; i++) {
				this.server[i] = 1;
			}
		}

		public Integer next() {
			while (true) {
				number = (number + 1) % server.length;
				// System.out.println("number=" + number);
				// System.out.println("cw=" + cw);
				if (number == 0) {
					cw = cw - gcd;// cw比较因子，从最大权重开始，以最大公约数为步长递减
					if (cw <= 0) {//
						cw = max;
						if (cw == 0) {
							return null;
						}
					}
				}
				if (server[number] >= cw) {
					return number;
				}
			}
		}
	}

	private static class Tmp {
		private static final FastDFSFileFactory factory = new FastDFSFileFactory();
	}

	public static FastDFSFileFactory getInstance() {
		return Tmp.factory;
	}

	public void saveFile(InputStream in, FastDFSFile dfsfile) {
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = null;
		try {
			trackerServer = tracker.getConnection();
			try {
				StorageServer storageServer = null;
				StorageClient1 client = new StorageClient1(trackerServer, storageServer);
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				dfsfile.setFileid(client.upload_file1(dfsfile.getGroupname(), outStream.toByteArray(),
						dfsfile.getExtname(), null));
				dfsfile.setUrl(hostlist.get(rr.next()) + dfsfile.getFileid());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (trackerServer != null) {
						trackerServer.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] downloadFile(FastDFSFile dfsfile) {
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = null;
		try {
			trackerServer = tracker.getConnection();
			try {
				StorageServer storageServer = null;
				StorageClient1 client = new StorageClient1(trackerServer, storageServer);
				byte b[] = client.download_file1(dfsfile.getFileid());
				return b;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					trackerServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
