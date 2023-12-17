create table usuario (
    id bigint not null auto_increment,
    nome varchar(255) not null,
    email varchar(255) not null,
    primary key(id)
);

insert into usuario values(1, 'Ana da Silva', 'ana@gmail.com');