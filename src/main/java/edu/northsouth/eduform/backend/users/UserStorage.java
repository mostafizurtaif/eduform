package edu.northsouth.eduform.backend.users;

import java.io.*;

/**
 *
 * @author Taif
 * @param <T>
 */
public class UserStorage<T extends Serializable & UserIdentifiable> {

    private final String dirPath;
    private final String filePrefix;
    private final Class<T> type;

    public UserStorage(Class<T> type) {
        this.type = type;
        String typeName = type.getSimpleName().toLowerCase();

        this.dirPath = "./src/main/java/edu/northsouth/eduform/backend/database/users/" + typeName + "s/";
        this.filePrefix = typeName + "_";

        new File(this.dirPath).mkdirs();
    }

    public void save(T user) throws IOException {
        String filename = dirPath + filePrefix + user.getId() + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(user);
        }
    }

    public T load(String id) throws IOException, ClassNotFoundException {
        String filename = dirPath + filePrefix + id + ".ser";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (T) ois.readObject();
        }
    }
}
