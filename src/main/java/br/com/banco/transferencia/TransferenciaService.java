package br.com.banco.transferencia;

import br.com.banco.conta.Conta;
import br.com.banco.conta.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final ContaRepository contaRepository;

    public List<Transferencia> getTransferenciasPorConta(Long idConta) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> {
            throw new IllegalArgumentException("Nenhuma conta com este id");
        });

        return getAllTransferencias().stream()
                .filter(transferencia -> {
                    Conta c = transferencia.getConta();
                    return Objects.equals(c.getId(), conta.getId());
                })
                .collect(Collectors.toList());
    }
    public List<Transferencia> getAllTransferencias() {
        return transferenciaRepository.findAll();
    }

    public List<Transferencia> getTransferenciasPorPeriodo(String dataInicio, String dataFim) {
        ZoneOffset zoneOffset = ZoneOffset.of("+03");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate inicio = LocalDate.parse(dataInicio, formatter);
        LocalTime tempoInicio = LocalTime.of(0, 0, 0);

        LocalDate fim = LocalDate.parse(dataFim, formatter);
        LocalTime tempoFim = LocalTime.of(23, 59, 59);
        return transferenciaRepository.findByDataTransferencias(
                OffsetDateTime.of(inicio, tempoInicio, zoneOffset),
                OffsetDateTime.of(fim, tempoFim, zoneOffset));
    }

    public List<Transferencia> getTransferenciasPorOperador(String operadorTransacao) {
        Conta conta = contaRepository.findByNomeResponsavel(operadorTransacao);
        return transferenciaRepository.findAll().stream()
                .filter(transferencia -> {
                    Conta c = transferencia.getConta();
                    return Objects.equals(c.getId(), conta.getId());
                })
                .collect(Collectors.toList());
    }

    public List<Transferencia> getTransferenciasPorOperadorEPeriodo(String operadorTransacao,
                                                                    String dataInicio, String dataFim){

        Conta conta = contaRepository.findByNomeResponsavel(operadorTransacao);

        return getTransferenciasPorPeriodo(dataInicio, dataFim)
                .stream().filter(transferencia -> {
                    Conta c = transferencia.getConta();
                    return Objects.equals(c.getId(), conta.getId());
                })
                .collect(Collectors.toList());
    }

}
