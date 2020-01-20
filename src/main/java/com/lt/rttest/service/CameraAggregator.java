package com.lt.rttest.service;

import com.lt.rttest.data.CameraAgg;
import com.lt.rttest.data.SourceData;
import com.lt.rttest.data.TokenData;

public class CameraAggregator {
	private int cameraId;
	private CameraAggReceiver receiver;
	private SourceData sourceData;
	private TokenData tokenData;

	public CameraAggregator(int cameraId, CameraAggReceiver receiver) {
		this.cameraId = cameraId;
		this.receiver = receiver;
	}

	public void addSource(SourceData sourceData) {
		this.sourceData = sourceData;
		aggregate();

	}

	public void addToken(TokenData tokenData) {
		this.tokenData = tokenData;
		aggregate();
	}

	private void aggregate() {
		if (sourceData == null || tokenData == null) {
			return;
		}
		CameraAgg cameraAgg = new CameraAgg();
		cameraAgg.setId(cameraId);
		cameraAgg.setVideoUrl(sourceData.getVideoUrl());
		cameraAgg.setUrlType(sourceData.getUrlType());
		cameraAgg.setValue(tokenData.getValue());
		cameraAgg.setTtl(tokenData.getTtl());
		sourceData = null;
		tokenData = null;
		receiver.sendCameraAgg(cameraAgg);
	}

}
