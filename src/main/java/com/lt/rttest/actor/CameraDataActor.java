package com.lt.rttest.actor;

import org.springframework.web.client.RestTemplate;

import com.lt.rttest.data.CameraData;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class CameraDataActor extends AbstractActor {

	public static class LoadData {
	}

	private RestTemplate template;
	private String uri;
	private Class<? extends CameraData> cameraDataType;

	private CameraDataActor(RestTemplate template, String uri, Class<? extends CameraData> cameraDataType) {
		this.template = template;
		this.uri = uri;
		this.cameraDataType = cameraDataType;
	}

	public static Props props(RestTemplate template, String uri, Class<? extends CameraData> cameraDataType) {
		return Props.create(CameraDataActor.class, () -> new CameraDataActor(template, uri, cameraDataType));
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(LoadData.class, this::loadData).build();
	}

	private void loadData(LoadData msg) {
		CameraData cameraData = getData(uri);
		getSender().tell(cameraData, getSelf());
	}

	private CameraData getData(String uri) {
		return template.getForObject(uri, cameraDataType);
	}
}
