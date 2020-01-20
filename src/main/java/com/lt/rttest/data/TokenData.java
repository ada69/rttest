package com.lt.rttest.data;

import lombok.Data;

@Data
public class TokenData extends CameraData {
	private String value;
	private int ttl;
}
