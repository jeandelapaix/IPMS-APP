package rw.imps.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("agent")
public class Agent extends Account {

}
