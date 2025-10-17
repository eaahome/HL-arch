DROP TABLE public."user";
CREATE TABLE IF NOT EXISTS public."user" (
    id character varying NOT NULL,
    first_name character varying NOT NULL,
    second_name character varying NOT NULL,
    birthdate character varying NOT NULL,
    sex character varying NOT NULL,
    biography character varying NULL,
    city character varying NOT NULL
);

ALTER TABLE public."user" OWNER TO hl1;

insert into "user" values ('user1', 'FIRST', 'SECOND', '1978-05-04', 'SEX', 'BIO', 'MSK');
insert into "user" values ('user2', 'FIRST2', 'SECOND2', '1978-05-04', 'SEX2', 'BIO2', 'MSK2');

CREATE TABLE IF NOT EXISTS account (
    login varchar(255) NOT NULL primary key,
    password varchar(255)
);

insert into account values ('user1', '8DUJw3vDAUsBQnBYFwmnmg==');
insert into account values ('user2', '8DUJw3vDAUsBQnBYFwmnmg==');

CREATE TABLE IF NOT EXISTS public."friend" (
    user_id character varying NOT NULL,
    friend_id character varying NOT NULL
);

CREATE TABLE IF NOT EXISTS public."post" (
    id character varying NOT NULL primary key,
    "text" character varying NOT NULL,
    author_user_id character varying NOT NULL,
    created_at TIMESTAMPTZ DEFAULT Now()
);

CREATE TABLE IF NOT EXISTS public."dialog" (
    id character varying NOT NULL primary key default gen_random_uuid(),
    "text" character varying NOT NULL,
    src_user_id character varying NOT NULL,
    dst_user_id character varying NOT NULL,
    created_at TIMESTAMPTZ DEFAULT Now()
);

insert into dialog ("text", src_user_id, dst_user_id) values ('text text text', 'user1', 'user2');
insert into dialog ("text", src_user_id, dst_user_id) values ('text text text 222222', 'user2', 'user1');
insert into dialog ("text", src_user_id, dst_user_id) values ('text text text 333 333 333', 'user1', 'user2');
