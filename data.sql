DROP DATABASE IF EXISTS paymybundy;
CREATE DATABASE payMyBundy CHARACTER SET 'utf8';
use payMyBundy;

    DROP TABLE IF EXISTS users;
    CREATE TABLE users(
		idUser SMALLINT unsigned NOT NULL auto_increment,
        username VARCHAR(50),
        mdp VARCHAR(100) not null ,
        balance FLOAT ,
        step BOOLEAN not null,
        PRIMARY KEY(idUser)
        )
	ENGINE =InnoDB;
    describe users;
    
    
DROP TABLE IF EXISTS bank;
CREATE TABLE  IF NOT EXISTS bank(
	idBank SMALLINT unsigned NOT NULL auto_increment,
    idUser SMALLINT unsigned not null,
    iban VARCHAR(50) NOT NULL,
    swift VARCHAR(50) NOT NULL,
    acount FLOAT,
    PRIMARY KEY(idBank),
    FOREIGN KEY(idUser) REFERENCES users(idUser)
    )
    ENGINE =InnoDB;
    describe bank;

    DROP TABLE IF EXISTS userFriend;
    CREATE TABLE userFriend(
		idRelation SMALLINT unsigned not null auto_increment,
        idUser SMALLINT unsigned not null,
        idFriend SMALLINT unsigned not null,
        PRIMARY KEY(idRelation),
        FOREIGN KEY(idUser) REFERENCES users(idUser),
        FOREIGN KEY (idFriend) REFERENCES users(idUser)
        )
	ENGINE =InnoDB;
    describe userFriend;
    
    DROP TABLE IF EXISTS motif;
    CREATE TABLE motif(
	idMotif SMALLINT unsigned not null  auto_increment,
    descriptionMotif TEXT ,
    PRIMARY KEY (idMotif)
    );
    
    DROP TABLE IF EXISTS transactions;
    CREATE TABLE transactions(
    idTransaction SMALLINT unsigned not null auto_increment ,
    idUserReceiver SMALLINT unsigned not null,
    idUserSender SMALLINT unsigned not null,
    idMotif smallint unsigned not null,
    fees float not null ,
    amount float not null ,
    transactionType VARCHAR (50),
    PRIMARY KEY (idTransaction),
    FOREIGN KEY(idUserReceiver) REFERENCES users(idUser),
    FOREIGN KEY (idUserSender) REFERENCES users(idUser),
    FOREIGN KEY (idMotif) REFERENCES motif(idMotif)
    )
    ENGINE =InnoDB;
    
    
    INSERT INTO users(mail,mdp,balance,step) VALUES('hayeyqueen@gmail.com','hayley123',800,true);
    INSERT INTO users(mail,mdp,balance,step) VALUES('claramoney@yahoo.fr','claramoneyAzerty',750,true);
    INSERT INTO users(mail,mdp,step) VALUES('taylorSmith@gmail.com','cobra123',false);
    
    INSERT INTO bank(iban,swift,acount,idUser)VALUES('FR00 1234 5132 4152 3456 7891 A12','BNPA FRPP 658',1500,1);
    INSERT INTO bank(iban,swift,acount,idUser)VALUES('FR00 3648 0547 4152 8765 6354 B54','SOCG FRPP 354',1000,2);
    INSERT INTO bank(iban,swift,acount,idUser)VALUES('FR00 8547 0314 6845 3201 8450 C25','BOUS FRPP 214',2500,3);
    
    INSERT INTO userFriend(idUser,idFriend) VALUES(1,2);
    INSERT INTO userFriend(idUser,idFriend) VALUES(1,3);
    
    INSERT INTO motif(descriptionMotif) VALUES('Pret entre amis ');
    INSERT INTO motif(descriptionMotif) VALUES('Remboursement');
	INSERT INTO motif(descriptionMotif) VALUES('Loyer');
	
    INSERT INTO transactions(idUserReceiver,idUserSender,idMotif,fees,amount,transactionType) VALUES(1,2,1,2.5,-500,'VIREMENT');
	INSERT INTO transactions(idUserReceiver,idUserSender,idMotif,fees,amount,transactionType) VALUES(2,2,1,2.5,-500,'RETRAIT');