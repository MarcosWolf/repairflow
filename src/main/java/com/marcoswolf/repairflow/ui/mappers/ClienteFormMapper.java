package com.marcoswolf.repairflow.ui.mappers;

import com.marcoswolf.repairflow.infrastructure.entities.Cliente;
import com.marcoswolf.repairflow.infrastructure.entities.Endereco;
import com.marcoswolf.repairflow.infrastructure.entities.Estado;
import org.springframework.stereotype.Component;

@Component
public class ClienteFormMapper {
    public Cliente toEntity(String nome, String telefone, String email, String documento,
                            String cidade, String bairro, String cep, String logradouro,
                            Integer numero, Estado estado) {

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setDocumento(documento);

        var endereco = new Endereco();
        endereco.setCidade(cidade);
        endereco.setBairro(bairro);
        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setEstado(estado);

        cliente.setEndereco(endereco);
        return cliente;
    }
}
