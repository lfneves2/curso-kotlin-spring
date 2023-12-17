create table resposta (
    id bigint not null auto_increment,
    mensasgem varchar(300) not null,
    data_criacao datetime not null,
    solucao int not null,
    autor_id bigint not null,
    topico_id bigint not null,
    primary key(id),
    foreign key (autor_id) references usuario(id),
    foreign key (topico_id) references topico(id)
);