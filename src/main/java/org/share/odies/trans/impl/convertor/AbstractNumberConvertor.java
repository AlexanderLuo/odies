package org.share.odies.trans.impl.convertor;

public abstract class AbstractNumberConvertor<N extends Number> extends AbstractSimpleValueConvertor<N> {

	@Override
	protected String val2String(N val) {
		return val.toString();
	}
}
