package com.shunyi.autoparts.ui.vfs;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

/**
 * @Description: VFS客户端
 *
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/2 12:01
 * @Version: 1.0
 */
public class VFSClient {


    /**
     *
     * @param vfs
     * @return
     */
	public boolean testConnection(com.shunyi.autoparts.ui.model.VFS vfs) {
        FileSystemManager fileSystemManager;
        try {
            fileSystemManager = VFS.getManager();
            String protocol;
            String fileName = null;
            if (vfs.getProtocol().equals("FTP") || vfs.getProtocol().equals("FTPS")) {
                protocol = vfs.getProtocol().toLowerCase();
                fileName = protocol + "://" + vfs.getUserName() + ":" + vfs.getPassword() + "@" + vfs.getHost() + ":" + vfs.getPort() + vfs.getHome();
            } else if (vfs.getProtocol().equals("File")) {
                protocol = vfs.getProtocol().toLowerCase();
                fileName = protocol + ":///" + vfs.getHost();
            }

            FileObject rootFile = fileSystemManager.resolveFile(fileName);
            fileSystemManager.closeFileSystem(rootFile.getFileSystem());
            return rootFile.exists();
        } catch (FileSystemException e) {
            e.printStackTrace();
        }
	    return false;
	}

//	/**
//	 * 上传
//	 *
//	 * @param site
//	 * @param targetPath
//	 * @return
//	 * @throws FileException
//	 */
//	public OutputStream receiveUpload(Site site, String targetPath) throws FileException {
//		FileSystemManager fsManager = null;
//		FileObject file = null;
//		try {
//			fsManager = VFS.getManager();
//			String protocol = null;
//			String name = null;
//			if (site.getSiteType().equals("FTP") || site.getSiteType().equals("FTPS")) {
//				protocol = site.getSiteType().toLowerCase();
//				name = protocol + "://" + site.getUserName() + ":" + site.getPassword() + "@" + site.getHostAddr() + ":"
//						+ site.getPort() + site.getDefaultRemoteDirectory();
//			} else if (site.getSiteType().equals("File")) {
//				protocol = site.getSiteType().toLowerCase();
//				name = protocol + ":///" + site.getHostAddr();
//			}
//			targetPath = new String(targetPath.getBytes("UTF-8"),"ISO-8859-1");
//			file = fsManager.resolveFile(name + "/"+ targetPath);
//
//			if (!file.exists()) {
//				file.createFile();
//			}
//			return file.getContent().getOutputStream(); // Return the output stream to write to
//		} catch (FileSystemException e) {
//			log.info(e.getMessage());
//			throw new FileException(e.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			log.info(e.getMessage());
//			throw new FileException(e.getMessage());
//		} finally {
//			try {
//				fsManager.closeFileSystem(file.getFileSystem());
//			} catch (java.lang.NullPointerException e) {
//				String msg = "Cannot connect to the site, please contact the administrator in TB4 team.";
//				log.info(msg);
//				throw new FileException(msg);
//			}
//		}
//	}
//
//	/**
//	 * 下载
//	 * @param site
//	 * @param targetPath
//	 * @return
//	 * @throws FileException
//	 */
//	public FileObject resolveFile(Site site, String targetPath) throws FileException {
//		FileSystemManager fsManager = null;
//		FileObject file = null;
//		try {
//			fsManager = VFS.getManager();
//			String protocol = null;
//			String name = null;
//			if (site.getSiteType().equals("FTP") || site.getSiteType().equals("FTPS")) {
//				protocol = site.getSiteType().toLowerCase();
//				name = protocol + "://" + site.getUserName() + ":" + site.getPassword() + "@" + site.getHostAddr() + ":"
//						+ site.getPort() + site.getDefaultRemoteDirectory();
//			} else if (site.getSiteType().equals("File")) {
//				protocol = site.getSiteType().toLowerCase();
//				name = protocol + ":///" + site.getHostAddr();
//			}
//			targetPath = new String(targetPath.getBytes("UTF-8"),"ISO-8859-1");
//			file = fsManager.resolveFile(name + "/"+ targetPath);
//
//			return file; // Return the output stream to write to
//		} catch (FileSystemException e) {
//			log.info(e.getMessage());
//			throw new FileException(e.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			log.info(e.getMessage());
//			throw new FileException(e.getMessage());
//		} finally {
//			try {
//				fsManager.closeFileSystem(file.getFileSystem());
//			} catch (java.lang.NullPointerException e) {
//				String msg = "Cannot connect to the site, please contact the administrator in TB4 team.";
//				log.info(msg);
//				throw new FileException(msg);
//			}
//		}
//	}
//
//	/**
//	 * 删除文件
//	 *
//	 * @param site
//	 * @param targetPath
//	 * @throws FileException
//	 */
//	public void deleteFile(Site site, String targetPath) throws FileException {
//		FileSystemManager fsManager = null;
//		FileObject file = null;
//		try {
//			fsManager = VFS.getManager();
//			String protocol = null;
//			String name = null;
//			if (site.getSiteType().equals("FTP") || site.getSiteType().equals("FTPS")) {
//				protocol = site.getSiteType().toLowerCase();
//				name = protocol + "://" + site.getUserName() + ":" + site.getPassword() + "@" + site.getHostAddr() + ":"
//						+ site.getPort() + site.getDefaultRemoteDirectory();
//			} else if (site.getSiteType().equals("File")) {
//				protocol = site.getSiteType().toLowerCase();
//				name = protocol + ":///" + site.getHostAddr();
//			}
//			targetPath = new String(targetPath.getBytes("UTF-8"),"UTF-8");
//			file = fsManager.resolveFile(name + "/"+ targetPath);
//
//			file.delete();
//
//		} catch (FileSystemException e) {
//			log.info(e.getMessage());
//			throw new FileException(e.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			log.info(e.getMessage());
//			throw new FileException(e.getMessage());
//		} finally {
//			try {
//				fsManager.closeFileSystem(file.getFileSystem());
//			} catch (java.lang.NullPointerException e) {
//				String msg = "Cannot connect to the site, please contact the administrator in TB4 team.";
//				log.info(msg);
//				throw new FileException(msg);
//			}
//		}
//	}
//
//	/**
//	 * 更新存储已用大小
//	 *
//	 * @param siteUniqueId
//	 * @param increment     增量字节数
//	 */
//	public void increaseUsedSize(int siteUniqueId, long increment) {
//		Site site = ui.siteService.findById(siteUniqueId);
//		long newUsedSize = site.getSiteCapacity().getUsedSpace() + increment;
//		int affectedRow = ui.siteService.updateCapacity(siteUniqueId, site.getSiteCapacity().getUpdateTimeMillis(), System.currentTimeMillis(), newUsedSize);
//		// 如果更新时间点不存在，怎递归执行，直到累加成功。
//		if (affectedRow == 0) {
//			increaseUsedSize(siteUniqueId, increment);
//		}
//	}
//
//
//	public static void main(String[] args) throws FileSystemException, UnsupportedEncodingException {
////		// Locate the Jar file
////		FileSystemManager fsManager = VFS.getManager();
//////		FileSystemOptions options = new FileSystemOptions();
//////		FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, true);
////
//////		String name = "ftp://user1:123456@127.0.0.1:21/";
//////		String name = "ftps://ftps_user:123456@127.0.0.1:21/";
////		String name = "ftp"+"://"+"chens"+":"+"Wonderful2018"+"@"+"10.48.5.75"+":"+"21"+"/";
//////		String name = "file"+":///"+"C:\\Users\\chens\\Documents\\development\\maxtree\\TrackBe4\\tmp\\uploads\\1";
//////		String name = "file"+":///"+"E:\\Maxtree\\trackbe4\\tmp\\uploads";
////
////		FileObject rootFile = fsManager.resolveFile(name );
////
//////		System.out.println(rootFile.exists());
////
////		// List the children of the Jar file
////		FileObject[] children = rootFile.getChildren();
////		System.out.println( "Children of " + rootFile.getName().getURI() );
////		for ( int i = 0; i < children.length; i++ ) {
////			String baseName = children[i].getName().getBaseName();
//////			System.out.println(children[i].getContent().);
////			String ext = Files.getFileExtension(baseName);
//////			System.out.println(baseName+"  "+ext+"  "); //prints txt
////			for (String d: children[i].getContent().getAttributeNames()) {
////				System.out.println(d);
////			}
//////            System.out.println(" " + baseName + "  --  " + new String(baseName.getBytes("iso-8859-1"),"UTF-8"));
////		}
//
//		VFSUtils sys = new VFSUtils();
//		Site site = new Site();
//		site.setSiteType("FTP");
//		site.setUserName("chens");
//		site.setPassword("Wonderful2018");
//		site.setHostAddr("10.48.5.75");
//		site.setPort(21);
//		site.setDefaultRemoteDirectory("/");
//		String targetPath = "1/primary/MaxTree截图20171220113538.png";
//		targetPath = new String(targetPath.getBytes("UTF-8"),"ISO-8859-1");
//		try {
//			sys.receiveUpload(site, targetPath);
//		} catch (FileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
