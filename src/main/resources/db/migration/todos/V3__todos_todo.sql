CREATE schema todos;

create table todos.state(
	id serial primary key,
	name varchar (60) not null
);

create table todos.todo(
	id serial primary key,
	title varchar(80) not null,
	content varchar(550),
	creation_data TIMESTAMP default CURRENT_TIMESTAMP,
	fk_state int references todos.state(id)
);