Create table if not exists`users` (
`id` int (10) AUTO_INCREMENT PRIMARY KEY,
`name` varchar (50) NOT NULL,
`password` varchar (50) NOT NULL,
`role` varchar (50) NOT NULL
);

Create table if not exists`courses` (
`id` int (10) AUTO_INCREMENT PRIMARY KEY,
`name` varchar (50) NOT NULL,
`description` varchar (50),
`links` varchar (50),
`minimalSubscribers` int (10),
`minimalAttendee` int(10),
`state` varchar (50) NOT NULL,
`idCreater` int (10),
`idCategory` int (10),
`decisionDM` varchar (50),
`reasonDM` varchar (50),
`decisionKM` varchar (50),
`reasonKM` varchar (50)
);

Create table if not exists`categories` (
`id` int (10) AUTO_INCREMENT PRIMARY KEY,
`name` varchar (50) NOT NULL
);

Create table if not exists`courses_subscribes` (
`idUser` int (10),
`idCourse` int (10),
PRIMARY KEY(`idUser`,`idCourse`)
);

Create table if not exists`courses_attenders` (
`idUser` int (10),
`idCourse` int (10),
PRIMARY KEY(`idUser`,`idCourse`)
);

Create table if not exists`courses_evaluate` (
`idUser` int (10),
`idCourse` int (10),
`grade` int (10),
PRIMARY KEY(`idUser`,`idCourse`)
);

INSERT INTO `courses` VALUES ('1','Draft','32','54','1','1','Draft','3','1','None','','None',''); 
INSERT INTO `courses` VALUES ('2','Proposal','12','21','1','1','Proposal','3','2','None','','None',''); 
INSERT INTO `courses` VALUES ('3','Rejected','2','1','1','1','Rejected','3','2','Reject',
'The worst that i seen','Reject','So close'); 
INSERT INTO `courses` VALUES ('4','New','12','21','2','2','New','3','2','Approve','Greate','Approve','Awesome'); 
INSERT INTO `courses` VALUES ('5','Open','12','21','1','1','Open','3','2','Approve','You are genius','Approve','Legendary'); 
INSERT INTO `courses` VALUES ('6','Ready','12','21','1','1','Ready','3','2','Approve','Again wanted been student',
'Approve','It`s Le... wait-wait ...gendary'); 
INSERT INTO `courses` VALUES ('7','InProgress','12','21','1','1','InProgress','3','2','Approve','Not bad','Approve','So-so'); 
INSERT INTO `courses` VALUES ('8','Finished','12','21','1','1','Finished','3','2','Approve','Good','Approve','Cool course'); 
INSERT INTO `users` VALUES ('1','km@tc.edu','202cb962ac59075b964b07152d234b70','KM'); 
INSERT INTO `users` VALUES ('2','dm@tc.edu','202cb962ac59075b964b07152d234b70','DM'); 
INSERT INTO `users` VALUES ('3','lecturer-a@tc.edu','202cb962ac59075b964b07152d234b70','LECTURER'); 
INSERT INTO `users` VALUES ('4','lecturer-b@tc.edu','202cb962ac59075b964b07152d234b70','LECTURER'); 
INSERT INTO `users` VALUES ('5','user-a@tc.edu','202cb962ac59075b964b07152d234b70','STUDENT'); 
INSERT INTO `users` VALUES ('6','user-b@tc.edu','202cb962ac59075b964b07152d234b70','STUDENT'); 
INSERT INTO `users` VALUES ('7','user-c@tc.edu','202cb962ac59075b964b07152d234b70','STUDENT'); 
INSERT INTO `courses_subscribes` VALUES ('5', '4');
INSERT INTO `courses_subscribes` VALUES ('6', '5');
INSERT INTO `courses_attenders` VALUES ('5', '5');
INSERT INTO `courses_attenders` VALUES ('5', '6');
INSERT INTO `courses_attenders` VALUES ('6', '6');
INSERT INTO `courses_attenders` VALUES ('5', '7');
INSERT INTO `courses_attenders` VALUES ('6', '7');
INSERT INTO `courses_attenders` VALUES ('5', '8');
INSERT INTO `courses_attenders` VALUES ('6', '8');
INSERT INTO `courses_evaluate` VALUES ('5', '8', '5');
INSERT INTO `categories` VALUES ('1', 'Development');
INSERT INTO `categories` VALUES ('2', 'Project Management')