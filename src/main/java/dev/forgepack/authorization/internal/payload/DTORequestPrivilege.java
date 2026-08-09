package dev.forgepack.authorization.internal.payload;

import java.util.UUID;
import dev.forgepack.validation.api.annotation.Unique;
import dev.forgepack.core.api.payload.DTOIdentifiable;
import dev.forgepack.authorization.internal.service.ServicePrivilege;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for Privilege entity
 *
 * @author Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/
@Unique(service = ServicePrivilege.class, fields = { "name" })
public record DTORequestPrivilege(

        UUID id,
        @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
        String name
) implements DTOIdentifiable<UUID> {
}
