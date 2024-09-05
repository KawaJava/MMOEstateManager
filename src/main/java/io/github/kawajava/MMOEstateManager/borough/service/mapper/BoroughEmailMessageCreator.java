package io.github.kawajava.MMOEstateManager.borough.service.mapper;

import io.github.kawajava.MMOEstateManager.player.model.Player;

public class BoroughEmailMessageCreator {

    public static String createEmailMessage(Player player) {
        return "\nDzień dobry, " + player.getName() +
                "\n" +
                "\nProsimy o uaktualnienie ilości złota w gminach." +
                "\n" +
                "\nDziękujemy za przeczytanie wiadomości." +
                "\n" +
                "\nPozdrawiamy" +
                "\nAdmini MMOEstateManager";
    }
}
