package com.marcoswolf.crm.reparos.business.cliente;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClienteFiltro {
    private String nome;
    private boolean pendentes;
    private boolean recentes;
    private boolean comReparos;
    private boolean inativos;
    private LocalDate dataDesde;
}