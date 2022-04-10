CREATE SCHEMA IF NOT EXISTS auth;

CREATE  TABLE IF NOT EXISTS auth.offices (
                               id                   bigserial  NOT NULL  ,
                               office_name          varchar(100)  NOT NULL  ,
                               CONSTRAINT pk_offices PRIMARY KEY ( id ),
                               CONSTRAINT unq_offices_name_key UNIQUE ( office_name )
);

CREATE  TABLE IF NOT EXISTS auth.roles (
                             id                   bigserial  NOT NULL  ,
                             created_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
                             updated_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
                             status               boolean DEFAULT false NOT NULL  ,
                             role_name            varchar(100)  NOT NULL  ,
                             CONSTRAINT pk_roles_id PRIMARY KEY ( id ),
                             CONSTRAINT unq_roles_role_name_key UNIQUE ( role_name )
);

CREATE  TABLE IF NOT EXISTS auth.user_profile_types (
                                          id                   bigserial  NOT NULL  ,
                                          type_name            varchar(255)  NOT NULL  ,
                                          CONSTRAINT pk_user_profile_types_id PRIMARY KEY ( id ),
                                          CONSTRAINT unq_user_profile_types_name_key UNIQUE ( type_name )
);

CREATE  TABLE IF NOT EXISTS auth.users (
                                           id                   bigserial  NOT NULL  ,
                                           created_at           timestamp(3) DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
                                           updated_at           timestamp(3) DEFAULT CURRENT_TIMESTAMP   ,
                                           status               boolean DEFAULT false NOT NULL  ,
                                           username             varchar(150)  NOT NULL  ,
                                           email                varchar(150)  NOT NULL  ,
                                           pswd                 varchar(255)  NOT NULL  ,
                                           CONSTRAINT pk_users_id PRIMARY KEY ( id ),
                                           CONSTRAINT unq_users_username_key UNIQUE ( username ) ,
                                           CONSTRAINT unq_users_email_key UNIQUE ( email ),
                                           CONSTRAINT unq_users_email_username_key UNIQUE ( email, username )
);

CREATE  TABLE IF NOT EXISTS auth.users_roles (
                                                 user_id              bigint  NOT NULL  ,
                                                 role_id              bigint  NOT NULL
);

CREATE  TABLE IF NOT EXISTS auth.user_profile (
                                    id                   bigserial  NOT NULL  ,
                                    first_name           varchar(200)  NOT NULL  ,
                                    last_name            varchar(200)  NOT NULL  ,
                                    patronymic           varchar(200)    ,
                                    date_birth           date    ,
                                    place_birth          varchar(255)    ,
                                    file_avatar_path     varchar(4096)    ,
                                    is_b2b               boolean DEFAULT false NOT NULL  ,
                                    user_profile_type_id bigint  NOT NULL  ,
                                    user_id              bigint  NOT NULL  ,
                                    CONSTRAINT pk_user_profile_id PRIMARY KEY ( id ),
                                    CONSTRAINT unq_user_profile_user_id UNIQUE ( user_id )
);

CREATE  TABLE IF NOT EXISTS auth.user_profile_offices (
                                            user_profile_id      bigint  NOT NULL  ,
                                            offices_id           bigint  NOT NULL
);


ALTER TABLE auth.user_profile ADD CONSTRAINT fk_user_profile_user_profile_types FOREIGN KEY ( user_profile_type_id ) REFERENCES auth.user_profile_types( id ) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE auth.user_profile ADD CONSTRAINT fk_users_user_profile FOREIGN KEY ( user_id ) REFERENCES auth.users( id );

ALTER TABLE auth.user_profile_offices ADD CONSTRAINT fk_user_profile_offices_offices FOREIGN KEY ( offices_id ) REFERENCES auth.offices( id ) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE auth.user_profile_offices ADD CONSTRAINT fk_user_profile_offices_user_profile FOREIGN KEY ( user_profile_id ) REFERENCES auth.user_profile( id ) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE auth.users_roles ADD CONSTRAINT fk_user_roles_roles FOREIGN KEY ( role_id ) REFERENCES auth.roles( id ) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE auth.users_roles ADD CONSTRAINT fk_user_roles_users FOREIGN KEY ( user_id ) REFERENCES auth.users( id ) ON DELETE CASCADE ON UPDATE RESTRICT;

COMMENT ON TABLE auth.offices IS 'Кабинеты:\nИнвестор\nФермер\nАгроном\nПредставитель СПК\nПредставитель ТК транспортной компании';

COMMENT ON TABLE auth.roles IS 'Администратор\nАнонимус\nПользователь\nИнвестор\nФермер\nАгроном\nПредставитель СПК\nПредставитель ТК транспортной компании';

COMMENT ON TABLE auth.user_profile_types IS 'типы пользователей в системе\nфизические лица\nфизические лица (ИП)\nКрестьянские (фермерские) хозяйства\nюр лица';

COMMENT ON COLUMN auth.users.pswd IS 'Пароль';

COMMENT ON COLUMN auth.user_profile.first_name IS 'имя';

COMMENT ON COLUMN auth.user_profile.last_name IS 'фамилия';

COMMENT ON COLUMN auth.user_profile.patronymic IS 'отчество';

COMMENT ON COLUMN auth.user_profile.date_birth IS 'дата рождения';

COMMENT ON COLUMN auth.user_profile.place_birth IS 'Место рождения';

COMMENT ON COLUMN auth.user_profile.file_avatar_path IS 'путь к файлу фото';

COMMENT ON COLUMN auth.user_profile.is_b2b IS 'представитель юр. лица';

COMMENT ON COLUMN auth.user_profile.user_profile_type_id IS 'ссылка на тип пользователей';

INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_ADMIN');
INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_AGRONOMIST');
INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_ANONYMOUS');
INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_INVESTOR');
INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_SPK_REPRESENTATIVE');
INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_TK_REPRESENTATIVE');
INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_USER');
INSERT INTO auth.roles (status, role_name)
values (true, 'ROLE_FARMER');

INSERT INTO auth.user_profile_types (type_name)
values ('физическое лицо');
INSERT INTO auth.user_profile_types (type_name)
values ('физическое лицо (ИП)');
INSERT INTO auth.user_profile_types (type_name)
values ('крестьянское (фермерское) хозяйство');
INSERT INTO auth.user_profile_types (type_name)
values ('юридическое лицо');

insert into auth.offices (office_name)
values ('Агроном');
insert into auth.offices (office_name)
values ('Инвестор');
insert into auth.offices (office_name)
values ('Представитель СПК');
insert into auth.offices (office_name)
values ('Представитель ТК');
insert into auth.offices (office_name)
values ('Фермер');

