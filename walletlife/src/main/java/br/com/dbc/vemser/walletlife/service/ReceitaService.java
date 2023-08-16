package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.ReceitaCreateDTO;
import br.com.dbc.vemser.walletlife.dto.ReceitaDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.exceptions.EntidadeNaoEncontradaException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.modelos.Receita;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import br.com.dbc.vemser.walletlife.repository.ReceitaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;


    public ReceitaDTO create(ReceitaCreateDTO receita, Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(idUsuario);
        if (usuarioById != null) {
            Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);
            Receita entity = objectMapper.convertValue(receita, Receita.class);
            entity.setUsuario(usuarioConvertido);
            Receita receitaAdicionada = receitaRepository.save(entity);
            return convertToDTO(receitaAdicionada);
        } else {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
    }

    public void remove(Integer idReceita) {
        Receita receita = returnReceitaEntityById(idReceita);
        receitaRepository.delete(receita);
    }

    public ReceitaDTO update(Integer id, ReceitaDTO receita) {
        try {
            Optional<Receita> receitaExisteOp = receitaRepository.findById(id);
            if (receitaExisteOp.isEmpty()) {
                throw new RegraDeNegocioException("Receita não encontrada");
            }
            Receita receitaDados = objectMapper.convertValue(receita, Receita.class);
            Receita receitaExiste = receitaExisteOp.get();

            BeanUtils.copyProperties(receitaDados, receitaExiste, "id", "usuario");

            Receita receitaAtualizada = receitaRepository.save(receitaExiste);

            return convertToDTO(receitaAtualizada);
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ReceitaDTO> findAll() {
        List<Receita> receitas = receitaRepository.findAll();
        List<ReceitaDTO> receitasDTO = this.convertToDTOList(receitas);
        return receitasDTO;
    }

    public List<ReceitaDTO> findByUsuario(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(idUsuario);
        Usuario usuarioEntity = objectMapper.convertValue(usuarioById, Usuario.class);

        if (usuarioEntity != null) {
            List<Receita> receitas = receitaRepository.findByUsuario(usuarioEntity);
            List<ReceitaDTO> receitasDTO = this.convertToDTOList(receitas);
            return receitasDTO;
        } else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    public ReceitaDTO findById(Integer id) throws EntidadeNaoEncontradaException {
        return convertToDTO(returnReceitaEntityById(id));
    }

    public Receita returnReceitaEntityById(Integer id) {
        try {
            return receitaRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Receita não encontrada"));
        } catch (EntidadeNaoEncontradaException e) {
            throw new RuntimeException(e);
        }
    }

    private ReceitaDTO convertToDTO(Receita receita) {
        ReceitaDTO receitaDTO = objectMapper.convertValue(receita, ReceitaDTO.class);

        return receitaDTO;
    }

    private List<ReceitaDTO> convertToDTOList(List<Receita> listaReceitas) {
        return listaReceitas.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

}
