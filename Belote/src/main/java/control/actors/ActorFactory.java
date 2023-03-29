package control.actors;


import types.ActorType;


public class ActorFactory {

    public static Actor getActor(ActorType at) {
        switch (at) {
            case EQUIPE:
                return new ActorEquipe();
            case MATCH:
                return new ActorMatch();
            case TOURNOI:
                return new ActorTournoi();
            default:
                return null;
        }
    }

}
