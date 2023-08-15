package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.InvestimentoCreateDTO;
import br.com.dbc.vemser.walletlife.dto.InvestimentoDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.modelos.Investimento;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import br.com.dbc.vemser.walletlife.repository.InvestimentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvestimentoService {
    private InvestimentoRepository investimentoRepository;
    private UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    // criação de um objeto
    public InvestimentoDTO adicionarInvestimento(InvestimentoCreateDTO investimento) throws RegraDeNegocioException {
        UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(investimento.getIdUsuario());
        Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);

        if (usuarioConvertido != null){
            Investimento entity = objectMapper.convertValue(investimento, Investimento.class);

            entity.setValor(investimento.getValor());
            entity.setDescricao(investimento.getDescricao());
            entity.setDataInicio(investimento.getDataInicio());
            entity.setTipo(investimento.getTipo());
            entity.setCorretora(investimento.getCorretora());
            entity.setUsuario(usuarioConvertido);

            Investimento investimentoAdicionado = investimentoRepository.save(entity);

            if (investimentoAdicionado != null){
                InvestimentoDTO investimentoDTO = convertToDTO(investimentoAdicionado);
                return investimentoDTO;
            }else{
                throw new RegraDeNegocioException("Investimento não adicionado");
            }
        }else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    // remoção
    public void removerInvestimento(Integer id) {

        investimentoRepository.deleteById(id);
    }

    // atualização de um objeto
    public InvestimentoDTO editarInvestimento(Integer id, InvestimentoCreateDTO investimento) throws RegraDeNegocioException  {
        try {
            Optional<Investimento> investimentoRecuperado = investimentoRepository.findById(id);
            if (investimentoRecuperado.isEmpty()) {
                throw new RegraDeNegocioException("Investimento não encontrado");
            }
            Investimento investimentoDados = objectMapper.convertValue(investimento, Investimento.class);
            Investimento investimentoExiste = investimentoRecuperado.get();

            BeanUtils.copyProperties(investimentoDados, investimentoExiste, "idInvestimento", "usuario");

            Investimento investimentoAtualizado = investimentoRepository.save(investimentoExiste);
            InvestimentoDTO investimentoDTO = objectMapper.convertValue(investimentoAtualizado, InvestimentoDTO.class);

            return investimentoDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    // leitura por id do investimento
    public InvestimentoDTO buscarById(Integer idInvestimento) {
        try {
            Investimento investimento = investimentoRepository.findById(idInvestimento).get();
            InvestimentoDTO investimentoDTO = convertToDTO(investimento);
            if (investimento.getIdInvestimento() == null){
                throw new RegraDeNegocioException("Investimento não encontrado");
            }

            return investimentoDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    // leitura geral
    public List<Investimento> listarTodos() {
        return investimentoRepository.findAll();
    }

    // Leitura por usuario
    public List<InvestimentoDTO> buscarByIdUsuario(Integer idUsuario) throws RegraDeNegocioException   {
        UsuarioDTO usuarioById = usuarioService.listarPessoasPorId(idUsuario);
        Usuario usuarioConvertido = objectMapper.convertValue(usuarioById, Usuario.class);

        if (usuarioById != null){
            List<Investimento> investimento = investimentoRepository.listInvestimentoListByIdUsuario(idUsuario);
            List<InvestimentoDTO> investimentoDTO = convertToDTOList(investimento);

            return investimentoDTO;
        }else {
            throw new RegraDeNegocioException("Usuario não encontrado");
        }
    }

    public Investimento convertToEntity(InvestimentoCreateDTO dto) {
        return objectMapper.convertValue(dto, Investimento.class);
    }

    public InvestimentoDTO convertToDTO(Investimento entity) {
        return objectMapper.convertValue(entity, InvestimentoDTO.class);
    }

    public List<InvestimentoDTO> convertToDTOList(List<Investimento> investimentos) {
        return investimentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
