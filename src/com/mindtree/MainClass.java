package com.mindtree;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainClass 
{
	private static int maxAllowedMemory = 70;
	public static void main(String[] args) 
	{
		int count = 0;
		String hostName = "";
		File[] drives;
		Map<File, List<Double>> highUsedDrives = new HashMap<File,List<Double>>();
		try 
		{
			hostName = InetAddress.getLocalHost().getHostName();
			drives = File.listRoots();
			if (drives != null && drives.length > 0) 
			{
				for (File drive : drives) 
				{
					double totalSpaceInGB = (drive.getTotalSpace())/(1024*1024*1024);
					double freeSpaceInGB = (drive.getFreeSpace())/(1024*1024*1024);
					double usedSpaceInGB = totalSpaceInGB - freeSpaceInGB;
					double usedPercentage = (int) ((usedSpaceInGB / totalSpaceInGB) * 100);
					if(usedPercentage >= maxAllowedMemory)
					{
						count = count+1;
					}
					List<Double> spaceDetails = new ArrayList<Double>();
					spaceDetails.add(totalSpaceInGB);
					spaceDetails.add(freeSpaceInGB);
					spaceDetails.add(usedPercentage);
					highUsedDrives.put(drive, spaceDetails);
				}
			}
			if(count>0)
			{
				MailClass.sendMail(hostName, highUsedDrives);
			}
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
