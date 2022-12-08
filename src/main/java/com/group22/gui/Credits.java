package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class Credits extends MenuPane {
    public Credits() {
        this.addH1("CREDITS");
        this.addParagraph("LIAM CLEGG");
        this.addParagraph("SAM AUSTIN");
        this.addParagraph("RHYS MCGUIRE");
        this.addParagraph("CELLAN LEES");
        this.addParagraph("LEWIS MEEKINGS");
        this.addParagraph("STEFFAN BORLAND");
        this.addParagraph("EZANA TAREKE");
        this.addButton("EXIT", () -> Game.getInstance().setGameState(GameState.Start));
    }
}
