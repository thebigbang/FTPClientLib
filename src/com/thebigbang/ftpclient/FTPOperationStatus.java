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
 * Set on status possible for FTP operations
 * @author Jeremy.Mei-Garino
 *
 */
public enum FTPOperationStatus {
	/**
	 * Operation was a success
	 */
	Succed,
	/**
	 * Operation Failed (see Exception error in Bundle then...)
	 */
	Failed,
	/**
	 * Operation not yet proceed.
	 */
	Pending
}
