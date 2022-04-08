package org.ip;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class MemoryAllocator {
	public static void main(String[] s) {
		MemoryAllocator allocator = new MemoryAllocator(2, 2);
		ByteBuffer block0 = allocator.alloc();
		ByteBuffer block1 = allocator.alloc();
		allocator.release(block1);
		allocator.alloc();
		try 
		{
			allocator.alloc();
		} catch (RuntimeException e) {
			//expected
		}
	}
	Map<Integer,BitSet> released = new HashMap<Integer, BitSet>();
	private final int blockSize;
	private Allocator idAllocator;
	private final byte[] blocks = null;
	private final ByteBuffer memory;
	public MemoryAllocator(int nbBlocks, int blockSizeInBytes) {
		ByteBuffer b;
		this.blockSize = blockSizeInBytes;
		this.memory = ByteBuffer.allocate(blockSizeInBytes * nbBlocks);
		this.idAllocator = new Allocator(nbBlocks);
	}
	public ByteBuffer alloc() {
		int id = this.idAllocator.alloc();
		return memory.slice(id * blockSize, blockSize);
	}
	public void release(ByteBuffer buffer) {
		int offset = buffer.arrayOffset();
		this.idAllocator.release(offset / blockSize);
	}
	/*
	 * Alternative to ByteBuffer using backing blocks
	 */
	public class Block {
		private int id;
		private int blockId;

		public Block(int id) {
			this.id = id;
			this.blockId = id * blockSize;
		}
		public byte get(int byteInBlock) {
			assertBlock(byteInBlock);
			return blocks[blockId + byteInBlock];
		}
		public void set(int byteInBlock, byte value) {
			assertBlock(byteInBlock);
			blocks[blockId + byteInBlock] = value;
		}
		void assertBlock(int byteInBlock) {
			if (byteInBlock > blockSize || byteInBlock < 0) throw new IllegalArgumentException("Requesting block outside block size");
		}
		@Override
		public String toString() {
			return "Block [id=" + id + "]";
		}
		
	}
}
