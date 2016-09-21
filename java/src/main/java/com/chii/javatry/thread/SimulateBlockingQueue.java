package com.chii.javatry.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Describe:
 * Author:  JerryChii.
 * Date:    2016/9/21
 */
public class SimulateBlockingQueue<E> {
	final List<E> list;

	/** Main lock guarding all access */
	final ReentrantLock lock;

	/** Condition for waiting take */
	private final Condition putProcess;

	/** Condition for waiting put */
	private final Condition takeProcess;

	public SimulateBlockingQueue() {
		list = new ArrayList<E>();
		lock = new ReentrantLock(false);
		putProcess = lock.newCondition();
		takeProcess = lock.newCondition();
	}

	public List<Integer> take() throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			while (list.size() == 0)
				takeProcess.await();
			return dequeue();
		} finally {
			lock.unlock();
		}
	}

	public void put(List<E> toPut) throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			while (list.size() != 0)
				putProcess.await();
			enqueue(toPut);
		} finally {
			lock.unlock();
		}
	}

	private List<Integer> dequeue() {
		List newList = new ArrayList();
		newList.addAll(list);
		list.clear();
		putProcess.signal();
		return newList;
	}

	private void enqueue(List<E> vals) {
		checkNotNull(list);
		list.addAll(vals);
		takeProcess.signal();
	}

	private static void checkNotNull(Object v) {
		if (v == null)
			throw new NullPointerException();
	}

	public static void main(String[] args) throws InterruptedException {
		SimulateBlockingQueue queue = new SimulateBlockingQueue();

		new Thread(new TakeRunner(queue)).start();

		Thread.sleep(5000);

		new Thread(new PutRunner(queue)).start();
	}

}

class PutRunner implements Runnable {
	SimulateBlockingQueue<Integer> testQueue;

	public PutRunner(SimulateBlockingQueue<Integer> testQueue) {
		this.testQueue = testQueue;
	}

	private List<Integer> datas = new ArrayList<Integer>();
	private int i = 0;

	public void run() {

		while (true) {
			try {
				datas.clear();
				datas.add(++i);
				testQueue.put(datas);
				System.out.println("put a num : " + i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("put method was interrupted");
			}
		}
	}
}
class TakeRunner implements Runnable {
	SimulateBlockingQueue<Integer> testQueue;

	public TakeRunner(SimulateBlockingQueue<Integer> testQueue) {
		this.testQueue = testQueue;
	}

	public void run() {
		while (true) {
			try {
				List<Integer> result = testQueue.take();
				String resStr = "";
				for (Integer t : result) {
					resStr += t;
				}
				System.out.println("take a result size : " + resStr);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("poll method was interrupted");
			}
		}
	}
}
