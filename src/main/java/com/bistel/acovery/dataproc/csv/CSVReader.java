package com.bistel.acovery.dataproc.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CSVReader {

	private static final String TARGET_EXTENSION = ".csv";
	private static final int READ_AHEAD_LIMIT = Character.SIZE / Byte.SIZE;
	private BufferedReader br;
	private final File[] fileArr;
	private String[] headers;
	private boolean hasNext;
	private int fileIndex;

	public CSVReader(String dir) throws Exception {
		this(dir != null ? new File(dir) : null);
	}

	public CSVReader(File f) throws Exception {
		File[] fArr = f.listFiles(new ExtensionFilter(TARGET_EXTENSION));
		if (fArr == null || fArr.length == 0) {
			throw new Exception("");
		}
		this.fileArr = fArr;
		this.fileIndex = 0;

	}

	private BufferedReader makeBufferedReader() throws FileNotFoundException, UnsupportedEncodingException {
		FileInputStream fis = new FileInputStream(new File(""));
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		return br;
	}

	public String readLine() throws IOException {
		String nextLine = br.readLine();

		if (nextLine == null) {
			hasNext = false;
		} else {

		}
		return hasNext ? nextLine : null;
	}

	private boolean hasNext() throws IOException {
		br.mark(READ_AHEAD_LIMIT);
		int nextByte = br.read();
		br.reset();
		return !(nextByte == -1);
	}

	private class ExtensionFilter implements FilenameFilter {

		private final String extension;

		public ExtensionFilter(String extension) {
			this.extension = extension;
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(extension);
		}
	}
}
