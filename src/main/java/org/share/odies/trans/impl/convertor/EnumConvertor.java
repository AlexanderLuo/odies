package org.share.odies.trans.impl.convertor;

import org.apache.commons.lang3.EnumUtils;
import org.share.odies.trans.ConvertorMatcher;
import org.share.odies.trans.DataItem;
import org.share.odies.trans.ValueConvertor;

public class EnumConvertor<E extends Enum<E>> implements ValueConvertor<E> {

	public static final ConvertorMatcher MARCHER = new ConvertorMatcher(){
		@Override
		public boolean isMatch(Class clazz) {
			return Enum.class.isAssignableFrom(clazz);
		}
	};

	@Override
	public DataItem[] toRedisData(String key, Enum val) {
		if(val==null)
			return new DataItem[0];
		return new DataItem[]{new DataItem(key,val.name().getBytes())};
	}

	@Override
	public E toValue(Class<E> clazz, String prefix, DataItem[] redisData) {
		if(redisData==null || redisData.length==0){
			return null;
		}
		String enumName = new String(redisData[0].getData());
		return EnumUtils.getEnum(clazz,enumName);
	}
//	enums TEST{
//		A,
//		B
//	}
//	public static void main(String[] args) {
//		EnumConvertor ec = new EnumConvertor();
//		DataItem[] items = ec.toRedisData("abc", TEST.A);
//		System.out.println(Arrays.toString(items));
//		Enum anEnum = ec.toValue(TEST.class, "", items);
//		System.out.println(anEnum);
//	}

}
