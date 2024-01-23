package manager;

import java.io.File;

public class Managers {



    private static InMemoryTaskManager instance;



    private Managers() {

    }


    public static InMemoryTaskManager getDefault() {

        if (instance == null) {

            instance = new InMemoryTaskManager(getDefaultHistory());

        }

        return instance;

    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();



    }



}
