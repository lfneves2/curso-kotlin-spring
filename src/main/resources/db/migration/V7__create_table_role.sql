create table role (
    id bigint not null auto_increment,
    nome varchar(255) not null,
    primary key(id)
);

insert into role values(1, 'LEITURA_ESCRITA');