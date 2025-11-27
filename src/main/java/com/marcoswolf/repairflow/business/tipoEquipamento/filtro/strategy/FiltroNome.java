package com.marcoswolf.repairflow.business.tipoEquipamento.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;

public class FiltroNome implements ITipoEquipamentoFiltroStrategy {
    private final String nome;

    public FiltroNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean aplicar(TipoEquipamento tipoEquipamento) {
        if (nome == null || nome.isBlank()) return true;
        return tipoEquipamento.getNome() != null && tipoEquipamento.getNome().toLowerCase().contains(nome.toLowerCase().trim());
    }
}
