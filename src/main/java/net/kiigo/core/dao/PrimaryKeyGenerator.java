package net.kiigo.core.dao;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * 主键生成器
 * 
 * @author Coollf
 * 
 */
public abstract class PrimaryKeyGenerator {

	public static PrimaryKeyGenerator UUID = new PrimaryKeyGenerator() {		
		@Override
		public String next() {
			return compressedUUID(java.util.UUID.randomUUID());
		}
	};

	public static PrimaryKeyGenerator SEQUENCE = new PrimaryKeyGenerator() {
		private IdWorker worker = new IdWorker(1);		
		@Override
		public String next() {
			return  String.valueOf(worker.nextId());
		}
	};

	public abstract String next();

	protected static String compressedUUID(UUID uuid) {

		byte[] byUuid = new byte[16];

		long least = uuid.getLeastSignificantBits();

		long most = uuid.getMostSignificantBits();

		long2bytes(most, byUuid, 0);

		long2bytes(least, byUuid, 8);

		String compressUUID = Base64.encodeBase64URLSafeString(byUuid);

		return compressUUID;

	}

	protected static void long2bytes(long value, byte[] bytes, int offset) {

		for (int i = 7; i > -1; i--) {

			bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);

		}

	}

	@SuppressWarnings("static-access")
	public class IdWorker {
		private final long workerId;
		private final static long twepoch = 1361753741828L;
		private long sequence = 0L;
		/***
		 * 用于标示节点占多少位
		 */
		private final static long workerIdBits = 4L;
		
		/****
		 * 最大节点数
		 */
		public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
		
		/***
		 * 序列占的数据
		 */
		private final static long sequenceBits = 10L;

		private final static long workerIdShift = sequenceBits;
		
		private final static long timestampLeftShift = sequenceBits + workerIdBits;
		
		public final static long sequenceMask = -1L ^ -1L << sequenceBits;

		private long lastTimestamp = -1L;

		
		public IdWorker(final long workerId) {
			super();
			if (workerId > this.maxWorkerId || workerId < 0) {
				throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",this.maxWorkerId));
			}
			this.workerId = workerId;
		}

		public synchronized long nextId() {
			long timestamp = this.timeGen();
			if (this.lastTimestamp == timestamp) {
				this.sequence = (this.sequence + 1) & this.sequenceMask;
				if (this.sequence == 0) {
					System.out.println("###########" + sequenceMask);
					timestamp = this.tilNextMillis(this.lastTimestamp);
				}
			} else {
				this.sequence = 0;
			}
			if (timestamp < this.lastTimestamp) {
				try {
					throw new Exception(
							String.format(
									"Clock moved backwards.  Refusing to generate id for %d milliseconds",
									this.lastTimestamp - timestamp));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			this.lastTimestamp = timestamp;
			long nextId = ((timestamp - twepoch << timestampLeftShift))
					| (this.workerId << this.workerIdShift) | (this.sequence);
			// System.out.println("timestamp:" + timestamp +
			// ",timestampLeftShift:"
			// + timestampLeftShift + ",nextId:" + nextId + ",workerId:"
			// + workerId + ",sequence:" + sequence);
			return nextId;
		}

		private long tilNextMillis(final long lastTimestamp) {
			long timestamp = this.timeGen();
			while (timestamp <= lastTimestamp) {
				timestamp = this.timeGen();
			}
			return timestamp;
		}

		private long timeGen() {
			return System.currentTimeMillis();
		}
	}
}
