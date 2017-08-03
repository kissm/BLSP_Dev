/**
 * 
 */
package com.framework.osp.modules.weboffice.server;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.framework.osp.common.config.Global;
import com.framework.osp.modules.weboffice.exception.OfficeConvertException;
import com.framework.osp.modules.weboffice.exception.OfficeServerException;


/**
 * OfficeServer
 * 
 * @author wangyj
 * 
 */
public class OfficeServer {
	private final static Log log = LogFactory.getLog(OfficeServer.class);
	/**
	 * OfficeServer已连接
	 */
	public final static int SERVER_STATE_CONNECTED = 1;
	/**
	 * OfficeServer未连接
	 */
	public final static int SERVER_STATE_NOT_CONNECTED = 0;

	private static OfficeServer instance;

	private String converterWorkDir;

	private OfficeManager officeManager;

	private int serverState;

	private OfficeServer(OfficeManager officeManager, int serverState,
			String converterWorkDir) {
		this.officeManager = officeManager;
		this.serverState = serverState;
		this.converterWorkDir = converterWorkDir;
	}

	public static OfficeServer getInstance() throws OfficeServerException {
		if (instance == null) {
			init();
		}
		return instance;
	}

	private static void init() throws OfficeServerException {
		try {
			String officeServerHome = Global.getConfig("openoffice.server.home");
			String converterWorkDir = Global.getConfig("openoffice.converter.workdir");
			if (StringUtils.isBlank(officeServerHome)
					|| StringUtils.isBlank(converterWorkDir)) {
				throw new OfficeServerException(
						"未配置openoffice.server.home或openoffice.converter.workdir");
			}
			int port = Integer.parseInt(Global.getConfig("openoffice.server.port"));
			OfficeManager officeManager = new DefaultOfficeManagerConfiguration()
					.setOfficeHome(officeServerHome).setPortNumber(port)
					.buildOfficeManager();
			instance = new OfficeServer(officeManager,
					SERVER_STATE_NOT_CONNECTED, converterWorkDir);

			// 添加JVM关闭钩子
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					log.info("JVM将要关闭，准备结束soffice进程...");
					try {
						instance.assertTerminateSoffice();
						log.info("成功结束soffice进程。");
					} catch (OfficeServerException e) {
						log.error("结束soffice进程失败，请手动结束soffice进程。", e.getCause());
					}
				}

			});
		} catch (Exception e) {
			throw new OfficeServerException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 查看OfficeServer启动状态
	 * 
	 * @see SERVER_STATE_CONNECTED
	 * @see SERVER_STATE_NOT_CONNECTED
	 * @see SERVER_STATE_CONFIG_ERROR
	 * @see SERVER_STATE_ERROR
	 * @return OfficeServer
	 */
	public int getServerState() {
		return serverState;
	}
	
	public String getConverterWorkDir(){
		return this.converterWorkDir;
	}

	/**
	 * 断言启动soffice进程，未启动则启动进程
	 * 
	 * @throws OfficeServerException
	 */
	public void assertStartSoffice() throws OfficeServerException {
		if (this.serverState != SERVER_STATE_CONNECTED) {
			try {
				this.officeManager.start();
				this.serverState = SERVER_STATE_CONNECTED;
			} catch (OfficeException e) {
				throw new OfficeServerException("启动Office Server失败："
						+ e.getMessage(), e.getCause());
			}
		}
	}

	/**
	 * 断言结束soffice进程，已启动则结束
	 * 
	 * @throws OfficeServerException
	 */
	public void assertTerminateSoffice() throws OfficeServerException {
		if (this.serverState == SERVER_STATE_CONNECTED) {
			try {
				this.officeManager.stop();
				this.serverState = SERVER_STATE_NOT_CONNECTED;
			} catch (OfficeException e) {
				throw new OfficeServerException("停止Office Server失败："
						+ e.getMessage(), e.getCause());
			}
		}
	}

	public File[] convert(File inputFile, File outputFile)
			throws OfficeConvertException {
		try {
			this.assertStartSoffice();
			OfficeDocumentConverter converter = new OfficeDocumentConverter(
					officeManager);
			converter.convert(inputFile, outputFile);
		} catch (Exception e) {
			throw new OfficeConvertException(e.getMessage(), e.getCause());
		}
		return outputFile.getParentFile().listFiles();
	}

}
