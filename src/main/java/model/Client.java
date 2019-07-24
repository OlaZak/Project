package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Olya_Clients")
public class Client {

    @Id
    @Column(name = "clientId")
    private String clientId;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
