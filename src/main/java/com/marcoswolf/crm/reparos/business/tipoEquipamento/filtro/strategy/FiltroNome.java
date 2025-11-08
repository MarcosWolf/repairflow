package com.marcoswolf.crm.reparos.business.tipoEquipamento.filtro.strategy;

import com.marcoswolf.crm.reparos.infrastructure.entities.TipoEquipamento;

public class FiltroNome implements TipoEquipamentoFiltroStrategy {
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
