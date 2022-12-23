package br.com.banco.transferencia;

import br.com.banco.conta.Conta;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Transferencia {

    @Id
    private Long id;
    private OffsetDateTime dataTransferencia;
    private double valor;
    @Enumerated(EnumType.STRING)
    private Transacao tipo;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

}
