package de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table("UUID_TEST")
@Data
class UuidTestEntity extends UuidEntity {}
