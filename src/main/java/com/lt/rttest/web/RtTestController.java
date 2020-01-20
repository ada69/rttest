package com.lt.rttest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lt.rttest.data.CameraAgg;
import com.lt.rttest.service.RtTestService;

@RestController
@RequestMapping(path = "/rttest")
public class RtTestController {

	@Autowired
	private RtTestService rtTestService;

	@RequestMapping(path = "/cameras", method = RequestMethod.GET)
	public ResponseEntity<List<CameraAgg>> camerasInfo() {

		List<CameraAgg> info = rtTestService.camerasInfo();
		ResponseEntity<List<CameraAgg>> response = new ResponseEntity<List<CameraAgg>>(info, HttpStatus.OK);
		return response;

	}

}
