package io.oasp.gastronomy.restaurant.tablemanagement.logic.mapper;

import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface TableMapper {

    TableEto toTableEto(TableEntity table);

    TableEntity toTableEntity(TableEto table);

    List<TableEto> toTableEtos(List<TableEntity> tables);
}
