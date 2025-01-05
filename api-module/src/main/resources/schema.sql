DROP TABLE IF EXISTS MESSAGE;
DROP TABLE IF EXISTS CONVERSATION_PARTICIPANT;
DROP TABLE IF EXISTS CONVERSATION;
DROP TABLE IF EXISTS MATCHING;
DROP TABLE IF EXISTS USER;

create table MESSAGE
(
    message_id      varchar(40)   not null comment '메시지Id'
        primary key,
    conversation_id varchar(40)   not null comment '대화방Id',
    sender_id       varchar(40)   not null comment '송신인Id',
    receiver_id     varchar(40)   not null comment '수신자id',
    file_id         varchar(40)   null comment '파일id',
    message_content varchar(4000) null comment '메시지 내용',
    timestamp       datetime      not null comment '타임스탬프',
    message_status  varchar(10)   not null comment '메시지 상태 : SENT, READ, FAILED'
)
    comment '메시지 Entity';


create table MATCHING
(
    matching_id   varchar(40)                                                        not null
        primary key,
    sender_id     varchar(40)                                                        not null,
    sender_name   varchar(100)                                                       not null,
    receiver_id   varchar(40)                                                        not null,
    receiver_name varchar(100)                                                       not null,
    status        enum ('PENDING', 'ACCEPTED', 'REJECTED') default 'PENDING'         not null,
    created_at    datetime                                 default CURRENT_TIMESTAMP not null,
    updated_at    datetime                                 default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    message       text                                                               null
);

create index idx_created_at
    on MATCHING (created_at);

create index idx_receiver_id
    on MATCHING (receiver_id);

create index idx_sender_id
    on MATCHING (sender_id);

create index idx_status
    on MATCHING (status);

create table CONVERSATION
(
    conversation_id        varchar(40)                        not null
        primary key,
    last_message_timestamp datetime                           null,
    created_at             datetime default CURRENT_TIMESTAMP not null,
    updated_at             datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create table CONVERSATION_PARTICIPANT
(
    conversation_participant_id  bigint auto_increment
        primary key,
    conversation_id varchar(40) not null,
    user_id         varchar(40) not null,
    constraint conversation_user_unique unique (conversation_id, user_id),
    constraint fk_conversation
        foreign key (conversation_id) references CONVERSATION (conversation_id)
            on delete cascade
);


CREATE INDEX idx_conversation_user ON CONVERSATION_PARTICIPANT(conversation_id, user_id);

create table USER
(
    user_id    varchar(40)                        not null
        primary key,
    name       varchar(100)                       not null,
    role       enum ('MODEL', 'PHOTOGRAPHER')     not null,
    location   varchar(255)                       null,
    style      json                               null,
    introduce  text                               null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create index idx_location
    on USER (location);

create index idx_name
    on USER (name);

create index idx_role
    on USER (role);

