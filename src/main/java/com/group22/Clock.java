package com.group22;

public class Clock extends PickUp {
    
    private double time;

    public Clock(int posX, int posY, Sprite clockSprite, double time){
        super(posX, posY, clockSprite);
        setTime(time);
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }
    
    public void activatePickUpEffect(LandMover landMover) {
        if (landMover == Game.getInstance().getPlayer()){
            Game.getInstance().addTime(time);
        } else {
            Game.getInstance().addTime(-time);
        }
    }

}
