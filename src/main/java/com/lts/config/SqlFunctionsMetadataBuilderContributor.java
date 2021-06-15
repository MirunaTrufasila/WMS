package com.lts.config;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction(
                "group_concat",
                new SQLFunctionTemplate(
                        StandardBasicTypes.STRING,
                        "group_concat(?1)"
                )
        );
        metadataBuilder.applySqlFunction(
                "group_concat_distinct",
                new SQLFunctionTemplate(
                        StandardBasicTypes.STRING,
                        "group_concat(distinct ?1)"
                )
        );
    }
}