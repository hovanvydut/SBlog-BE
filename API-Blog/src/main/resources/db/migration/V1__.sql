create table article (
                         id bigint not null auto_increment,
                         content TEXT not null,
                         count_view bigint,
                         created_at datetime(6) not null,
                         deleted_at datetime(6),
                         last_updated_at datetime(6),
                         published_at datetime(6),
                         scope TINYINT DEFAULT 0 not null,
                         slug varchar(255) not null,
                         status TINYINT DEFAULT 0 not null,
                         thumbnail varchar(255),
                         title varchar(255) not null,
                         transliterated varchar(255) not null,
                         type integer,
                         user_id bigint not null,
                         category_id bigint not null,
                         primary key (id)
) engine=InnoDB;

create table article_participant (
                                     article_id bigint not null,
                                     user_id bigint not null,
                                     primary key (article_id, user_id)
) engine=InnoDB;

create table article_tag (
                             article_id bigint not null,
                             tag_id bigint not null,
                             primary key (article_id, tag_id)
) engine=InnoDB;

create table article_vote (
                              article_id bigint not null,
                              user_id bigint not null,
                              created_at datetime(6) not null,
                              vote TINYINT not null,
                              primary key (article_id, user_id)
) engine=InnoDB;

create table bookmark_article (
                                  article_id bigint not null,
                                  user_id bigint not null,
                                  created_at datetime(6),
                                  type TINYINT not null,
                                  primary key (article_id, user_id)
) engine=InnoDB;

create table category (
                          id bigint not null auto_increment,
                          description varchar(255),
                          image varchar(255),
                          name varchar(255) not null,
                          slug varchar(255) not null,
                          primary key (id)
) engine=InnoDB;

create table comment (
                         id bigint not null auto_increment,
                         content varchar(5000),
                         created_at datetime(6) not null,
                         image_slug varchar(255),
                         is_root TINYINT(1) DEFAULT 1,
                         updated_at datetime(6),
                         article_id bigint not null,
                         from_user_id bigint not null,
                         primary key (id),
                         check (content IS NOT NULL OR image_slug IS NOT NULL)
) engine=InnoDB;

create table follower (
                          from_user_id bigint not null,
                          to_user_id bigint not null,
                          created_at datetime(6) not null,
                          primary key (from_user_id, to_user_id)
) engine=InnoDB;

create table parent_child_comment (
                                      level TINYINT UNSIGNED not null,
                                      child_comment_id bigint not null,
                                      parent_comment_id bigint not null,
                                      primary key (child_comment_id, parent_comment_id)
) engine=InnoDB;

create table password_reset_token (
                                      user_id bigint not null,
                                      created_at datetime(6) not null,
                                      expire_at datetime(6) not null,
                                      token varchar(255) not null,
                                      primary key (user_id)
) engine=InnoDB;

create table role (
                      id bigint not null auto_increment,
                      name varchar(50) not null,
                      primary key (id)
) engine=InnoDB;

create table series_article (
                                article_id bigint not null,
                                series_id bigint not null,
                                primary key (article_id, series_id)
) engine=InnoDB;

create table tag (
                     id bigint not null auto_increment,
                     created_at datetime(6) not null,
                     description varchar(255),
                     image varchar(255),
                     last_edited_at datetime(6),
                     name varchar(32) not null,
                     slug varchar(255) not null,
                     primary key (id)
) engine=InnoDB;

create table user (
                      id bigint not null auto_increment,
                      avatar varchar(255) not null,
                      biography varchar(255),
                      birthday date,
                      created_at datetime(6) not null,
                      email varchar(255) not null,
                      enabled bit not null,
                      fullname varchar(32) not null,
                      gender varchar(10) not null,
                      host_avatar varchar(255) not null,
                      is_using_2fa bit not null,
                      password varchar(255) not null,
                      secret varchar(255),
                      username varchar(32) not null,
                      primary key (id)
) engine=InnoDB;

create table user_image (
                            id bigint not null auto_increment,
                            created_at datetime(6) not null,
                            host varchar(255) not null,
                            slug varchar(255) not null,
                            user_id bigint not null,
                            primary key (id)
) engine=InnoDB;

create table user_role (
                           user_id bigint not null,
                           role_id bigint not null,
                           primary key (user_id, role_id)
) engine=InnoDB;

create table verification_token (
                                    user_id bigint not null,
                                    created_at datetime(6) not null,
                                    expire_at datetime(6) not null,
                                    token varchar(255) not null,
                                    primary key (user_id)
) engine=InnoDB; create index IDXaxp71ccxxbow51kwcjc8vcoxk on article (status);

alter table article
    drop index UK_lc76j4bqg2jrk06np18eve5yj;

alter table article
    add constraint UK_lc76j4bqg2jrk06np18eve5yj unique (slug);

alter table category
    drop index UK_46ccwnsi9409t36lurvtyljak;

alter table category
    add constraint UK_46ccwnsi9409t36lurvtyljak unique (name);

alter table category
    drop index UK_hqknmjh5423vchi4xkyhxlhg2;

alter table category
    add constraint UK_hqknmjh5423vchi4xkyhxlhg2 unique (slug);

alter table password_reset_token
    drop index UK_g0guo4k8krgpwuagos61oc06j;

alter table password_reset_token
    add constraint UK_g0guo4k8krgpwuagos61oc06j unique (token);

alter table tag
    drop index UK_1wdpsed5kna2y38hnbgrnhi5b;

alter table tag
    add constraint UK_1wdpsed5kna2y38hnbgrnhi5b unique (name);

alter table tag
    drop index UK_1afk1y1o95l8oxxjxsqvelm3o;

alter table tag
    add constraint UK_1afk1y1o95l8oxxjxsqvelm3o unique (slug);

alter table user
    drop index UK_ob8kqyqqgmefl0aco34akdtpe;

alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table user
    drop index UK_sb8bbouer5wak8vyiiy4pf2bx;

alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);

alter table user_image
    drop index UK_iub8nmc2iv3xffuctjnb90i3y;

alter table user_image
    add constraint UK_iub8nmc2iv3xffuctjnb90i3y unique (slug);

alter table verification_token
    drop index UK_p678btf3r9yu6u8aevyb4ff0m;

alter table verification_token
    add constraint UK_p678btf3r9yu6u8aevyb4ff0m unique (token);

alter table article
    add constraint FKbc2qerk3l47javnl2yvn51uoi
        foreign key (user_id)
            references user (id);

alter table article
    add constraint FKy5kkohbk00g0w88fi05k2hcw
        foreign key (category_id)
            references category (id);

alter table article_participant
    add constraint FK5v1e8f8ebjlkbqc11i8si4b8r
        foreign key (user_id)
            references user (id);

alter table article_participant
    add constraint FKrclgytk8oq68tpw263jcni66x
        foreign key (article_id)
            references article (id);

alter table article_tag
    add constraint FKesqp7s9jj2wumlnhssbme5ule
        foreign key (tag_id)
            references tag (id);

alter table article_tag
    add constraint FKenqeees0y8hkm7x1p1ittuuye
        foreign key (article_id)
            references article (id);

alter table article_vote
    add constraint FKp8bfgu7gea52k8sa6smpj4fni
        foreign key (article_id)
            references article (id);

alter table article_vote
    add constraint FK3sbprg6pqgerriov5gqegatse
        foreign key (user_id)
            references user (id);

alter table bookmark_article
    add constraint FKd7pv9amc1yk0fdqbnhfyl8ftj
        foreign key (article_id)
            references article (id);

alter table bookmark_article
    add constraint FKit8s3fywvl835pnrpcjiqgjre
        foreign key (user_id)
            references user (id);

alter table comment
    add constraint FK5yx0uphgjc6ik6hb82kkw501y
        foreign key (article_id)
            references article (id);

alter table comment
    add constraint FKaaa0k7eumd9xi3vu65l6aol9e
        foreign key (from_user_id)
            references user (id);

alter table follower
    add constraint FK3aswa7d1w1871kcghomicyfsu
        foreign key (from_user_id)
            references user (id);

alter table follower
    add constraint FK8xgry3e14jqtkbyx0vwsnckj
        foreign key (to_user_id)
            references user (id);

alter table parent_child_comment
    add constraint FKdnv631ltkod18qtwc60c5lq9o
        foreign key (child_comment_id)
            references comment (id);

alter table parent_child_comment
    add constraint FKo3dol36vanre5xk1w131hgqun
        foreign key (parent_comment_id)
            references comment (id);

alter table password_reset_token
    add constraint FK5lwtbncug84d4ero33v3cfxvl
        foreign key (user_id)
            references user (id);

alter table series_article
    add constraint FKbx7owdsueib4u5hikevxlspri
        foreign key (article_id)
            references article (id);

alter table series_article
    add constraint FKqywffhni1o6cpoyjhqsef9m6k
        foreign key (series_id)
            references article (id);

alter table user_image
    add constraint FK5m3lhx7tcj9h9ju10xo4ruqcn
        foreign key (user_id)
            references user (id);

alter table user_role
    add constraint FKa68196081fvovjhkek5m97n3y
        foreign key (role_id)
            references role (id);

alter table user_role
    add constraint FK859n2jvi8ivhui0rl0esws6o
        foreign key (user_id)
            references user (id);

alter table verification_token
    add constraint FKrdn0mss276m9jdobfhhn2qogw
        foreign key (user_id)
            references user (id);