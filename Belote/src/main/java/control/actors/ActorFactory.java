package control.actors;


import control.actors.actors.ActorEquipe;
import control.actors.actors.ActorMatch;
import control.actors.actors.ActorTournoi;
import types.ActorType;


public class ActorFactory {

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
