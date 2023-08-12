package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.DespesaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.DespesaDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.modelos.Despesa;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import br.com.dbc.vemser.walletlife.repository.DespesaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    public DespesaDTO adicionarDespesa(DespesaCreateDTO despesa) throws RegraDeNegocioException {
        try {
            Despesa despesaConvertida = objectMapper.convertValue(despesa, Despesa.class);
            Despesa despesaCriada = despesaRepository.save(despesaConvertida);
            DespesaDTO novaDespesa = this.convertToDTO(despesaCriada);

            return novaDespesa;
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
        return null;

    }

    // remoção
    public void removerDespesa(Integer idDespesa) throws RegraDeNegocioException {
        despesaRepository.deleteById(idDespesa);
    }

    // atualização de um objeto
    public DespesaDTO editarDespesa(Integer id, DespesaCreateDTO despesa) throws RegraDeNegocioException {
        try {
            Optional<Despesa> despesaExiteOp = despesaRepository.findById(id);
            if (despesaExiteOp.isEmpty()){
                throw new RegraDeNegocioException("Despesa não encontrada");
            }
            Despesa despesaDados = objectMapper.convertValue(despesa,Despesa.class);
            Despesa despesaExiste = despesaExiteOp.get();

            BeanUtils.copyProperties(despesaDados,despesaExiste, "idDespesa");

            Despesa despesaAtualizada = despesaRepository.save(despesaExiste);
            DespesaDTO despesaDTO = objectMapper.convertValue(despesaAtualizada,DespesaDTO.class);

            return despesaDTO;
        } catch (RegraDeNegocioException e){
            throw new RuntimeException(e);
        }
    }

    // leitura
    public List<DespesaDTO> listarDespesaByIdUsuario(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(idUsuario);
        Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);

        if (usuarioById != null) {
            List<Despesa> despesas = despesaRepository.listDespesaListByIdFK(idUsuario);
            List<DespesaDTO> despesasDTO = this.convertToDTOList(despesas);
            return despesasDTO;
        } else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    public List<DespesaDTO> listar() {
        List<Despesa> despesas = despesaRepository.findAll();
        List<DespesaDTO> despesaDTOS = this.convertToDTOList(despesas);
        return despesaDTOS;
    }

    public DespesaDTO buscarById(Integer idDespesa) throws RegraDeNegocioException {
        try {
            Optional<Despesa> despesaExisteOp = despesaRepository.findById(idDespesa);
            if (despesaExisteOp.isEmpty()) {
                throw new RegraDeNegocioException("Despesa não encontrada");
            }
            Despesa despesaExiste = despesaExisteOp.get();
            DespesaDTO despesaDTO = objectMapper.convertValue(despesaExiste, DespesaDTO.class);
            return despesaDTO;

        } catch (RegraDeNegocioException e) {
            throw new RuntimeException();
        }
    }

    private DespesaDTO convertToDTO(Despesa despesa) {
        DespesaDTO despesaDTO = objectMapper.convertValue(despesa, DespesaDTO.class);
        return despesaDTO;
    }

    private List<DespesaDTO> convertToDTOList(List<Despesa> listaDespesas) {
        return listaDespesas.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }
}