package com.thoratou.exact.processors;

import com.thoratou.exact.annotations.ExactNode;
import com.thoratou.exact.annotations.ExactPath;
import com.thoratou.exact.fields.FieldString;

@ExactNode
public class SimpleBom {

	FieldString dummy;
	
	@ExactPath("dummy/text()")
	FieldString getDummy(){
		return dummy;
	}
}
