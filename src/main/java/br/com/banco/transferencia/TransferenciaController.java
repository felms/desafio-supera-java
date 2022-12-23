package br.com.banco.transferencia;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @GetMapping
    public List<Transferencia> getAll() {
        return transferenciaService.getAllTransferencias();
    }

    @GetMapping("{idConta}")
    public List<Transferencia> getByConta(@PathVariable Long idConta) {
        return transferenciaService.getTransferenciasPorConta(idConta);
    }

    @GetMapping("/periodo")
    public List<Transferencia> getByPeriodo(
            @RequestParam String dataInicio, @RequestParam String dataFim) {

        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException(
                    "É necessario informar a data de inicio e" +
                            " a data do fim do intervale de busca."
            );
        }

        return transferenciaService.getTransferenciasPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/operador/{nome}")
    public List<Transferencia> getByOperador(@PathVariable("nome") String nome) {
        return transferenciaService.getTransferenciasPorOperador(nome);
    }

    @GetMapping("/operador/periodo")
    public List<Transferencia> getByOperadorEPeriodo(@RequestParam Map<String, String> params) {

        String dataInicio = params.get("dataInicio");
        String dataFim = params.get("dataFim");
        String operador = params.get("operador");

        if (dataInicio == null || dataFim == null || operador == null) {
            throw new IllegalArgumentException(
                    "É necessario informar o operador da transação, a data de inicio" +
                            " e a data do fim do intervale de busca."
            );
        }

        return transferenciaService.getTransferenciasPorOperadorEPeriodo(operador, dataInicio, dataFim);
    }
}
