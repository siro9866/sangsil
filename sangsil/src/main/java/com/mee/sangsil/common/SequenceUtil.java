package com.mee.sangsil.common;

import java.text.DecimalFormat;

/**
 * 시퀀스 생성 - 서버재기동 하면 0 값으로 세팅됨
 * @author Administrator
 *
 */
public class SequenceUtil {

	private final static int SEQ_START = 0;
	private final static int SEQ_END = 999999999;
	private final static DecimalFormat df = new DecimalFormat("000000000");
	protected static int seq = 0;
	protected static Object obj = new Object();
	
	public static String getSeqNumberFile() {
		synchronized (obj) {
			if (seq == SEQ_END)	seq = SEQ_START;
			seq++;
			String seqNumber = df.format(seq);
			return seqNumber;
		}
	}
	
}
