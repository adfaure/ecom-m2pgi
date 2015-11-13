package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;

/**
 * Created by FAURE Adrien on 13/11/15.
 */
@Entity
public class MemberIdGenerator {

    @Id
    @Column(name="sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sequence;

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
}
