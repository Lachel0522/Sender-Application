package com.bistel.acovery.dataproc;

import java.util.Properties;

public interface DataProc extends Runnable {

	public void initialize(final Properties interfaceConfig) throws Exception;
}
