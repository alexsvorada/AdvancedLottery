package com.gmail.ianlim224.advancedlottery;

public class LotteryGrabber {
    private boolean shouldUpdate;
    private int countDownTime;
    private long buyPrice;
    private int maxTicketDefault;
    private int maxTicketDonor;
    private boolean allowBroadcast;
    private boolean popSound;
    private boolean anvilSound;
    private boolean winSound;
    private boolean shouldTitle;
    private long startingMoney;
    private String helpMenuName;
    private String lotteryMenuName;
    private String confirmMenuName;
    private String playerMenuName;
    private boolean bungeeSync;
    private double fadeInSeconds;
    private double fadeOutSeconds;
    private double staySeconds;
    private String moneyFormat;

    public LotteryGrabber() {
        setShouldUpdate(true);
        setCountDownTime(60);
        setBuyPrice(1000L);
        setMaxTicketDefault(1);
        setAllowBroadcast(true);
        setPopSound(true);
        setAnvilSound(true);
        setWinSound(true);
        setShouldTitle(true);
        setStartingMoney(0);
    }

    public String getMoneyFormat() {
        return moneyFormat;
    }

    public void setMoneyFormat(String moneyFormat) {
        this.moneyFormat = moneyFormat;
    }

    public int getMaxTicketDonor() {
        return maxTicketDonor;
    }

    public void setMaxTicketDonor(int maxTicketDonor) {
        this.maxTicketDonor = maxTicketDonor;
    }

    public boolean isShouldUpdate() {
        return shouldUpdate;
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }

    public String getHelpMenuName() {
        return helpMenuName;
    }

    public void setHelpMenuName(String helpMenuName) {
        this.helpMenuName = helpMenuName;
    }

    public String getLotteryMenuName() {
        return lotteryMenuName;
    }

    public void setLotteryMenuName(String lotteryMenuName) {
        this.lotteryMenuName = lotteryMenuName;
    }

    public String getConfirmMenuName() {
        return confirmMenuName;
    }

    public void setConfirmMenuName(String confirmMenuName) {
        this.confirmMenuName = confirmMenuName;
    }

    public String getPlayerMenuName() {
        return playerMenuName;
    }

    public void setPlayerMenuName(String playerMenuName) {
        this.playerMenuName = playerMenuName;
    }

    public long getStartingMoney() {
        return startingMoney;
    }

    public void setStartingMoney(long startingMoney) {
        this.startingMoney = startingMoney;
    }

    public boolean isShouldTitle() {
        return shouldTitle;
    }

    public void setShouldTitle(boolean shouldTitle) {
        this.shouldTitle = shouldTitle;
    }

    public boolean shouldUpdate() {
        return shouldUpdate;
    }

    public int getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(int countDownTime) {
        this.countDownTime = countDownTime;
    }

    public long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getMaxTicketDefault() {
        return maxTicketDefault;
    }

    public void setMaxTicketDefault(int i) {
        maxTicketDefault = i;
    }

    public boolean isAllowBroadcast() {
        return allowBroadcast;
    }

    public void setAllowBroadcast(boolean allowBroadcast) {
        this.allowBroadcast = allowBroadcast;
    }

    public boolean isPopSound() {
        return popSound;
    }

    public void setPopSound(boolean popSound) {
        this.popSound = popSound;
    }

    public boolean isAnvilSound() {
        return anvilSound;
    }

    public void setAnvilSound(boolean anvilSound) {
        this.anvilSound = anvilSound;
    }

    public boolean isWinSound() {
        return winSound;
    }

    public void setWinSound(boolean winSound) {
        this.winSound = winSound;
    }

    public boolean isBungeeSync() {
        return bungeeSync;
    }

    public void setBungeeSync(boolean bungeeSync) {
        this.bungeeSync = bungeeSync;
    }

    public double getFadeInSeconds() {
        return fadeInSeconds;
    }

    public double getFadeOutSeconds() {
        return fadeOutSeconds;
    }

    public double getStaySeconds() {
        return staySeconds;
    }

    public void setFadeInSeconds(double fadeInSeconds) {
        this.fadeInSeconds = fadeInSeconds;
    }

    public void setFadeOutSeconds(double fadeOutSeconds) {
        this.fadeOutSeconds = fadeOutSeconds;
    }

    public void setStaySeconds(double staySeconds) {
        this.staySeconds = staySeconds;
    }
}
