package oyster2;

public class Exchange {
	private String remoteHost;
	private int exchangeType;
	public final  int initorExchange = 0;
	public final  int updateExchange = 1;
	/**
	 * The KaonP2P facility.
	 */
	private Oyster2Factory mKaon = Oyster2Factory.sharedInstance();
	private Oyster2Host localHost = mKaon.getLocalHost();
	public Exchange(String remoteHost, int exchangeType){
		this.remoteHost = remoteHost;
		this.exchangeType = exchangeType;
	}
	public String getRemoteHost(){
		return this.remoteHost;
	}
	public int getExchangeType(){
		return this.exchangeType;
	}

}
