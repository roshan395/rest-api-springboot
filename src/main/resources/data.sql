insert into user_details(id, birth_date, name)
values (1001, current_date(), 'ronak');

insert into user_details(id, birth_date, name)
values (1002, current_date(), 'rohan');

insert into user_details(id, birth_date, name)
values (1003, current_date(), 'roshan');

insert into post(id, description, user_id)
values (20001, 'I Want to learn AWS', 1001);

insert into post(id, description, user_id)
values (20002, 'I Want to learn devops', 1001);

insert into post(id, description, user_id)
values (20003, 'I Want to learn react', 1002);

insert into post(id, description, user_id)
values (20004, 'I Want to learn angular', 1002);