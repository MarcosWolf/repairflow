package com.marcoswolf.crm.reparos.ui.controller.reparo;

import com.marcoswolf.crm.reparos.business.cliente.IClienteConsultaService;
import com.marcoswolf.crm.reparos.business.equipamento.EquipamentoConsultaService;
import com.marcoswolf.crm.reparos.business.statusReparo.StatusReparoConsultaService;
import com.marcoswolf.crm.reparos.infrastructure.entities.*;
import com.marcoswolf.crm.reparos.ui.handler.reparo.ReparoExcluirAction;
import com.marcoswolf.crm.reparos.ui.handler.reparo.ReparoFormData;
import com.marcoswolf.crm.reparos.ui.handler.reparo.ReparoSalvarAction;
import com.marcoswolf.crm.reparos.ui.interfaces.DataReceiver;
import com.marcoswolf.crm.reparos.ui.navigation.ViewNavigator;
import com.marcoswolf.crm.reparos.ui.utils.ComboBoxUtils;
import com.marcoswolf.crm.reparos.ui.utils.ParseUtils;
import com.marcoswolf.crm.reparos.ui.utils.TextFieldUtils;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ReparoFormController implements DataReceiver<Reparo> {
    private final ViewNavigator navigator;
    private final ReparoSalvarAction salvarAction;
    private final ReparoExcluirAction excluirAction;
    private final IClienteConsultaService clienteConsultaService;
    private final EquipamentoConsultaService equipamentoConsultaService;
    private final StatusReparoConsultaService statusReparoConsultaService;

    private Reparo reparoAtual;
    private final ObservableList<PecaPagamento> pecas = FXCollections.observableArrayList();

    @FXML private AnchorPane rootPane;
    @FXML private Label lblTitulo;
    @FXML private Button btnExcluir;
    @FXML private ComboBox<Cliente> comboCliente;
    @FXML private ComboBox<Equipamento> comboEquipamento;
    @FXML private ComboBox<StatusReparo> comboStatus;
    @FXML private DatePicker dateEntrada, dateSaida, datePagamento;
    @FXML private TextArea txtDescricaoProblema, txtServicoExecutado;
    @FXML private TextField txtNumeroSerie, txtValorServico, txtDesconto, txtValorTotal;
    @FXML private TextField txtPecaDescricao, txtPecaQuantidade, txtPecaValorUnitario;
    @FXML private TableView<PecaPagamento> tabela;
    @FXML private TableColumn<PecaPagamento, String> colDescricao;
    @FXML private TableColumn<PecaPagamento, Integer> colQuantidade;
    @FXML private TableColumn<PecaPagamento, String> colValorUnitario;
    @FXML private TableColumn<PecaPagamento, String> colValorTotalLinha;
    @FXML private TableColumn<PecaPagamento, Void> colRemover;

    private static final NumberFormat FORMATADOR_MOEDA =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private static final String GERENCIAR_PATH = "/fxml/reparo/reparo-gerenciar.fxml";

    @FXML
    public void initialize() {
        configurarCampos();
        alimentarCombos();
        configurarTabelaPecas();
        configurarRecalculo();
        configurarListenerCliente();
        configurarListenerEquipamento();
    }

    private void configurarCampos() {
        TextFieldUtils.aplicarMascaraNumerica(txtValorServico);
        TextFieldUtils.aplicarMascaraNumerica(txtDesconto);
        txtValorTotal.setEditable(false);
    }

    private void alimentarCombos() {
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

        ComboBoxUtils.carregarCombo(
                comboEquipamento,
                FXCollections.observableArrayList(), // vazio
                eq -> eq.getModelo() != null ? eq.getModelo() : "Sem modelo",
                () -> {
                    Equipamento e = new Equipamento();
                    e.setId(0L);
                    e.setModelo("Selecione");
                    return e;
                }
        );
        comboEquipamento.setDisable(true);

        ComboBoxUtils.carregarCombo(
                comboStatus,
                statusReparoConsultaService.listarTodos(),
                StatusReparo::getNome,
                () -> {
                    StatusReparo s = new StatusReparo();
                    s.setId(0L);
                    s.setNome("Selecione");
                    return s;
                }
        );

        configurarAcaoComboCliente();
    }

    private void configurarAcaoComboCliente() {
        comboCliente.valueProperty().addListener((obs, antigo, novoCliente) -> {
            if (novoCliente == null || novoCliente.getId() == 0) {
                comboEquipamento.getItems().clear();
                comboEquipamento.getSelectionModel().clearSelection();
                comboEquipamento.setDisable(true);
                return;
            }

            var equipamentos = equipamentoConsultaService.listarPorClienteId(novoCliente.getId());

            if (equipamentos == null || equipamentos.isEmpty()) {
                comboEquipamento.getItems().clear();
                comboEquipamento.setDisable(true);
                return;
            }

            comboEquipamento.setDisable(false);
            ComboBoxUtils.carregarCombo(
                    comboEquipamento,
                    equipamentos,
                    eq -> eq.getModelo() != null ? eq.getModelo() : "Sem modelo",
                    () -> {
                        Equipamento e = new Equipamento();
                        e.setId(0L);
                        e.setModelo("Selecione");
                        return e;
                    }
            );
            comboEquipamento.getSelectionModel().selectFirst();
        });
    }

    private void configurarListenerCliente() {
        comboCliente.valueProperty().addListener((obs, oldCliente, novoCliente) -> {
            if (novoCliente != null && novoCliente.getId() != null && novoCliente.getId() > 0) {
                var equipamentosDoCliente = equipamentoConsultaService.listarPorClienteId(novoCliente.getId());
                ComboBoxUtils.carregarCombo(
                        comboEquipamento,
                        equipamentosDoCliente,
                        eq -> eq.getModelo() != null ? eq.getModelo() : "Sem modelo",
                        () -> {
                            Equipamento e = new Equipamento();
                            e.setId(0L);
                            e.setModelo("Selecione");
                            return e;
                        }
                );
            } else {
                comboEquipamento.getItems().clear();
            }
        });
    }

    private void configurarListenerEquipamento() {
        comboEquipamento.valueProperty().addListener((obs, antigo, novoEquipamento) -> {
            if (novoEquipamento == null || novoEquipamento.getId() == null || novoEquipamento.getId() == 0) {
                txtNumeroSerie.clear();
            } else {
                txtNumeroSerie.setText(novoEquipamento.getNumeroSerie() != null ? novoEquipamento.getNumeroSerie() : "");
            }
        });
    }

    private void configurarTabelaPecas() {
        colDescricao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValorUnitario.setCellValueFactory(c ->
                new SimpleStringProperty(FORMATADOR_MOEDA.format(c.getValue().getValor()))
        );

        colValorTotalLinha.setCellValueFactory(c -> {
            BigDecimal valorUnitario = c.getValue().getValor();
            BigDecimal quantidade = new BigDecimal(c.getValue().getQuantidade());
            BigDecimal totalLinha = valorUnitario.multiply(quantidade);
            return new SimpleStringProperty(FORMATADOR_MOEDA.format(totalLinha));
        });

        colRemover.setCellFactory(criarBotaoRemover());
        tabela.setItems(pecas);
    }

    private Callback<TableColumn<PecaPagamento, Void>, TableCell<PecaPagamento, Void>> criarBotaoRemover() {
        return col -> new TableCell<>() {
            private final Button btn = new Button("X");

            {
                btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
                btn.setOnAction(e -> {
                    PecaPagamento p = getTableView().getItems().get(getIndex());
                    pecas.remove(p);
                    recalcularTotal();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(btn));
            }
        };
    }

    private void configurarRecalculo() {
        txtValorServico.textProperty().addListener((obs, oldV, newV) -> recalcularTotal());
        txtDesconto.textProperty().addListener((obs, oldV, newV) -> recalcularTotal());
    }

    private void recalcularTotal() {
        BigDecimal valorServico = ParseUtils.parseBigDecimal(txtValorServico);
        BigDecimal desconto = ParseUtils.parseBigDecimal(txtDesconto);
        BigDecimal totalPecas = pecas.stream()
                .map(p -> p.getValor().multiply(new BigDecimal(p.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = valorServico.add(totalPecas).subtract(desconto);
        txtValorTotal.setText(total.toString());
    }

    @FXML
    private void adicionarPeca() {
        if (txtPecaDescricao.getText().isBlank()) return;

        try {
            String descricao = txtPecaDescricao.getText();
            int quantidade = Integer.parseInt(txtPecaQuantidade.getText());
            BigDecimal valorUnitario = ParseUtils.parseBigDecimal(txtPecaValorUnitario);

            PecaPagamento nova = new PecaPagamento();
            nova.setNome(descricao);
            nova.setQuantidade(quantidade);
            nova.setValor(valorUnitario);

            pecas.add(nova);
            tabela.refresh();
            limparCamposPeca();
            recalcularTotal();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.WARNING, "Quantidade e valor devem ser numéricos.").showAndWait();
        }
    }

    private void limparCamposPeca() {
        txtPecaDescricao.clear();
        txtPecaQuantidade.clear();
        txtPecaValorUnitario.clear();
    }

    @Override
    public void setData(Reparo reparo) {
        this.reparoAtual = reparo;
        preencherFormulario(reparo);
    }

    private void preencherFormulario(Reparo reparo) {
        if (reparo == null) {
            lblTitulo.setText("Cadastrar Reparo");
            btnExcluir.setVisible(false);
            limparFormulario();
            return;
        }

        lblTitulo.setText("Editar Reparo");
        btnExcluir.setVisible(true);

        var cliente = reparo.getEquipamento().getCliente();
        comboCliente.setValue(cliente);

        var equipamentosDoCliente = equipamentoConsultaService.listarPorClienteId(cliente.getId());
        comboEquipamento.setItems(FXCollections.observableArrayList(equipamentosDoCliente));

        comboEquipamento.setValue(reparo.getEquipamento());
        comboStatus.setValue(reparo.getStatus());
        dateEntrada.setValue(reparo.getDataEntrada());
        dateSaida.setValue(reparo.getDataSaida());
        datePagamento.setValue(reparo.getPagamento() != null ? reparo.getPagamento().getDataPagamento() : null);
        txtDescricaoProblema.setText(reparo.getDescricaoProblema());
        txtServicoExecutado.setText(reparo.getServicoExecutado());
        txtValorServico.setText(String.valueOf(reparo.getPagamento().getValorServico()));
        txtDesconto.setText(String.valueOf(reparo.getPagamento().getDesconto()));
        pecas.setAll(reparo.getPagamento().getPecas() != null ? reparo.getPagamento().getPecas() : FXCollections.observableArrayList());
        recalcularTotal();
    }

    @FXML
    private void salvar() {
        var data = new ReparoFormData(
                comboEquipamento.getValue(),
                dateEntrada.getValue(),
                dateSaida.getValue(),
                txtDescricaoProblema.getText(),
                txtServicoExecutado.getText(),
                comboStatus.getValue(),
                ParseUtils.parseBigDecimal(txtValorServico),
                ParseUtils.parseBigDecimal(txtDesconto),
                datePagamento.getValue(),
                pecas
        );

        boolean sucesso = salvarAction.execute(reparoAtual, data);
        if (sucesso) voltar();
    }

    @FXML
    private void excluir() {
        boolean sucesso = excluirAction.execute(reparoAtual, null);
        if (sucesso) voltar();
    }

    @FXML
    private void voltar() {
        navigator.openViewRootPane(GERENCIAR_PATH, rootPane, null);
    }

    private void limparFormulario() {
        comboEquipamento.getSelectionModel().selectFirst();
        comboStatus.getSelectionModel().selectFirst();
        dateEntrada.setValue(LocalDate.now());
        dateSaida.setValue(null);
        datePagamento.setValue(null);
        txtDescricaoProblema.clear();
        txtServicoExecutado.clear();
        txtValorServico.clear();
        txtDesconto.clear();
        txtValorTotal.clear();
        pecas.clear();
    }
}
