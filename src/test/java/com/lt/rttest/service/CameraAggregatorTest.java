package com.lt.rttest.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lt.rttest.data.CameraAgg;
import com.lt.rttest.data.SourceData;
import com.lt.rttest.data.SourceUrlType;
import com.lt.rttest.data.TokenData;

import akka.actor.ActorSystem;

public class CameraAggregatorTest {

	private ActorSystem system;
	private CameraAggregator aggregator;
	private CameraAggReceiver receiver;

	@Before
	public void setup() {
		system = ActorSystem.create("rttest-system");
		receiver = mock(CameraAggReceiver.class);
		aggregator = new CameraAggregator(123, receiver);
	}

	@After
	public void teardown() {
		system.terminate();
		system = null;
	}

	@Test
	public void aggregateTest() {
		SourceData sourceData = new SourceData();
		sourceData.setUrlType(SourceUrlType.LIVE);
		sourceData.setVideoUrl("videoUrl");
		TokenData tokenData = new TokenData();
		tokenData.setTtl(20);
		tokenData.setValue("value");

		CameraAgg expected = new CameraAgg();
		expected.setId(123);
		expected.setTtl(tokenData.getTtl());
		expected.setValue(tokenData.getValue());
		expected.setUrlType(sourceData.getUrlType());
		expected.setVideoUrl(sourceData.getVideoUrl());

		aggregator.addSource(sourceData);
		aggregator.addToken(tokenData);

		verify(receiver).sendCameraAgg(expected);

	}
}
