/**
 * La classe ActorTournoi représente un acteur qui gère les tournois.
 * Elle hérite de la classe abstraite Actor.
 */
package control.actors.actors;

import control.actors.Actor;

public class ActorTournoi extends Actor {

    /**
     * Constructeur par défaut qui appelle le constructeur de la classe mère avec le
     * nom "Tournois".
     */
    public ActorTournoi() {
        super("Tournois");
    }

}