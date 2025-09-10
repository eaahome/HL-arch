CREATE TABLE IF NOT EXISTS "user" (
    id varchar NOT NULL,
    first_name varchar NOT NULL,
    second_name varchar NOT NULL,
    sex varchar NOT NULL,
    age integer NOT NULL,
    city varchar NOT NULL,
    biography varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
    login varchar(255) NOT NULL primary key,
    password varchar(255)
);

insert into account values ("user1", "8DUJw3vDAUsBQnBYFwmnmg==");
insert into account values ("user2", "123");
