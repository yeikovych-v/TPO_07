package pl.pja.s28201.tpo_07.model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class CodeFormat implements Externalizable {

    private int id;
    private String body;
    private LocalDateTime timeSerialized;
    private long secondsToExpire = 10;

    public CodeFormat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTimeSerialized() {
        return timeSerialized;
    }

    public void setTimeSerialized(LocalDateTime timeSerialized) {
        this.timeSerialized = timeSerialized;
    }

    public long getSecondsToExpire() {
        return secondsToExpire;
    }

    public void setSecondsToExpire(long secondsToExpire) {
        this.secondsToExpire = secondsToExpire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeFormat that)) return false;
        return id == that.id && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body);
    }

    @Override
    public String toString() {
        return "CodeFormat{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeObject(body);
        out.writeObject(timeSerialized.toString());
        out.writeLong(secondsToExpire);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        body = (String) in.readObject();
        timeSerialized = LocalDateTime.parse((String) in.readObject());
        secondsToExpire = in.readLong();
    }
}
