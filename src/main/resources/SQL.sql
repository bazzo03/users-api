CREATE KEYSPACE users_keyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

create table users_keyspace.users (id bigint, name varchar, lastname varchar, ip varchar, primary key (id));

