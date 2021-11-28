package br.edu.unifio.setimoprojeto.bean;

import br.edu.unifio.setimoprojeto.domain.Usuario;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
@Data
public class UsuarioBean {
    private Usuario usuario;

    private List<Usuario> usuarios;


    public void incluirUsuario() {

        usuario = new Usuario();

    }

    public void listar() {
        String json = "{}";
        try {
            json = Jsoup.connect("http://localhost:3000/usuario/-1").ignoreContentType(true).get().toString();
            json = json.replace("<html>", "").replace("</html>", "").replace("<body>", "").replace("</body>", "").replace("<head>", "").replace("</head>", "");
            System.out.println(json);
        } catch (Exception caso) {
            Messages.addGlobalError(caso.toString());
        }

        Gson gson = new Gson();
        Usuario[] arrayUsuarios = gson.fromJson(json, Usuario[].class);
        usuarios = Arrays.asList(arrayUsuarios);
    }

    public void editar(Usuario usuario) {
        Faces.setFlashAttribute("Usuario", usuario);
        Faces.navigate("usuario-editar.xhtml?faces-redirect=true");
    }

    public void car() {
        usuario = Faces.getFlashAttribute("Usuario");


        if (usuario == null) {
            Faces.navigate("usuario-listar.xhtml?faces-redirect=true");
        }
    }

    public void salvar() {
        try {
            if (usuario.getUSR_ID() == null) {
                org.apache.http.client.HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost("http://localhost:3000/usuario/insert");


                List<NameValuePair> params = new ArrayList<>(2);
                params.add(new BasicNameValuePair("USR_EMAIL", usuario.getUSR_EMAIL()));
                params.add(new BasicNameValuePair("USR_SENHA", usuario.getUSR_SENHA()));
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                HttpResponse response = httpclient.execute(httppost);

            } else {
                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost("http://localhost:3000/usuario/update");


                List<NameValuePair> params = new ArrayList<NameValuePair>(3);
                params.add(new BasicNameValuePair("USR_ID", usuario.getUSR_ID().toString()));
                params.add(new BasicNameValuePair("USR_EMAIL", usuario.getUSR_EMAIL()));
                params.add(new BasicNameValuePair("USR_SENHA", usuario.getUSR_SENHA()));
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                HttpResponse response = httpclient.execute(httppost);
            }

            Messages.addFlashGlobalInfo("Usuario salvo");

            Faces.navigate("usuario-listar.xhtml?faces-redirect=true");

        } catch (Exception excecao) {
            Messages.addGlobalError("Usuario nao encontrado");
        }
    }

    public void excluir() {
        try {
            org.apache.http.client.HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost("http://localhost:3000/usuario/delete");


            List<NameValuePair> params = new ArrayList<>(1);
            params.add(new BasicNameValuePair("USR_ID", usuario.getUSR_ID().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpclient.execute(httppost);


            Messages.addFlashGlobalInfo("Usuario excluído");

            Faces.navigate("usuario-listar.xhtml?faces-redirect=true");
        } catch (Exception caso) {
            Messages.addGlobalError("Usuario nao encontrado");
        }
    }

    public void selecionarExclusao(Usuario usuario) {
        Faces.setFlashAttribute("Usuario", usuario);
        Faces.navigate("usuario-exclusao.xhtml?faces-redirect=true");
    }

}