package com.lt.rttest.data;

import lombok.Data;

@Data
public class SourceData extends CameraData {
	private SourceUrlType urlType;
	private String videoUrl;
}
