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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;


    public ReceitaDTO create(ReceitaCreateDTO receita) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(receita.getUsuario().getIdUsuario());
        Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);

        if (usuarioConvertido != null) {
            Receita entity = objectMapper.convertValue(receita, Receita.class);

            Receita receitaAdicionada = receitaRepository.save(entity);
            ReceitaDTO receitaDTO = convertToDTO(receitaAdicionada);

            return receitaDTO;
        } else {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
    }

    public void remove(Integer idReceita) {
        Receita receita = null;
        try {
            receita = returnReceitaEntityById(idReceita);
        } catch (EntidadeNaoEncontradaException e) {
            e.printStackTrace();
        }
        receitaRepository.delete(receita);
    }

    public ReceitaDTO update(Integer id, ReceitaDTO receita) throws EntidadeNaoEncontradaException {
        receita.setId(id);

        ReceitaDTO entityDTO = convertToDTO(returnReceitaEntityById(id));

        Receita entity = objectMapper.convertValue(entityDTO, Receita.class);

        if (entity != null) {
            entity.setDescricao(receita.getDescricao());
            entity.setValor(receita.getValor());
            entity.setUsuario(receita.getUsuario());
            entity.setBanco(receita.getBanco());
            entity.setEmpresa(receita.getEmpresa());
            receitaRepository.save(entity);
        }
        return convertToDTO(entity);
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

    public Receita returnReceitaEntityById(Integer id) throws EntidadeNaoEncontradaException {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Receita não encontrada"));
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
