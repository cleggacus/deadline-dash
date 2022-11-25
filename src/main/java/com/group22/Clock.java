package com.group22;

public class Clock extends PickUp {
    
    private long time;

    public Clock(int posX, int posY, Sprite clockSprite, long time){
        super(posX, posY, clockSprite);
        setTime(time);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }
/** I don't know where the level timer is and what it is called, we do have one right?
    public void activatePickUpEffect(LandMover landMover) {
        if (landMover == Player){
            levelTimer.add(this.getTime);
        } else {
            levelTimer.minus(this.getTime);
        }
    }
    */
}
