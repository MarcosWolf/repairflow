package com.marcoswolf.repairflow.business.reparo.filtro;

import lombok.Data;

@Data
public class ReparoFiltro {
    private String termo;
    private boolean pendentes;
    private boolean finalizados;
}
