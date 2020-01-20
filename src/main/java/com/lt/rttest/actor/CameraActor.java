package com.lt.rttest.actor;

import org.springframework.web.client.RestTemplate;

import com.lt.rttest.data.Camera;
import com.lt.rttest.data.SourceData;
import com.lt.rttest.data.TokenData;
import com.lt.rttest.service.CameraAggReceiver;
import com.lt.rttest.service.CameraAggregator;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class CameraActor extends AbstractActor {

	public static class LoadAggCamera {
	}

	private RestTemplate template;
	private Camera camera;
	private CameraAggregator aggregator;

	private ActorRef sourceRef;
	private ActorRef tokenRef;

	CameraActor(Camera camera, CameraAggReceiver receiver, RestTemplate template) {
		this.camera = camera;
		this.template = template;
		this.aggregator = new CameraAggregator(camera.getId(), receiver);
	}

	public static Props props(Camera camera, CameraAggReceiver receiver, RestTemplate template) {
		return Props.create(CameraActor.class, () -> new CameraActor(camera, receiver, template));
	}

	@Override
	public void preStart() {
		sourceRef = getContext().actorOf(CameraDataActor.props(template, camera.getSourceDataUrl(), SourceData.class));
		tokenRef = getContext().actorOf(CameraDataActor.props(template, camera.getTokenDataUrl(), TokenData.class));
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(LoadAggCamera.class, this::loadCameraAgg)
				.match(SourceData.class, this::onSourceLoaded).match(TokenData.class, this::onTokenLoaded).build();
	}

	private void onSourceLoaded(SourceData sourceData) {
		aggregator.addSource(sourceData);
	}

	private void onTokenLoaded(TokenData tokenData) {
		aggregator.addToken(tokenData);
	}

	private void loadCameraAgg(LoadAggCamera msg) {
		sourceRef.tell(new CameraDataActor.LoadData(), getSelf());
		tokenRef.tell(new CameraDataActor.LoadData(), getSelf());
	}

}
