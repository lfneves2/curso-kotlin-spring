create table curso (
    id bigint not null auto_increment,
    nome varchar(255) not null,
    categoria varchar(255) not null,
    primary key(id)
);

insert into curso values(1, 'Kotlin', 'Programacao');
insert into curso values(2, 'CSS', 'Front-end');