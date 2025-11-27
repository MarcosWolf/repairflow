package com.marcoswolf.repairflow.controller.mappers;

import com.marcoswolf.repairflow.controller.dto.PecaPagamentoRequestDTO;
import com.marcoswolf.repairflow.controller.dto.ReparoRequestDTO;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.entities.Pagamento;
import com.marcoswolf.repairflow.infrastructure.entities.PecaPagamento;
import com.marcoswolf.repairflow.infrastructure.entities.Reparo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReparoRequestMapper {
    public Reparo toEntity(ReparoRequestDTO dto) {
        var equipamentoDTO = dto.equipamentoId();
        Equipamento equipamento = new Equipamento();
        equipamento.setId(equipamentoDTO);

        Reparo reparo = new Reparo();
        reparo.setDescricaoProblema(dto.descricaoProblema());
        reparo.setServicoExecutado(dto.servicoExecutado());
        reparo.setDataEntrada(dto.dataEntrada());
        reparo.setDataSaida(dto.dataSaida());
        reparo.setEquipamento(equipamento);

        Pagamento pagamento = new Pagamento();
        pagamento.setValorServico(dto.valorServico());
        pagamento.setDesconto(dto.desconto());
        pagamento.setDataPagamento(dto.dataPagamento());

        List<PecaPagamentoRequestDTO> pecasDTO = dto.pecas();
        List<PecaPagamento> pecas = mapPecas(pecasDTO);

        pagamento.setPecas(pecas);

        return reparo;
    }

    private List<PecaPagamento> mapPecas(List<PecaPagamentoRequestDTO> pecasDTO) {
        if (pecasDTO == null || pecasDTO.isEmpty()) {
            return new ArrayList<>();
        }

        return pecasDTO.stream()
                .map(this::mapPeca)
                .collect(Collectors.toList());
    }

    private PecaPagamento mapPeca(PecaPagamentoRequestDTO dto) {
        PecaPagamento p = new PecaPagamento();

        p.setNome(dto.nome());
        p.setQuantidade(dto.quantidade());
        p.setValor(dto.valorUnitario());

        return p;
    }
}
