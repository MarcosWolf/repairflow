package com.marcoswolf.crm.reparos.business.equipamento;

import com.marcoswolf.crm.reparos.infrastructure.entities.Equipamento;
import com.marcoswolf.crm.reparos.infrastructure.repositories.EquipamentoRepository;
import com.marcoswolf.crm.reparos.infrastructure.repositories.ReparoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService implements EquipamentoConsultaService, EquipamentoComandoService {
    private final EquipamentoRepository equipamentoRepository;
    private final ReparoRepository reparoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, ReparoRepository reparoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.reparoRepository = reparoRepository;
    }

    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    public List<Equipamento> listarPorClienteId(Long clienteId) {
        if (clienteId == null) return List.of();
        return equipamentoRepository.findByCliente_Id(clienteId);
    }

    public void salvar(Equipamento equipamento) {
        equipamentoRepository.saveAndFlush(equipamento);
    }

    public void deletar(Long id) {
        var equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));

        equipamentoRepository.delete(equipamento);
    }
}
