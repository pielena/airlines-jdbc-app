CREATE TABLE IF NOT EXISTS crew (
id BIGINT NOT NULL AUTO_INCREMENT ,
name VARCHAR(255) NOT NULL ,

CONSTRAINT crew_PK PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS airplane (
id            BIGINT NOT NULL AUTO_INCREMENT ,
code_name     VARCHAR(255) NOT NULL ,
model         VARCHAR(255) NOT NULL ,
manufacture_date      DATE NOT NULL ,
capacity    BIGINT NOT NULL ,
flight_range DECIMAL NOT NULL ,
crew BIGINT NOT NULL ,

CONSTRAINT airplane_PK PRIMARY KEY (id),
CONSTRAINT airplane_crew_FK FOREIGN KEY (crew) REFERENCES crew(id)
);

CREATE TABLE IF NOT EXISTS crew_member (
id BIGINT NOT NULL ,
crew BIGINT NOT NULL ,
first_name VARCHAR(255) NOT NULL ,
last_name VARCHAR(255) NOT NULL ,
position VARCHAR(255) NOT NULL ,
birthday DATE NOT NULL ,
citizenship VARCHAR(255) ,

CONSTRAINT crew_member_PK PRIMARY KEY (id) ,
CONSTRAINT crew_member_crew_FK FOREIGN KEY (crew) REFERENCES crew(id)
);

