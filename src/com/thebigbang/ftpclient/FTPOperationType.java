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


/**
 * Enumerator of kind of ftp operation we want to do.
 * @author Jeremy.Mei-Garino
 *
 */
public enum FTPOperationType {
	/**
	 * Simply tells us if we can actually connect to the remote FTP server
	 */
	Connect,
	/**
	 * Retrieve the list of directory AND files on a FTP remote server
	 */
	RetrieveFilesFoldersList,
	/**
	 * Retrieve the list of directory on a FTP remote server
	 */
	RetrieveFolderList,
	/**
	 * Retrieve the list of files on a FTP remote server
	 */
	RetrieveFileList,
	/**
	 * Send data to a FTP remote server
	 */
	SendData,
	/**
	 * Get data from the FTP remote server
	 */
	GetData,
	/**
	 * Delete data on the FTP remote server
	 */
	DeleteData,
}
