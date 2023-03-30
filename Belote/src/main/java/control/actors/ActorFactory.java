package control.actors;


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
