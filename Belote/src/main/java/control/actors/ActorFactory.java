package control.actors;


import control.actors.actors.ActorEquipe;
import control.actors.actors.ActorMatch;
import control.actors.actors.ActorTournoi;
import types.ActorType;

/**
 * Cette classe est une usine pour créer des acteurs (instances de la classe Actor).
 * Elle contient une méthode statique getActor() qui prend un paramètre de type ActorType et retourne une instance de la classe Actor correspondante.
 */
public class ActorFactory {

    /**
     * Retourne une instance de la classe Actor correspondante au type d'acteur spécifié.
     * @param at : le type d'acteur
     * @return une instance de la classe Actor correspondante
     * @throws Exception si le type d'acteur n'est pas trouvé
     */
    public static Actor getActor(ActorType at) throws Exception {
        switch (at) {
            case EQUIPE:
                return new ActorEquipe();
            case MATCH:
                return new ActorMatch();
            case TOURNOI:
                return new ActorTournoi();
            default:
                throw new Exception("Le type d'acteur " + at + " n'a pas été trouvé.");
        }
    }

}
