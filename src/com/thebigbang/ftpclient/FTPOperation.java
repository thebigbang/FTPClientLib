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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

/**
 * Manage multiple FTP operations in a background thread. Can handle a bunch on
 * FTPBundle operations. Will send an event for each operation (FTPBundle) done
 * with the result in it. The Event result will always be on the UI Thread.
 * Force the device to stay on during the command processing, mainly because of connection drop in sleeping cases...
 * todo in newt version: maybe add a timeout feature? :)
 * @author Jeremy.Mei-Garino
 * 
 */
public class FTPOperation extends AsyncTask<FTPBundle, FTPBundle, Boolean> {

	private Context ctx;
	private FTPOperationResult resultListener;
/**
 * set the listener of the results of operations.
 * @param listener 
 */
	public void setOperationResultListener(FTPOperationResult listener) {
		resultListener = listener;
	}

	private void sendOperationResult(FTPBundle... re) {
		if (resultListener != null)
			for (FTPBundle b : re)
				resultListener.Result(b);
	}

	public FTPOperation(Context c) {
		super();
		ctx = c;
	}

	public FTPOperation(Context c, FTPOperationResult list) {
		super();
		resultListener = list;
		ctx = c;
	}
/**
 * will force keep the device turned on for all the operation duration.
 * @param params
 * @return 
 */
	@SuppressLint("Wakelock") @SuppressWarnings("deprecation")
	@Override
	protected Boolean doInBackground(FTPBundle... params) {
		Thread.currentThread().setName("FTPOperationWorker");
		for (FTPBundle bundle : params) {

			FTPClient ftp = new FTPClient();
			PowerManager pw = (PowerManager) ctx
					.getSystemService(Context.POWER_SERVICE);
			WakeLock w = pw.newWakeLock(PowerManager.FULL_WAKE_LOCK,
					"FTP Client");
			try {
				// setup ftp connection:
				InetAddress addr = InetAddress.getByName(bundle.FTPServerHost);

				ftp.connect(addr, bundle.FTPServerPort);
				int reply = ftp.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					throw new IOException("connection refus�e");
				}
				ftp.login(bundle.FTPCredentialUsername,
						bundle.FTPCredentialPassword);
				if(bundle.OperationType==FTPOperationType.Connect)
				{
					bundle.OperationStatus=FTPOperationStatus.Succed;
					publishProgress(bundle);
					continue;	
				}
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				ftp.enterLocalPassiveMode();

				w.acquire();
				// then switch between enum of operation types.
				if (bundle.OperationType == FTPOperationType.RetrieveFilesFoldersList) {
					ftp.changeWorkingDirectory(bundle.RemoteWorkingDirectory);
					bundle.FilesOnCurrentPath = ftp.listFiles();
					bundle.FoldersOnCurrentPath = ftp.listDirectories();
					bundle.OperationStatus = FTPOperationStatus.Succed;
				} else if (bundle.OperationType == FTPOperationType.RetrieveFolderList) {
					ftp.changeWorkingDirectory(bundle.RemoteWorkingDirectory);
					bundle.FoldersOnCurrentPath = ftp.listDirectories();
					bundle.OperationStatus = FTPOperationStatus.Succed;
				} else if (bundle.OperationType == FTPOperationType.RetrieveFileList) {
					ftp.changeWorkingDirectory(bundle.RemoteWorkingDirectory);
					bundle.FilesOnCurrentPath = ftp.listFiles();
					bundle.OperationStatus = FTPOperationStatus.Succed;
				} else if (bundle.OperationType == FTPOperationType.GetData) {
					String finalFPFi = bundle.LocalFilePathName;
					// The remote filename to be downloaded.
					if (bundle.LocalWorkingDirectory != null
							&& bundle.LocalWorkingDirectory != "") {
						File f = new File(bundle.LocalWorkingDirectory);
						f.mkdirs();

						finalFPFi = bundle.LocalWorkingDirectory + finalFPFi;
					}
					FileOutputStream fos = new FileOutputStream(finalFPFi);

					// Download file from FTP server
					String finalFileN = bundle.RemoteFilePathName;
					if (bundle.RemoteWorkingDirectory!=null&&bundle.RemoteWorkingDirectory != "") {
						finalFileN = bundle.RemoteWorkingDirectory + finalFileN;
					}
					boolean b = ftp.retrieveFile(finalFileN, fos);
					if (b)
						bundle.OperationStatus = FTPOperationStatus.Succed;
					else
						bundle.OperationStatus = FTPOperationStatus.Failed;
					fos.close();

				} else if (bundle.OperationType == FTPOperationType.SendData) {
					InputStream istr = new FileInputStream(
							bundle.LocalFilePathName);
					ftp.changeWorkingDirectory(bundle.RemoteWorkingDirectory);
					Boolean b = ftp.storeFile(bundle.RemoteFilePathName, istr);
					istr.close();
					if (b)
						bundle.OperationStatus = FTPOperationStatus.Succed;
					else
						bundle.OperationStatus = FTPOperationStatus.Failed;
				} else if (bundle.OperationType == FTPOperationType.DeleteData) {
					throw new IOException("DeleteData is Not yet implemented");
				}

				ftp.disconnect();
				// then finish/return.
				//publishProgress(bundle);
			} catch (IOException e) {
				e.printStackTrace();
				bundle.OperationStatus = FTPOperationStatus.Failed;
			}
			try {
				w.release();
			} catch (RuntimeException ex) {
				ex.printStackTrace();
			}
			publishProgress(bundle);
		}
		return true;
	}

	@Override
	protected void onProgressUpdate(FTPBundle... re) {
		sendOperationResult(re);
	}

}
