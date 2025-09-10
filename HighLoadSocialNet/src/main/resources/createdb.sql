#CREATE TABLE IF NOT EXISTS user (
DROP TABLE user;
CREATE TABLE user (
    id varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    second_name varchar(255) NOT NULL,
    birthdate varchar(255) not null,
    sex varchar(255) NOT NULL,
    biography varchar(255) NOT NULL,
    city varchar(255) NOT NULL
);

insert into user values ("user1", "FIRST", "SECOND", "1978-05-04", "SEX", "BIO", "MSK");
insert into user values ("user2", "FIRST2", "SECOND2", "2000-05-04", "SEX2", "BIO2", "MSK2");


CREATE TABLE IF NOT EXISTS account (
    login varchar(255) NOT NULL primary key,
    password varchar(255)
);

insert into account values ("user1", "8DUJw3vDAUsBQnBYFwmnmg==");
insert into account values ("user2", "123");
