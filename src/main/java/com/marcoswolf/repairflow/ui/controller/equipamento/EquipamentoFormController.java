package com.marcoswolf.repairflow.ui.controller.equipamento;

import com.marcoswolf.repairflow.business.cliente.ClienteConsultaService;
import com.marcoswolf.repairflow.business.tipoEquipamento.TipoEquipamentoConsultaService;
import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.entities.Equipamento;
import com.marcoswolf.repairflow.infrastructure.entities.TipoEquipamento;
import com.marcoswolf.repairflow.ui.handler.equipamento.action.EquipamentoExcluirAction;
import com.marcoswolf.repairflow.ui.handler.equipamento.dto.EquipamentoFormData;
import com.marcoswolf.repairflow.ui.handler.equipamento.action.EquipamentoSalvarAction;
import com.marcoswolf.repairflow.ui.interfaces.DataReceiver;
import com.marcoswolf.repairflow.ui.navigation.ViewNavigator;
import com.marcoswolf.repairflow.ui.utils.ComboBoxUtils;
import com.marcoswolf.repairflow.ui.utils.TextFieldUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipamentoFormController implements DataReceiver<Equipamento> {
    private final ViewNavigator navigator;
    private final EquipamentoSalvarAction salvarAction;
    private final EquipamentoExcluirAction excluirAction;

    private final ClienteConsultaService clienteConsultaService;
    private final TipoEquipamentoConsultaService tipoEquipamentoConsultaService;

    private Equipamento novoEquipamento;

    @FXML private AnchorPane rootPane;
    @FXML private Label lblTitulo;
    @FXML private TextField txtMarca, txtModelo, txtNumeroSerie;
    @FXML private ComboBox<TipoEquipamento> comboTipoEquipamento;
    @FXML private ComboBox<Cliente> comboCliente;
    @FXML private Button btnExcluir;

    private static final String GERENCIAR_PATH = "/fxml/equipamento/equipamento-gerenciar.fxml";

    @FXML
    public void initialize() {
        configurarCampos();
        carregarClientes();
        carregarTipoEquipamento();
    }

    private void configurarCampos() {
        TextFieldUtils.aplicarLimite(txtMarca, 50);
        TextFieldUtils.aplicarLimite(txtModelo, 50);
        TextFieldUtils.aplicarLimite(txtNumeroSerie, 150);
    }

    private void carregarClientes() {
        ComboBoxUtils.carregarCombo(
                comboCliente,
                clienteConsultaService.listarTodos(),
                Cliente::getNome,
                () -> {
                    Cliente c = new Cliente();
                    c.setId(0L);
                    c.setNome("Selecione");
                    return c;
                }
        );
    }

    private void carregarTipoEquipamento() {
        ComboBoxUtils.carregarCombo(
                comboTipoEquipamento,
                tipoEquipamentoConsultaService.listarTodos(),
                TipoEquipamento::getNome,
                () -> {
                    TipoEquipamento c = new TipoEquipamento();
                    c.setId(0L);
                    c.setNome("Selecione");
                    return c;
                }
        );
    }

    @Override
    public void setData(Equipamento equipamento) {
        this.novoEquipamento = equipamento;
        preencherFormulario(equipamento);
    }

    private void preencherFormulario(Equipamento equipamento) {
        if (equipamento == null) {
            lblTitulo.setText("Cadastrar Equipamento");
            btnExcluir.setVisible(false);
            limparCampos();
            return;
        }

        lblTitulo.setText("Editar Equipamento");
        btnExcluir.setVisible(true);

        comboTipoEquipamento.setValue(equipamento.getTipoEquipamento());
        txtMarca.setText(equipamento.getMarca());
        txtModelo.setText(equipamento.getModelo());
        txtNumeroSerie.setText(equipamento.getNumeroSerie());
        comboCliente.setValue(equipamento.getCliente());
    }

    private void limparCampos() {
        comboTipoEquipamento.getSelectionModel().selectFirst();
        txtMarca.clear();
        txtModelo.clear();
        txtNumeroSerie.clear();
        comboCliente.getSelectionModel().selectFirst();
    }

    @FXML
    private void salvar() {
        boolean sucesso = salvarAction.execute(novoEquipamento, criarFormData());
        if (sucesso) voltar();
    }

    @FXML
    private void excluir() {
        boolean sucesso = excluirAction.execute(novoEquipamento, null);
        if (sucesso) voltar();
    }

    @FXML
    private void voltar() {
        navigator.openViewRootPane(GERENCIAR_PATH , rootPane, null);
    }

    private EquipamentoFormData criarFormData() {
        return new EquipamentoFormData(
                comboTipoEquipamento.getValue(),
                txtMarca.getText(),
                txtModelo.getText(),
                txtNumeroSerie.getText(),
                comboCliente.getValue()
        );
    }



}
