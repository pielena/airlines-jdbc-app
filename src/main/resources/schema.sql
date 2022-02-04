CREATE TABLE IF NOT EXISTS crew (
id BIGINT AUTO_INCREMENT ,
name VARCHAR(50) NOT NULL ,

CONSTRAINT crew_PK PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS airplane (
id            BIGINT AUTO_INCREMENT ,
code_name     VARCHAR(50) NOT NULL ,
model         VARCHAR(50) NOT NULL ,
manufacture_date      DATE NOT NULL ,
capacity    INT NOT NULL ,
flight_range DECIMAL NOT NULL ,
crew_id BIGINT NOT NULL ,

CONSTRAINT airplane_PK PRIMARY KEY (id),
CONSTRAINT airplane_crew_FK FOREIGN KEY (crew_id) REFERENCES crew(id)
);

CREATE TABLE IF NOT EXISTS crew_member (
id BIGINT AUTO_INCREMENT ,
crew_id BIGINT NOT NULL ,
first_name VARCHAR(50) NOT NULL ,
last_name VARCHAR(50) NOT NULL ,
position VARCHAR(50) NOT NULL ,
birthday DATE NOT NULL ,
citizenship VARCHAR(25) ,

CONSTRAINT crew_member_PK PRIMARY KEY (id) ,
CONSTRAINT crew_member_crew_FK FOREIGN KEY (crew_id) REFERENCES crew(id)
);