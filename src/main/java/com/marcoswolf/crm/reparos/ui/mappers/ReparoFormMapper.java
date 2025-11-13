package com.marcoswolf.crm.reparos.ui.mappers;

import com.marcoswolf.crm.reparos.infrastructure.entities.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class ReparoFormMapper {
    public Reparo toEntity(Equipamento equipamento, LocalDate dataEntrada, LocalDate dataSaida,
                           String descricaoProblema, String servicoExecutado, StatusReparo status,
                           BigDecimal valorServico, BigDecimal desconto, LocalDate dataPagamento, List<PecaPagamento> pecas) {

        Reparo reparo = new Reparo();
        reparo.setEquipamento(equipamento);
        reparo.setDataEntrada(dataEntrada);
        reparo.setDataSaida(dataSaida);
        reparo.setDescricaoProblema(descricaoProblema);
        reparo.setServicoExecutado(servicoExecutado);
        reparo.setStatus(status);

        var pagamento = new Pagamento();
        pagamento.setValorServico(valorServico);
        pagamento.setDesconto(desconto);
        pagamento.setDataPagamento(dataPagamento);
        pagamento.setPecas(pecas);

        reparo.setPagamento(pagamento);
        return reparo;
    }
}
