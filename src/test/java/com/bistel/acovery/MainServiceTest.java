package com.bistel.acovery;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.bistel.acovery.config.ConfigManager;

public class MainServiceTest {

	private MainService mainService;

	@Before
	public void setUp() {
		try {
			Properties appConfig = ConfigManager.getAppConfig();
			this.mainService = new MainService(appConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMethod() {
		
	}
}
