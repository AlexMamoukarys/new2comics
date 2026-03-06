

INSERT INTO departments (code, name, contactEmail) VALUES ('MIE', 'Mechanical and Industrial Engineering', 'reception@mie.utoronto.ca');
INSERT INTO departments (code, name, contactEmail) VALUES ('ECE', 'Electrical and Computer Engineering', 'eceinquiry@utoronto.ca');
INSERT INTO departments (code, name, contactEmail) VALUES ('MSE', 'Materials Science and Engineering', 'materials.engineering@utoronto.ca');

INSERT INTO students (id, firstName, lastName, email, initials) VALUES (1111, 'Tyrion', 'Lannister', 'tyrion.lannister@mail.univ.ca', 'T. L.');
INSERT INTO students (id, firstName, lastName, email, initials) VALUES (2222, 'Cersei', 'Lannister', 'cersei.lannister@mail.univ.ca', 'C. L.');
INSERT INTO students (id, firstName, lastName, email, initials) VALUES (3333, 'Jaime', 'Lannister', 'jaime.lannister@mail.univ.ca', 'D. T.');
INSERT INTO students (id, firstName, lastName, email, initials) VALUES (4444, 'Daenerys', 'Targaryen', 'jaime.targaryen@mail.univ.ca', NULL);
INSERT INTO students (id, firstName, lastName, email, initials) VALUES (5555, 'Jon', 'Snow', 'jon.snow@mail.univ.ca', NULL);

INSERT INTO professors (id, firstName, lastName, email, office, salary) VALUES (1122, 'Sansa', 'Stark', 'sansa.stark@univ.ca', 'BA1234', 50000);
INSERT INTO professors (id, firstName, lastName, email, office, salary) VALUES (3344, 'Arya', 'Stark', 'arya.stark@univ.ca', 'MC1234', 70000);
INSERT INTO professors (id, firstName, lastName, email, office, salary) VALUES (5566, 'Jorah', 'Mormont', 'jorah.mormont@univ.ca', 'MY1234', 60000);

INSERT INTO courses (code, name, professorId) VALUES ('GOT123', 'A Game of Thrones', 1122);
INSERT INTO courses (code, name, professorId) VALUES ('GOT456', 'A Clash of Kings', 3344);
INSERT INTO courses (code, name, professorId) VALUES ('GOT789', 'A Storm of Swords', 5566);

INSERT INTO marks(studentId, courseCode, mark) VALUES(1111, 'GOT123', 80);
INSERT INTO marks(studentId, courseCode, mark) VALUES(2222, 'GOT123', 85);
INSERT INTO marks(studentId, courseCode, mark) VALUES(3333, 'GOT456', 90);
INSERT INTO marks(studentId, courseCode, mark) VALUES(4444, 'GOT456', 95);
INSERT INTO marks(studentId, courseCode, mark) VALUES(5555, 'GOT789', 100);
INSERT INTO marks(studentId, courseCode, mark) VALUES(5555, 'GOT123', 80);
INSERT INTO marks(studentId, courseCode, mark) VALUES(5555, 'GOT456', 90);

INSERT INTO classrooms(code, capacity) VALUES('MB123', 30);
INSERT INTO classrooms(code, capacity) VALUES('BA1130', 60);

INSERT INTO volumes(id, name, numIssues, numLikes, summary, startYear, image) VALUES(1, 'Batman #1', 2, 3, 'Blah blah blah', 2000, 'batman.png');

INSERT INTO characters(id, name, deck, gender, origin) VALUES(1, 'Ethan', 'Fine Shit', 'Female', 'Ohio');

INSERT INTO publishers(id, name, deck, description, image) VALUES(1, 'a', 'b', 'c', 'd');

INSERT INTO teams(id, name, deck, publisherId) VALUES(1, 'a', 'b', 1);

INSERT INTO powers(id, name, deck) VALUES(1, 'Invisibility', ' ');
INSERT INTO powers(id, name, deck) VALUES(2, 'Time Freeze', ' ');
INSERT INTO powers(id, name, deck) VALUES(3, 'Untraceable DNA', ' ');

INSERT INTO genres(id, name, deck) VALUES(1, 'Women in Refridgerators', ' ');