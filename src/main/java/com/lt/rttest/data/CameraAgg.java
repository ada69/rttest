package com.lt.rttest.data;

import lombok.Data;

@Data
public class CameraAgg {
	private int id;
	private SourceUrlType urlType;
	private String videoUrl;
	private String value;
	private int ttl;
}
