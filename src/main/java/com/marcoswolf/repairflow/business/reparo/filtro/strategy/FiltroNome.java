package com.marcoswolf.repairflow.business.reparo.filtro.strategy;

import com.marcoswolf.repairflow.infrastructure.entities.Reparo;

public class FiltroNome implements ReparoFiltroStrategy {
    private final String termo;

    public FiltroNome(String termo) {
        this.termo = termo;
    }

    @Override
    public boolean aplicar(Reparo reparo) {
        if (termo == null || termo.isBlank()) return true;

        if (reparo.getEquipamento() == null) return false;

        var nomeEquipamento = reparo.getEquipamento().getNome();
        return nomeEquipamento != null && nomeEquipamento.toLowerCase().contains(termo.toLowerCase().trim());
    }
}
