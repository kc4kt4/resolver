create sequence application_gen start 1 increment 1;

create table application (
   application_id bigint not null,

   primary key(application_id)
);

create table individual (
  application_id bigint not null,
  name varchar(255) not null,
  surname varchar(255) not null,
  patronymic varchar(255) not null,

  primary key (application_id)
);

create table company (
  application_id bigint not null,
  director_name varchar(255) not null,
  director_surname varchar(255) not null,
  company_name varchar(255) not null,

  primary key (application_id)
);

alter table individual add constraint individual_application_fk foreign key(application_id) references application;
alter table company add constraint company_application_fk foreign key(application_id) references application;