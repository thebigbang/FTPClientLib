/*This file is part of FTPClientLib.
 * 
 * FTPClientLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * CustomPages is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Copyright (c) Meï-Garino Jérémy 
*/
package com.thebigbang.ftpclient;


import org.apache.commons.net.ftp.FTPFile;

/**
 * Class used to set clear object communication with our ftp server
 * @author Jeremy.Mei-Garino
 *
 */
public class FTPBundle {
	public FTPBundle(String serverAddress,int serverPort,String username,String password)
	{
		FTPCredentialPassword=password;
		FTPCredentialUsername=username;
		FTPServerHost=serverAddress;
		FTPServerPort=serverPort;
	}
	/**
	 * Equivalent to a obj1=obj2; but keeping all references of obj1 (can then be used in a final obj1 case for event management.)
	 * @param bun the bundle to copy into this.
	 */
	public void set(FTPBundle bun)
	{
		FTPCredentialPassword=bun.FTPCredentialPassword;
		FTPCredentialUsername=bun.FTPCredentialUsername;
		FTPServerHost=bun.FTPServerHost;
		FTPServerPort=bun.FTPServerPort;
		LocalFilePathName=bun.LocalFilePathName;
		LocalWorkingDirectory=bun.LocalWorkingDirectory;
		RemoteFilePathName=bun.RemoteFilePathName;
		RemoteWorkingDirectory=bun.RemoteWorkingDirectory;
		FilesOnCurrentPath=bun.FilesOnCurrentPath;
		FoldersOnCurrentPath=bun.FoldersOnCurrentPath;
		OperationStatus=bun.OperationStatus;
		OperationType=bun.OperationType;
	}
	/**
	 * The kind of operation we want to do on the server.
	 * <br/>
	 * Default is Connect, which will just test if we can do a connection.
	 */
	public FTPOperationType OperationType=FTPOperationType.Connect;
	/**
	 * the host of the ftp server.
	 * <br/>
	 * example: ftp://192.168.0.2
	 */
	public String FTPServerHost;
	/**
	 * The communication port of the FTP server.
	 * <br/>
	 * example: 2121
	 */
	public int FTPServerPort;
	/**
	 * the username to login the remote FTP server.
	 */
	public String FTPCredentialUsername;
	/**
	 * the password to login the remote FTP server.
	 */
	public String FTPCredentialPassword;
	
	/**
	 * the local file path/filename to work with
	 * <br/>
	 * For a SendingOperation: must be a fullpath+filename.
	 * <br/>
	 * For a GettingOperation: must be a filename. (fullpath is managed by localWorkingDirectory.) OR fullpath+filename if localWorkingDirectory is empty/null
	 */
	public String LocalFilePathName;
	/**
	 * the remote file path/filename to work with
	 * <br/>
	 * For a SendingOperation: must be a filename.
	 * <br/>
	 * For a GettingOperation: must be a fullpath+filename. OR filename if WorkingPath is not empty.
	 */
	public String RemoteFilePathName;
	/**
	 * the files/folder of the current FTP server path.
	 */
	public FTPFile[] FilesOnCurrentPath;
	/**
	 * the folders on the current FTP server path.
	 */
	public FTPFile[] FoldersOnCurrentPath;
	/**
	 * the working directory we remotely want to be in.
	 * can be a string path or a FTPFile.getName() element.
	 */
	public String RemoteWorkingDirectory;
	/**
	 * the local working directory to store/retrieve file to/from.
	 * example: Environement.getExternalStoragePath()+"/download/";
	 */
	public String LocalWorkingDirectory;
	/**
	 * Retrieve the operation status of the Current Bundle.
	 * Default is Pending.
     * <br/> In Case of Fail see {@link #Exception}
	 */
	public FTPOperationStatus OperationStatus=FTPOperationStatus.Pending;
    /**
     * should not be null in case an exception occurred.
     */
    public Exception Exception;
}
