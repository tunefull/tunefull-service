create sequence hibernate_sequence start with 1 increment by 1;
create table clip
(
    clip_id          bigint       not null,
    album            varchar(255),
    artist           varchar(255) not null,
    begin_timestamp  integer,
    date_time_posted timestamp    not null,
    end_timestamp    integer,
    song_title       varchar(255) not null,
    track_key        varchar(255) not null,
    user_id          bigint       not null,
    primary key (clip_id)
);
create table relationship
(
    relationship_id     bigint  not null,
    friend_accepted     boolean,
    friend_relationship boolean not null,
    requested_id        bigint  not null,
    requester_id        bigint  not null,
    primary key (relationship_id)
);
create table user_profile
(
    user_id        bigint       not null,
    email          varchar(255) not null,
    favorite_genre varchar(255) not null,
    oauth          varchar(255) not null,
    username       varchar(255) not null,
    primary key (user_id)
);
create index IDX3tec6bpdraaqvsh6cognpbyse on clip (date_time_posted);
create unique index UKpo6uwivd8ct8jygubrc3v3rt0 on relationship (requester_id, requested_id);
alter table user_profile
    add constraint UK_pil844ub103gqirid42xhxx6m unique (oauth);
alter table user_profile
    add constraint UK_9551piq2wp9kh4kket0wr65vt unique (username);
alter table clip
    add constraint FKeun14qvjc74070h0rarbsk0ug foreign key (user_id) references user_profile;
alter table relationship
    add constraint FKaxm86ithcyne7dpeqwn8t2b foreign key (requested_id) references user_profile;
alter table relationship
    add constraint FKh7jjvc6gwtqfy926k5hmgfj47 foreign key (requester_id) references user_profile;
create sequence hibernate_sequence start with 1 increment by 1
create table clip (clip_id bigint not null, album varchar(255), artist varchar(255) not null, begin_timestamp integer, date_time_posted timestamp not null, end_timestamp integer, song_title varchar(255) not null, track_key varchar(255) not null, user_id bigint not null, primary key (clip_id))
create table relationship (relationship_id bigint not null, friend_accepted boolean, friend_relationship boolean not null, requested_id bigint not null, requester_id bigint not null, primary key (relationship_id))
create table user_profile (user_id bigint not null, email varchar(255) not null, favorite_genre varchar(255), oauth varchar(255) not null, username varchar(255) not null, primary key (user_id))
create index IDX3tec6bpdraaqvsh6cognpbyse on clip (date_time_posted)
create unique index UKpo6uwivd8ct8jygubrc3v3rt0 on relationship (requester_id, requested_id)
alter table user_profile add constraint UK_pil844ub103gqirid42xhxx6m unique (oauth)
alter table user_profile add constraint UK_9551piq2wp9kh4kket0wr65vt unique (username)
alter table clip add constraint FKeun14qvjc74070h0rarbsk0ug foreign key (user_id) references user_profile
alter table relationship add constraint FKaxm86ithcyne7dpeqwn8t2b foreign key (requested_id) references user_profile
alter table relationship add constraint FKh7jjvc6gwtqfy926k5hmgfj47 foreign key (requester_id) references user_profile
