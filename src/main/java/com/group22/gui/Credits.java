package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class Credits extends MenuPane {
    public Credits() {
        this.addH1("CREDITS");
        this.addParagraph("LIAM CLEGG");
        this.addParagraph("SAM AUSTIN");
        this.addParagraph("RHYS");
        this.addParagraph("CELLAN");
        this.addParagraph("LEWIS");
        this.addParagraph("STEFFAN");
        this.addParagraph("EZANA");
        this.addButton("EXIT", () -> Game.getInstance().setGameState(GameState.Start));
    }
}
