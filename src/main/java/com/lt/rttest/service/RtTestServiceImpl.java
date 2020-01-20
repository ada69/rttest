package com.lt.rttest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lt.rttest.actor.CameraActor;
import com.lt.rttest.config.ProjectConfig;
import com.lt.rttest.data.Camera;
import com.lt.rttest.data.CameraAgg;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@Service
public class RtTestServiceImpl implements RtTestService, CameraAggReceiver {

	private List<Camera> cameras;
	private ActorSystem system;
	private List<ActorRef> cameraActors = new ArrayList<>();
	private List<CameraAgg> camerasAgg;
	private Set<ActorRef> waitSet = new HashSet<>();
	private Map<Camera, ActorRef> camera2Actor = new HashMap<Camera, ActorRef>();;

	@Autowired
	private ProjectConfig props;

	@PostConstruct
	private void postConstruct() {

		cameras = getCamerasList();
		system = ActorSystem.create("rttest-system");

		for (Camera camera : cameras) {
			ActorRef cameraActorRef = system.actorOf(CameraActor.props(camera, this, getTemplate()),
					"camera-actor-" + camera.getId());
			cameraActors.add(cameraActorRef);
			camera2Actor.put(camera, cameraActorRef);
		}

	}

	private List<Camera> getCamerasList() {
		ResponseEntity<Camera[]> response = getTemplate().getForEntity(props.getVideouri(), Camera[].class);
		return Arrays.asList(response.getBody());

	}

	private RestTemplate getTemplate() {
		RestTemplate template = new RestTemplate();
		if (props.getProxyhost() != null && !props.getProxyhost().isEmpty())
		{
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(new AuthScope(props.getProxyhost(), props.getProxyport()),
					new UsernamePasswordCredentials(props.getUser(), props.getPassword()));

			HttpClientBuilder clientBuilder = HttpClientBuilder.create();

			clientBuilder.useSystemProperties();
			clientBuilder.setProxy(new HttpHost(props.getProxyhost(), props.getProxyport()));
			clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

			CloseableHttpClient client = clientBuilder.build();

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setHttpClient(client);

			template.setRequestFactory(factory);
		}
		return template;
	}

	@Override
	public List<CameraAgg> camerasInfo() {
		camerasAgg = new ArrayList<CameraAgg>();
		for (ActorRef cameraRef : cameraActors) {
			waitSet.add(cameraRef);
			cameraRef.tell(new CameraActor.LoadAggCamera(), null);
		}
		waitFillAggCameras();
		return camerasAgg;
	}

	private void waitFillAggCameras() {
		while (!waitSet.isEmpty()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			continue;
		}
	}

	@Override
	public void sendCameraAgg(CameraAgg cameraAgg) {
		camerasAgg.add(cameraAgg);
		Camera camera = cameras.stream().filter(cam -> cam.getId() == cameraAgg.getId()).findFirst().orElse(null);
		if (camera != null)
		{
			waitSet.remove(camera2Actor.get(camera));
		}
	}

}
