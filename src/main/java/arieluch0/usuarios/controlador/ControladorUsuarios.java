package arieluch0.usuarios.controlador;

import arieluch0.usuarios.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorUsuarios {
    @Autowired private RepositorioUsuarios repositorioUsuarios;

    @GetMapping("/usuarios")
    public List<Usuario> consultarUsuarios() {
        return repositorioUsuarios.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario consultarUsuario(@PathVariable long id) {
        return repositorioUsuarios.findById(id).orElse(null);
    }

    @PostMapping("/usuarios")
    public String crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = new Usuario(usuario.getNombre(), usuario.getApellido());

        this.repositorioUsuarios.save(nuevoUsuario);

        return "OK: El usuario fue creado exitosamente.";
    }

    @PutMapping("/usuarios/{id}")
    public String editarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        Usuario usuarioEditado = this.repositorioUsuarios.findById(id).orElse(null);

        if (usuarioEditado != null) {
            usuarioEditado.setNombre(usuario.getNombre());
            usuarioEditado.setApellido(usuario.getApellido());

            this.repositorioUsuarios.save(usuarioEditado);

            return "OK: El usuario fue editado exitosamente.";
        } else {
            return "ERROR: No existe ningún usuario con el ID " + id + ".";
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public String borrarUsuario(@PathVariable long id) {
        Usuario usuarioEliminado = this.repositorioUsuarios.findById(id).orElse(null);

        if (usuarioEliminado != null) {
            this.repositorioUsuarios.delete(usuarioEliminado);

            return "OK: El usuario \"" + usuarioEliminado.getNombre() + " " + usuarioEliminado.getApellido() + "\" fue borrado exitosamente.";
        } else {
            return "ERROR: No existe ningún usuario con el ID " + id + ".";
        }
    }
}
