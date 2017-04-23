package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue
    @Column(name = "document_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mark_id")
    private Mark mark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @Column(nullable = false)
    private Long code;

    @Column(nullable = false)
    private Integer lastVersion =1;

    private String file;

    @Column(nullable = false)
    private String initDate;

    private String lastChangeDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_id")
    private Company executor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "developer_id")
    private Company developer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Modification> modifications=new ArrayList<>();

}
