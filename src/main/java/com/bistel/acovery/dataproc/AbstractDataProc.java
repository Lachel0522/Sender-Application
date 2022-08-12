package com.bistel.acovery.dataproc;

import java.util.concurrent.BlockingQueue;

import com.bistel.acovery.asset.model.Asset;
import com.bistel.acovery.message.ApmMessage;

public abstract class AbstractDataProc implements DataProc {

	protected final Asset asset;
	protected final BlockingQueue<ApmMessage> blockingQueue;

	public AbstractDataProc(Asset asset, BlockingQueue<ApmMessage> blockingQueue) {
		this.asset = asset;
		this.blockingQueue = blockingQueue;
	}
}
