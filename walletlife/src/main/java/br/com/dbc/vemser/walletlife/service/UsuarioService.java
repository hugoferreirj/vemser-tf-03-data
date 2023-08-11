package br.com.dbc.vemser.walletlife.service;

import br.com.dbc.vemser.walletlife.dto.UsuarioCreateDTO;
import br.com.dbc.vemser.walletlife.dto.UsuarioDTO;
import br.com.dbc.vemser.walletlife.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.walletlife.modelos.Usuario;
import br.com.dbc.vemser.walletlife.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Data
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;


    // criação de um objeto
    public UsuarioDTO adicionarUsuario(UsuarioCreateDTO usuario) {
        try {
            Usuario usuarioConvertido = objectMapper.convertValue(usuario, Usuario.class);
            Usuario usuarioCriado = usuarioRepository.save(usuarioConvertido);
            UsuarioDTO novoUsuario = this.convertToDTO(usuarioCriado);

//            Map<String, String> dados = new HashMap<>();
//            dados.put("nome", novoUsuario.getNomeCompleto());
//            String paragrafo = "Estamos felizes em tê-lo como usuário do Wallet Life! :) <br>" +
//                    "           Seu cadastro foi realizado com sucesso, e agora você pode organizar todas suas finanças!.<br>" +
//                    "           Aproveite para acessar nossa plataforma e descobrir mais sobre o projeto!<br>";
//            dados.put("paragrafo", paragrafo);
//            dados.put("email", novoUsuario.getEmail());
//            emailService.sendTemplateEmail(dados);

           return novoUsuario;

        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
        return null;
    }

    public void removerPessoa(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // atualização de um objeto
    public UsuarioDTO editarPessoa(Integer id, UsuarioCreateDTO usuario) {
        try {
            Optional<Usuario> usuarioExisteOp = usuarioRepository.findById(id);
            if (usuarioExisteOp.isEmpty()){
                throw new RegraDeNegocioException("Usuário não encontrado");
            }
            Usuario usuarioDados = objectMapper.convertValue(usuario, Usuario.class);
            Usuario usuarioExiste = usuarioExisteOp.get();

            BeanUtils.copyProperties(usuarioDados, usuarioExiste, "idUsuario");

            Usuario usuarioAtualizado = usuarioRepository.save(usuarioExiste);
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioAtualizado, UsuarioDTO.class);
//            Map<String, String> dados = new HashMap<>();
//            dados.put("nome", usuarioDTO.getNomeCompleto());
//            String paragrafo = "Parece que você atualizou seus dados!<br>" +
//                               "Deu tudo certo na operação.<br>" +
//                               "Pode ficar tranquile! :)";
//            dados.put("paragrafo", paragrafo);
//            dados.put("email", usuarioDTO.getEmail());
//            emailService.sendTemplateEmail(dados);
            return usuarioDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    // leitura
    public UsuarioDTO listarPessoasPorId(Integer id) {
        try {
            Optional<Usuario> usuarioExisteOp = usuarioRepository.findById(id);
            if (usuarioExisteOp.isEmpty()){
                throw new RegraDeNegocioException("Usuário não encontrado");
            }
            Usuario usuarioExiste = usuarioExisteOp.get();
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioExiste, UsuarioDTO.class);

            return usuarioDTO;
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOS = this.convertToDTOList(usuarios);
        return usuarioDTOS;
    }

    private UsuarioDTO convertToDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);
        return usuarioDTO;
    }
    private List<UsuarioDTO> convertToDTOList(List<Usuario> listaUsuarios){
        return listaUsuarios.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

}
