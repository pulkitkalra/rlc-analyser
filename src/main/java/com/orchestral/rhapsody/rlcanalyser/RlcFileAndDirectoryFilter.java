package com.orchestral.rhapsody.rlcanalyser;

import java.io.File;
import java.io.FilenameFilter;

public class RlcFileAndDirectoryFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		if (dir == null || name == null) {
			return false;
		}
		File concatFile = new File(dir, name);
		if (concatFile.isDirectory()) {
			return true;
		}
		name = name.trim().toLowerCase();
		return (name.length() > 4 && name.endsWith(".rlc"));
	}

}
