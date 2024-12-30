INSERT INTO MESSAGE (message_id,conversation_id,sender_id,receiver_id,file_id,message_content,timestamp,message_status )
values ('MSG_b6780129-4d25-4899-a3bb-a02875b97159','CON_cfae4bc7-c56c-4098-8f53-a9b63fd3c544', 'MDL_ff65b00e-5f6d-4e40-9598-68edb0fe1f89', 'PHO_88fea9ea-982f-4288-a1c5-2649ba30566f', '', '안녕하세요. 잘부탁드립니다.', now(), 'SENT');

INSERT INTO MESSAGE (message_id,conversation_id,sender_id,receiver_id,file_id,message_content,timestamp,message_status )
values ('MSG_b6780129-4d25-4899-a3bb-a02875b97160','CON_cfae4bc7-c56c-4098-8f53-a9b63fd3c544', 'PHO_88fea9ea-982f-4288-a1c5-2649ba30566f', 'MDL_ff65b00e-5f6d-4e40-9598-68edb0fe1f89', '', '네. 안녕하세요 !', now(), 'SENT');

INSERT INTO CONVERSATION (conversation_id,last_message_timestamp,created_at,updated_at) VALUES ('CON_cfae4bc7-c56c-4098-8f53-a9b63fd3c544','2024-12-15 14:30:00','2024-12-15 14:30:00','2024-12-15 14:30:00');

insert into CONVERSATION_PARTICIPANT (participant_id, conversation_id, user_id) values (1, 'CON_cfae4bc7-c56c-4098-8f53-a9b63fd3c544', 'MDL_ff65b00e-5f6d-4e40-9598-68edb0fe1f89');
insert into CONVERSATION_PARTICIPANT (participant_id, conversation_id, user_id) values (2, 'CON_cfae4bc7-c56c-4098-8f53-a9b63fd3c544', 'PHO_88fea9ea-982f-4288-a1c5-2649ba30566f');

INSERT INTO MATCHING (matching_id,sender_id,sender_name,receiver_id,receiver_name,status,message) VALUES ('MAT_cfae4bc7-c56c-4098-8f53-a9b63fd3c544','MDL_ff65b00e-5f6d-4e40-9598-68edb0fe1f89','김모델','PHO_88fea9ea-982f-4288-a1c5-2649ba30566f','박사진','PENDING','촬영 요청드립니다.');

INSERT INTO USER (user_id,name,role,location,style,introduce)
VALUES('PHO_88fea9ea-982f-4288-a1c5-2649ba30566f','박사진','PHOTOGRAPHER','서울특별시 마포구',JSON_ARRAY('인물', '패션', '스튜디오'),'10년차 프로페셔널 포토그래퍼입니다.'),
      ('MDL_ff65b00e-5f6d-4e40-9598-68edb0fe1f89','이모델','MODEL','부산광역시 해운대구',JSON_ARRAY('패션', '수영복', '피트니스'),'피트니스 모델 전문입니다.');

