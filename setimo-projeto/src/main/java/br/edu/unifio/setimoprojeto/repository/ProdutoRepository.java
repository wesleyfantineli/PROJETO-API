package br.edu.unifio.setimoprojeto.repository;

import br.edu.unifio.setimoprojeto.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProdutoRepository extends JpaRepository<Produto, Integer> {

}
