package arieluch0.usuarios.controlador;

import arieluch0.usuarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/* Esto es mágico, no hay que darle más vueltas. */
public interface RepositorioUsuarios extends JpaRepository<Usuario, Long> {
}
