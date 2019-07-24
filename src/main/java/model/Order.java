package model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Olya_Orders")
public class Order {

    @Id
    @Column(name = "orderId")
    private String orderId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "moment")
    private String moment;
    @Column(name = "sum")
    private  String sum;
    @Column(name = "counterparty_uuid")
    private  String counterparty_uuid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId")
    private Client client;
}
