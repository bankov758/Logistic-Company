CREATE SCHEMA IF NOT EXISTS logistic_company;
USE logistic_company;

create table company
(
    id   int auto_increment
        primary key,
    name varchar(45) not null,
    constraint name_UNIQUE
        unique (name)
)
    charset = utf8mb3;

create table office
(
    id         int auto_increment
        primary key,
    address    varchar(45) not null,
    company_id int         not null,
    constraint address_company
        unique (address, company_id),
    constraint fk_office_company1
        foreign key (company_id) references company (id)
            on delete cascade
)
    charset = utf8mb3;

create index fk_office_company1_idx
    on office (company_id);

create table tariff
(
    id              int auto_increment
        primary key,
    price_per_kg    float not null,
    office_discount float not null,
    company_id      int   not null,
    constraint unique_tariff_combination
        unique (price_per_kg, office_discount, company_id),
    constraint fk_tarriffs_company1
        foreign key (company_id) references company (id)
            on delete cascade
)
    charset = utf8mb3;

create index fk_tariffs_company_idx
    on tariff (company_id);

create table user
(
    id         int auto_increment
        primary key,
    username   varchar(45)  not null,
    password   varchar(256) not null,
    first_name varchar(45)  not null,
    last_name  varchar(45)  not null,
    constraint username_UNIQUE
        unique (username)
)
    charset = utf8mb3;

create table employee
(
    id         int not null
        primary key,
    company_id int not null,
    constraint fk_employee_company1
        foreign key (company_id) references company (id)
            on delete cascade,
    constraint fk_employee_user1
        foreign key (id) references user (id)
)
    charset = utf8mb3;

create table courier
(
    id int not null
        primary key,
    constraint fk_courier_employee1
        foreign key (id) references employee (id)
            on delete cascade
)
    charset = utf8mb3;

create index fk_employee_company1_idx
    on employee (company_id);

create table office_employee
(
    id        int not null
        primary key,
    office_id int not null,
    constraint fk_office_employee_employee1
        foreign key (id) references employee (id)
            on delete cascade,
    constraint fk_office_employee_office1
        foreign key (office_id) references office (id)
            on delete cascade
)
    charset = utf8mb3;

create index fk_office_employee_office1_idx
    on office_employee (office_id);

create table shipment
(
    id                    int auto_increment
        primary key,
    departure_address     varchar(45) not null,
    arrival_address       varchar(45) not null,
    weight                float       not null,
    sender_id             int         null,
    receiver_id           int         null,
    is_sent_from_office   tinyint     not null,
    is_received_in_office tinyint     not null,
    office_employee_id    int         null,
    price                 float       not null,
    sent_date             datetime    not null,
    received_date         datetime    null,
    courier_id            int         null,
    company_id            int         not null,
    constraint fk_shipment_company1
        foreign key (company_id) references company (id)
            on delete cascade,
    constraint fk_shipment_courier1
        foreign key (courier_id) references courier (id)
            on delete set null,
    constraint fk_shipment_office_employee1
        foreign key (office_employee_id) references office_employee (id)
            on delete set null,
    constraint fk_shipment_user1
        foreign key (sender_id) references user (id)
            on delete set null,
    constraint fk_shipment_user2
        foreign key (receiver_id) references user (id)
            on delete set null
)
    charset = utf8mb3;

create index fk_shipment_company1_idx
    on shipment (company_id);

create index fk_shipment_courier1_idx
    on shipment (courier_id);

create index fk_shipment_office_employee1_idx
    on shipment (office_employee_id);

create index fk_shipment_user1_idx
    on shipment (sender_id);

create index fk_shipment_user2_idx
    on shipment (receiver_id);

create table User_roles
(
    id      int auto_increment
        primary key,
    user_id int         not null,
    roles   varchar(45) null,
    constraint fk_User_roles_user1
        foreign key (user_id) references user (id)
)
    charset = utf8mb3;

create index fk_User_roles_user1_idx
    on User_roles (user_id);



