package domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue
    @Column(name="project_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(optional = false)
    private Company head;
}
