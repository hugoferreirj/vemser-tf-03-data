package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.ReceitaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.ReceitaDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.walletlife.exceptions.EntidadeNaoEncontradaException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.modelos.ReceitaEntity;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import br.com.dbc.vemser.walletlife.repository.ReceitaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;


    // criação
    public ReceitaDTO adicionarReceita(ReceitaCreateDTO receita) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(receita.getIdFK());
        Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);

        if (usuarioConvertido != null) {
            ReceitaEntity entity = objectMapper.convertValue(receita, ReceitaEntity.class);

            ReceitaEntity receitaAdicionada = receitaRepository.save(entity);
            ReceitaDTO receitaDTO = convertToDTO(receitaAdicionada);

            return receitaDTO;
        } else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    // remoção
    public void removerReceita(Integer idReceita) {
        ReceitaEntity receita = null;
        try {
            receita = retornarReceitaPeloId(idReceita);
        } catch (EntidadeNaoEncontradaException e) {
            e.printStackTrace();
        }
        receitaRepository.delete(receita);
    }

    // atualização
    public ReceitaDTO editarReceita(Integer id, ReceitaDTO receita) throws RegraDeNegocioException, EntidadeNaoEncontradaException {
        receita.setId(id);

        ReceitaDTO entityDTO = convertToDTO(retornarReceitaPeloId(id));

        ReceitaEntity entity = objectMapper.convertValue(entityDTO, ReceitaEntity.class);

        if (entity != null) {
            entity.setDescricao(receita.getDescricao());
            entity.setValor(receita.getValor());
            entity.setIdFK(receita.getIdFK());
            entity.setBanco(receita.getBanco());
            entity.setEmpresa(receita.getEmpresa());
            receitaRepository.save(entity);
        }
        return convertToDTO(entity);
    }

    // leitura geral
    public List<ReceitaDTO> listarTodos() {
        List<ReceitaEntity> receitas = receitaRepository.findAll();
        List<ReceitaDTO> receitasDTO = this.convertToDTOList(receitas);
        return receitasDTO;
    }

    // Leitura por usuario
//    public List<ReceitaDTO> buscarByIdUsuario(Integer idUsuario) throws RegraDeNegocioException {
//        try {
//            UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(idUsuario);
//            Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);
//
//            if (usuarioConvertido != null) {
//                List<ReceitaEntity> receitas = receitaRepository.listarPorIdUsuario(idUsuario);
//                List<ReceitaDTO> receitasDTO = this.convertToDTOList(receitas);
//                return receitasDTO;
//            } else {
//                throw new RegraDeNegocioException("Usuario não encontrado");
//            }
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public ReceitaDTO buscarById(Integer id) throws EntidadeNaoEncontradaException {
        return convertToDTO(retornarReceitaPeloId(id));
    }

    public ReceitaEntity retornarReceitaPeloId(Integer id) throws EntidadeNaoEncontradaException {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Receita não encontrada"));
    }

    private ReceitaDTO convertToDTO(ReceitaEntity receita) {
        ReceitaDTO receitaDTO = objectMapper.convertValue(receita, ReceitaDTO.class);

        return receitaDTO;
    }

    private List<ReceitaDTO> convertToDTOList(List<ReceitaEntity> listaReceitas) {
        return listaReceitas.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

}
