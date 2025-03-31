create table if not exists app_users(
    user_id serial primary key ,
    full_name varchar(50) not null,
    email varchar(255) not null ,
    password varchar(255) not null
);
create table if not exists roles(
    role_id serial primary key,
    name varchar(50) not null
);
create table if not exists user_role(
     user_id int not null references app_users(user_id) on delete  cascade ,
    role_id int not null  references roles(role_id) on delete cascade
)   ;