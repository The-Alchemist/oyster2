package org.neon_toolkit.registry.oyster2;

import org.neon_toolkit.registry.oyster2.server.StartServer;




public class OysterInitiator implements Runnable{
	String[] arguments = new String[] {};
	

	/**
	 * Shutdown or not.
	 */
	private boolean mShutdownFlag = false;
	
	/**
	 * Constructs the Exchanger.
	 */
	public OysterInitiator(String[] args) {
		arguments=args;
	}
	
	
	
	/**
	 * Sleeps the delivered amount of time.
	 *
	 * @param time the time to sleep.
	 * @return <code>true</code> if the sleep was successful, <code>false</code> if interrupted.
	 */
	private boolean sleep(long time) {
		try {
			Thread.sleep(time);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}

	public void justWait(){
		//long lastTime = System.currentTimeMillis();
		while(true){
			if (mShutdownFlag)
				return;
			this.sleep(120000);
		}
	}

	/**
	 * Shutdown.
	 */
	synchronized public void shutdown() {
		mShutdownFlag = true;
	}
	/**
	 * Starts the Exchanger.
	 */
	public void run(){
			if (mShutdownFlag)
				return;
			
			try {
				StartServer.main(arguments);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				shutdown();
				e.printStackTrace();
			}
			justWait();
	}

}
