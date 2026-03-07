

-- INSERT INTO departments (code, name, contactEmail) VALUES ('MIE', 'Mechanical and Industrial Engineering', 'reception@mie.utoronto.ca');
-- INSERT INTO departments (code, name, contactEmail) VALUES ('ECE', 'Electrical and Computer Engineering', 'eceinquiry@utoronto.ca');
-- INSERT INTO departments (code, name, contactEmail) VALUES ('MSE', 'Materials Science and Engineering', 'materials.engineering@utoronto.ca');

-- INSERT INTO students (id, firstName, lastName, email, initials) VALUES (1111, 'Tyrion', 'Lannister', 'tyrion.lannister@mail.univ.ca', 'T. L.');
-- INSERT INTO students (id, firstName, lastName, email, initials) VALUES (2222, 'Cersei', 'Lannister', 'cersei.lannister@mail.univ.ca', 'C. L.');
-- INSERT INTO students (id, firstName, lastName, email, initials) VALUES (3333, 'Jaime', 'Lannister', 'jaime.lannister@mail.univ.ca', 'D. T.');
-- INSERT INTO students (id, firstName, lastName, email, initials) VALUES (4444, 'Daenerys', 'Targaryen', 'jaime.targaryen@mail.univ.ca', NULL);
-- INSERT INTO students (id, firstName, lastName, email, initials) VALUES (5555, 'Jon', 'Snow', 'jon.snow@mail.univ.ca', NULL);

-- INSERT INTO professors (id, firstName, lastName, email, office, salary) VALUES (1122, 'Sansa', 'Stark', 'sansa.stark@univ.ca', 'BA1234', 50000);
-- INSERT INTO professors (id, firstName, lastName, email, office, salary) VALUES (3344, 'Arya', 'Stark', 'arya.stark@univ.ca', 'MC1234', 70000);
-- INSERT INTO professors (id, firstName, lastName, email, office, salary) VALUES (5566, 'Jorah', 'Mormont', 'jorah.mormont@univ.ca', 'MY1234', 60000);

-- INSERT INTO courses (code, name, professorId) VALUES ('GOT123', 'A Game of Thrones', 1122);
-- INSERT INTO courses (code, name, professorId) VALUES ('GOT456', 'A Clash of Kings', 3344);
-- INSERT INTO courses (code, name, professorId) VALUES ('GOT789', 'A Storm of Swords', 5566);

-- INSERT INTO marks(studentId, courseCode, mark) VALUES(1111, 'GOT123', 80);
-- INSERT INTO marks(studentId, courseCode, mark) VALUES(2222, 'GOT123', 85);
-- INSERT INTO marks(studentId, courseCode, mark) VALUES(3333, 'GOT456', 90);
-- INSERT INTO marks(studentId, courseCode, mark) VALUES(4444, 'GOT456', 95);
-- INSERT INTO marks(studentId, courseCode, mark) VALUES(5555, 'GOT789', 100);
-- INSERT INTO marks(studentId, courseCode, mark) VALUES(5555, 'GOT123', 80);
-- INSERT INTO marks(studentId, courseCode, mark) VALUES(5555, 'GOT456', 90);

-- INSERT INTO classrooms(code, capacity) VALUES('MB123', 30);
-- INSERT INTO classrooms(code, capacity) VALUES('BA1130', 60);

INSERT INTO issues(id, name, deck) VALUES(1, 'Blah blah blah', 'nfiohnoj]ld');
INSERT INTO issues(id, name, deck) VALUES(2, 'Blah blah blah', 'nfiohnoj]ld');

INSERT INTO volumes(id, name, numIssues, numLikes, deck, startYear, image) VALUES(1, 'Batman #1', 2, 4, 'Blah cheese blah', 2000, 'batman.png');
INSERT INTO volumes(id, name, numIssues, numLikes, deck, startYear, image) VALUES(2, 'Spiderman #1', 2, 2, 'Blah blah blah', 2000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRby1SM-CGg_hYiSMAtS2NvMm2nV8xrKBjm_w&s');
INSERT INTO volumes(id, name, numIssues, numLikes, deck, startYear, image) VALUES(3, 'Spiderman #2', 2, 3, 'Blah blah blah', 2000, 'batman.png');
INSERT INTO volumes(id, name, numIssues, numLikes, deck, startYear, image) VALUES(4, 'Spiderman #3', 2, 6, 'Blah blah blah', 2000, 'batman.png');
INSERT INTO volumes(id, name, numIssues, numLikes, deck, startYear, image) VALUES(5, 'Spiderman #4', 2, 1, 'Blah blah blah', 2000, 'batman.png');


INSERT INTO characters(id, name, deck, gender, origin) VALUES(1, 'Ethan', 'Fine Shit', 'Female', 'Ohio');

INSERT INTO publishers(id, name, deck, description, image) VALUES(1, 'a', 'b', 'c', 'd');

INSERT INTO teams(id, name, deck, publisherId) VALUES(1, 'a', 'b', 1);

INSERT INTO powers(id, name, deck) VALUES(1, 'Invisibility', ' ');
INSERT INTO powers(id, name, deck) VALUES(2, 'Time Freeze', ' ');
INSERT INTO powers(id, name, deck) VALUES(3, 'Untraceable DNA', ' ');

INSERT INTO genres(id, name) VALUES(0, 'Women in Refridgerators');

INSERT INTO genres(id, name) VALUES(1, 'Action');
INSERT INTO genres(id, name) VALUES(2, 'Fantasy');
INSERT INTO genres(id, name) VALUES(3, 'Space');
INSERT INTO genres(id, name) VALUES(4, 'Sci-Fi');
INSERT INTO genres(id, name) VALUES(5, 'Romance');

INSERT INTO characters(id, name, deck, gender, origin) VALUES(1, 'Archie', ' ', 'Male', 'Ohio');
INSERT INTO characters(id, name, deck, gender, origin) VALUES(2, 'Batman', ' ', 'Male', 'Ohio');
INSERT INTO characters(id, name, deck, gender, origin) VALUES(3, 'Spiderman', ' ', 'Male', 'Ohio');
INSERT INTO characters(id, name, deck, gender, origin) VALUES(4, 'Superman', ' ', 'Male', 'Ohio');
INSERT INTO characters(id, name, deck, gender, origin) VALUES(5, 'Flash', ' ', 'Male', 'Ohio');

INSERT INTO publishers(id, name, deck, description, image) VALUES(1, 'Marvel', 'b', 'c', 'd');
INSERT INTO publishers(id, name, deck, description, image) VALUES(2, 'DC', 'b', 'c', 'd');
INSERT INTO publishers(id, name, deck, description, image) VALUES(3, 'Archie', 'b', 'c', 'd');

INSERT INTO powers(id, name, deck) VALUES(1, 'Speed', ' ');
INSERT INTO powers(id, name, deck) VALUES(2, 'Flight', ' ');
INSERT INTO powers(id, name, deck) VALUES(3, 'Web', ' ');

INSERT INTO teams(id, name, deck, publisherId) VALUES(1, 'Avengers', 'b', 1);
INSERT INTO teams(id, name, deck, publisherId) VALUES(2, 'Justice League', 'b', 2);
INSERT INTO teams(id, name, deck, publisherId) VALUES(3, 'Fantastic Four', 'b', 1);
