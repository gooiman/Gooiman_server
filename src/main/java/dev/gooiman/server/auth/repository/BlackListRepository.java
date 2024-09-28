package dev.gooiman.server.auth.repository;

import dev.gooiman.server.auth.repository.entity.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String> {

}
