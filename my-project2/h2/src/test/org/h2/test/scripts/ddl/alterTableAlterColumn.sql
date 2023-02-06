-- Copyright 2004-2022 H2 Group. Multiple-Licensed under the MPL 2.0,
-- and the EPL 1.0 (https://h2database.com/html/license.html).
-- Initial Developer: H2 Group
--

CREATE TABLE TEST(T INT);
> ok

SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
>> INTEGER

-- SET DEFAULT
ALTER TABLE TEST ALTER COLUMN T SET DEFAULT 1;
> ok

SELECT COLUMN_DEFAULT FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
>> 1

-- DROP DEFAULT
ALTER TABLE TEST ALTER COLUMN T DROP DEFAULT;
> ok

SELECT COLUMN_DEFAULT FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
>> null

-- SET NOT NULL
ALTER TABLE TEST ALTER COLUMN T SET NOT NULL;
> ok

SELECT IS_NULLABLE FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
>> NO

-- DROP NOT NULL
ALTER TABLE TEST ALTER COLUMN T DROP NOT NULL;
> ok

SELECT IS_NULLABLE FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
>> YES

ALTER TABLE TEST ALTER COLUMN T SET NOT NULL;
> ok

-- SET NULL
ALTER TABLE TEST ALTER COLUMN T SET NULL;
> ok

SELECT IS_NULLABLE FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
>> YES

-- SET DATA TYPE
ALTER TABLE TEST ALTER COLUMN T SET DATA TYPE BIGINT;
> ok

SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
>> BIGINT

ALTER TABLE TEST ALTER COLUMN T INT INVISIBLE DEFAULT 1 ON UPDATE 2 NOT NULL COMMENT 'C';
> ok

SELECT DATA_TYPE, IS_VISIBLE, COLUMN_DEFAULT, COLUMN_ON_UPDATE, REMARKS, IS_NULLABLE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
> DATA_TYPE IS_VISIBLE COLUMN_DEFAULT COLUMN_ON_UPDATE REMARKS IS_NULLABLE
> --------- ---------- -------------- ---------------- ------- -----------
> INTEGER   FALSE      1              2                C       NO
> rows: 1

ALTER TABLE TEST ALTER COLUMN T SET DATA TYPE BIGINT;
> ok

SELECT DATA_TYPE, IS_VISIBLE, COLUMN_DEFAULT, COLUMN_ON_UPDATE, REMARKS, IS_NULLABLE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'T';
> DATA_TYPE IS_VISIBLE COLUMN_DEFAULT COLUMN_ON_UPDATE REMARKS IS_NULLABLE
> --------- ---------- -------------- ---------------- ------- -----------
> BIGINT    FALSE      1              2                C       NO
> rows: 1

DROP TABLE TEST;
> ok

CREATE TABLE TEST(ID INT AUTO_INCREMENT PRIMARY KEY, V INT NOT NULL);
> ok

ALTER TABLE TEST ALTER COLUMN ID RESTART WITH 100;
> ok

INSERT INTO TEST(V) VALUES (1);
> update count: 1

ALTER TABLE TEST AUTO_INCREMENT = 200;
> exception SYNTAX_ERROR_2

SET MODE MySQL;
> ok

ALTER TABLE TEST AUTO_INCREMENT = 200;
> ok

INSERT INTO TEST(V) VALUES (2);
> update count: 1

ALTER TABLE TEST AUTO_INCREMENT 300;
> ok

INSERT INTO TEST(V) VALUES (3);
> update count: 1

SELECT * FROM TEST ORDER BY ID;
> ID  V
> --- -
> 100 1
> 200 2
> 300 3
> rows (ordered): 3

ALTER TABLE TEST DROP PRIMARY KEY;
> ok

ALTER TABLE TEST AUTO_INCREMENT = 400;
> exception COLUMN_NOT_FOUND_1

ALTER TABLE TEST ADD PRIMARY KEY(V);
> ok

ALTER TABLE TEST AUTO_INCREMENT = 400;
> exception COLUMN_NOT_FOUND_1

SET MODE Regular;
> ok

DROP TABLE TEST;
> ok

-- Compatibility syntax

SET MODE MySQL;
> ok

create table test(id int primary key, name varchar);
> ok

insert into test(id) values(1);
> update count: 1

alter table test change column id id2 int;
> ok

select id2 from test;
> ID2
> ---
> 1
> rows: 1

drop table test;
> ok

SET MODE Oracle;
> ok

CREATE MEMORY TABLE TEST(V INT NOT NULL);
> ok

ALTER TABLE TEST MODIFY COLUMN V BIGINT;
> ok

SCRIPT NODATA NOPASSWORDS NOSETTINGS NOVERSION TABLE TEST;
> SCRIPT
> -----------------------------------------------------------
> CREATE USER IF NOT EXISTS "SA" PASSWORD '' ADMIN;
> CREATE MEMORY TABLE "PUBLIC"."TEST"( "V" BIGINT NOT NULL );
> -- 0 +/- SELECT COUNT(*) FROM PUBLIC.TEST;
> rows (ordered): 3

SET MODE MySQL;
> ok

ALTER TABLE TEST MODIFY COLUMN V INT;
> ok

SCRIPT NODATA NOPASSWORDS NOSETTINGS NOVERSION TABLE TEST;
> SCRIPT
> ---------------------------------------------------
> CREATE USER IF NOT EXISTS "SA" PASSWORD '' ADMIN;
> CREATE MEMORY TABLE "PUBLIC"."TEST"( "V" INTEGER );
> -- 0 +/- SELECT COUNT(*) FROM PUBLIC.TEST;
> rows (ordered): 3

ALTER TABLE TEST MODIFY COLUMN V BIGINT NOT NULL;
> ok

SCRIPT NODATA NOPASSWORDS NOSETTINGS NOVERSION TABLE TEST;
> SCRIPT
> -----------------------------------------------------------
> CREATE USER IF NOT EXISTS "SA" PASSWORD '' ADMIN;
> CREATE MEMORY TABLE "PUBLIC"."TEST"( "V" BIGINT NOT NULL );
> -- 0 +/- SELECT COUNT(*) FROM PUBLIC.TEST;
> rows (ordered): 3

SET MODE Regular;
> ok

DROP TABLE TEST;
> ok

create table test(id int, name varchar);
> ok

alter table test alter column id int as id+1;
> exception COLUMN_NOT_FOUND_1

drop table test;
> ok

create table t(x varchar) as select 'x';
> ok

alter table t alter column x int;
> exception DATA_CONVERSION_ERROR_1

drop table t;
> ok

create table t(id identity default on null, x varchar) as select null, 'x';
> ok

alter table t alter column x int;
> exception DATA_CONVERSION_ERROR_1

drop table t;
> ok

-- ensure that increasing a VARCHAR columns length takes effect because we optimize this case
create table t(x varchar(2)) as select 'x';
> ok

alter table t alter column x varchar(20);
> ok

insert into t values 'Hello';
> update count: 1

drop table t;
> ok

SET MODE MySQL;
> ok

create table t(x int);
> ok

alter table t modify column x varchar(20);
> ok

insert into t values('Hello');
> update count: 1

drop table t;
> ok

-- This worked in v1.4.196
create table T (C varchar not null);
> ok

alter table T modify C int null;
> ok

insert into T values(null);
> update count: 1

drop table T;
> ok

-- This failed in v1.4.196
create table T (C int not null);
> ok

-- Silently corrupted column C
alter table T modify C null;
> ok

insert into T values(null);
> update count: 1

drop table T;
> ok

SET MODE Oracle;
> ok

create table foo (bar varchar(255));
> ok

alter table foo modify (bar varchar(255) not null);
> ok

insert into foo values(null);
> exception NULL_NOT_ALLOWED

DROP TABLE FOO;
> ok

SET MODE Regular;
> ok

-- Tests a bug we used to have where altering the name of a column that had
-- a check constraint that referenced itself would result in not being able
-- to re-open the DB.
create table test(id int check(id in (1,2)) );
> ok

alter table test alter id rename to id2;
> ok

@reconnect

insert into test values 1;
> update count: 1

insert into test values 3;
> exception CHECK_CONSTRAINT_VIOLATED_1

drop table test;
> ok

CREATE MEMORY TABLE TEST(C INT);
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D RENAME TO E;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS C RENAME TO D;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E SET NOT NULL;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D SET NOT NULL;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E SET DEFAULT 1;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D SET DEFAULT 1;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E SET ON UPDATE 2;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D SET ON UPDATE 2;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E SET DATA TYPE BIGINT;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D SET DATA TYPE BIGINT;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E SET INVISIBLE;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D SET INVISIBLE;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E SELECTIVITY 3;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D SELECTIVITY 3;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E RESTART WITH 4;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D RESTART WITH 4 SET MAXVALUE 1000;
> ok

SELECT COLUMN_NAME, IS_IDENTITY, IDENTITY_GENERATION, IDENTITY_START, IDENTITY_INCREMENT, IDENTITY_MAXIMUM,
    IDENTITY_MINIMUM, IDENTITY_CYCLE, IDENTITY_BASE, IDENTITY_CACHE FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME IS_IDENTITY IDENTITY_GENERATION IDENTITY_START IDENTITY_INCREMENT IDENTITY_MAXIMUM IDENTITY_MINIMUM IDENTITY_CYCLE IDENTITY_BASE IDENTITY_CACHE
> ----------- ----------- ------------------- -------------- ------------------ ---------------- ---------------- -------------- ------------- --------------
> D           YES         BY DEFAULT          1              1                  1000             1                NO             4             32
> rows: 1

ALTER TABLE TEST ALTER COLUMN D SET CYCLE;
> ok

SELECT IDENTITY_CYCLE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
>> YES

ALTER TABLE TEST ALTER COLUMN D DROP IDENTITY;
> ok

SELECT IS_IDENTITY FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
>> NO

ALTER TABLE TEST ALTER COLUMN D DROP IDENTITY;
> ok

ALTER TABLE TEST ALTER COLUMN E DROP IDENTITY;
> exception COLUMN_NOT_FOUND_1

ALTER TABLE TEST ALTER COLUMN D SET GENERATED BY DEFAULT;
> ok

ALTER TABLE TEST ALTER COLUMN D SET DEFAULT (1);
> ok

SELECT COLUMN_DEFAULT, IS_IDENTITY FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_DEFAULT IS_IDENTITY
> -------------- -----------
> null           YES
> rows: 1

ALTER TABLE TEST ALTER COLUMN D DROP IDENTITY;
> ok

ALTER TABLE TEST ALTER COLUMN D SET GENERATED ALWAYS;
> ok

SELECT IS_IDENTITY FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
>> YES

ALTER TABLE TEST ALTER COLUMN IF EXISTS E DROP IDENTITY;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E DROP NOT NULL;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D DROP NOT NULL;
> exception COLUMN_MUST_NOT_BE_NULLABLE_1

ALTER TABLE TEST ALTER COLUMN IF EXISTS E DROP DEFAULT;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D DROP DEFAULT;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E DROP ON UPDATE;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D DROP ON UPDATE;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E INT;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D INT;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS E SET VISIBLE;
> ok

ALTER TABLE TEST ALTER COLUMN IF EXISTS D SET VISIBLE;
> ok

SCRIPT NODATA NOPASSWORDS NOSETTINGS NOVERSION TABLE TEST;
> SCRIPT
> ------------------------------------------------------------
> CREATE USER IF NOT EXISTS "SA" PASSWORD '' ADMIN;
> CREATE MEMORY TABLE "PUBLIC"."TEST"( "D" INTEGER NOT NULL );
> -- 0 +/- SELECT COUNT(*) FROM PUBLIC.TEST;
> rows (ordered): 3

DROP TABLE TEST;
> ok

CREATE TABLE TEST(ID INT GENERATED ALWAYS AS IDENTITY (MINVALUE 1 MAXVALUE 10 INCREMENT BY -1), V INT);
> ok

INSERT INTO TEST(V) VALUES 1;
> update count: 1

TABLE TEST;
> ID V
> -- -
> 10 1
> rows: 1

DELETE FROM TEST;
> update count: 1

ALTER TABLE TEST ALTER COLUMN ID RESTART;
> ok

INSERT INTO TEST(V) VALUES 1;
> update count: 1

TABLE TEST;
> ID V
> -- -
> 10 1
> rows: 1

ALTER TABLE TEST ALTER COLUMN ID RESTART WITH 5;
> ok

INSERT INTO TEST(V) VALUES 2;
> update count: 1

TABLE TEST;
> ID V
> -- -
> 10 1
> 5  2
> rows: 2

DROP TABLE TEST;
> ok

CREATE TABLE TEST(A INT) AS VALUES 1, 2, 3;
> ok

ALTER TABLE TEST ALTER COLUMN A SET DATA TYPE BIGINT USING A * 10;
> ok

TABLE TEST;
> A
> --
> 10
> 20
> 30
> rows: 3

ALTER TABLE TEST ADD COLUMN B INT NOT NULL USING A + 1;
> ok

TABLE TEST;
> A  B
> -- --
> 10 11
> 20 21
> 30 31
> rows: 3

ALTER TABLE TEST ADD COLUMN C VARCHAR(2) USING A;
> ok

TABLE TEST;
> A  B  C
> -- -- --
> 10 11 10
> 20 21 20
> 30 31 30
> rows: 3

ALTER TABLE TEST ALTER COLUMN C SET DATA TYPE VARCHAR(3) USING C || '*';
> ok

TABLE TEST;
> A  B  C
> -- -- ---
> 10 11 10*
> 20 21 20*
> 30 31 30*
> rows: 3

DROP TABLE TEST;
> ok

CREATE TABLE TEST(B BINARY) AS VALUES X'00';
> ok

ALTER TABLE TEST ALTER COLUMN B SET DATA TYPE BINARY(2);
> ok

TABLE TEST;
>> X'0000'

DROP TABLE TEST;
> ok

CREATE TABLE TEST(D INT DEFAULT 8, G INT GENERATED ALWAYS AS (D + 1), S INT GENERATED ALWAYS AS IDENTITY);
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, IS_IDENTITY, IS_GENERATED, GENERATION_EXPRESSION
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT IS_IDENTITY IS_GENERATED GENERATION_EXPRESSION
> ----------- -------------- ----------- ------------ ---------------------
> D           8              NO          NEVER        null
> G           null           NO          ALWAYS       "D" + 1
> S           null           YES         NEVER        null
> rows: 3

ALTER TABLE TEST ALTER COLUMN D SET ON UPDATE 1;
> ok

ALTER TABLE TEST ALTER COLUMN G SET ON UPDATE 1;
> ok

ALTER TABLE TEST ALTER COLUMN S SET ON UPDATE 1;
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, IS_IDENTITY, IS_GENERATED, GENERATION_EXPRESSION, COLUMN_ON_UPDATE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT IS_IDENTITY IS_GENERATED GENERATION_EXPRESSION COLUMN_ON_UPDATE
> ----------- -------------- ----------- ------------ --------------------- ----------------
> D           8              NO          NEVER        null                  1
> G           null           NO          ALWAYS       "D" + 1               null
> S           null           YES         NEVER        null                  null
> rows: 3

ALTER TABLE TEST ALTER COLUMN D DROP ON UPDATE;
> ok

ALTER TABLE TEST ALTER COLUMN G DROP ON UPDATE;
> ok

ALTER TABLE TEST ALTER COLUMN S DROP ON UPDATE;
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, IS_IDENTITY, IS_GENERATED, GENERATION_EXPRESSION, COLUMN_ON_UPDATE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT IS_IDENTITY IS_GENERATED GENERATION_EXPRESSION COLUMN_ON_UPDATE
> ----------- -------------- ----------- ------------ --------------------- ----------------
> D           8              NO          NEVER        null                  null
> G           null           NO          ALWAYS       "D" + 1               null
> S           null           YES         NEVER        null                  null
> rows: 3

ALTER TABLE TEST ALTER COLUMN G DROP DEFAULT;
> ok

ALTER TABLE TEST ALTER COLUMN S DROP DEFAULT;
> ok

ALTER TABLE TEST ALTER COLUMN D DROP EXPRESSION;
> ok

ALTER TABLE TEST ALTER COLUMN S DROP EXPRESSION;
> ok

ALTER TABLE TEST ALTER COLUMN D DROP IDENTITY;
> ok

ALTER TABLE TEST ALTER COLUMN G DROP IDENTITY;
> ok

ALTER TABLE TEST ALTER COLUMN G SET DEFAULT ("D" + 2);
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, IS_IDENTITY, IS_GENERATED, GENERATION_EXPRESSION
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT IS_IDENTITY IS_GENERATED GENERATION_EXPRESSION
> ----------- -------------- ----------- ------------ ---------------------
> D           8              NO          NEVER        null
> G           null           NO          ALWAYS       "D" + 1
> S           null           YES         NEVER        null
> rows: 3

ALTER TABLE TEST ALTER COLUMN D DROP DEFAULT;
> ok

ALTER TABLE TEST ALTER COLUMN G DROP EXPRESSION;
> ok

ALTER TABLE TEST ALTER COLUMN S DROP IDENTITY;
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, IS_IDENTITY, IS_GENERATED, GENERATION_EXPRESSION
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT IS_IDENTITY IS_GENERATED GENERATION_EXPRESSION
> ----------- -------------- ----------- ------------ ---------------------
> D           null           NO          NEVER        null
> G           null           NO          NEVER        null
> S           null           NO          NEVER        null
> rows: 3

DROP TABLE TEST;
> ok

CREATE TABLE TEST(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 10 MINVALUE 3 INCREMENT BY 2 CYCLE CACHE 16), V INT);
> ok

INSERT INTO TEST(V) VALUES 1, 2;
> update count: 2

DELETE FROM TEST WHERE V = 2;
> update count: 1

SELECT COLUMN_NAME, DATA_TYPE, IS_IDENTITY, IDENTITY_START, IDENTITY_INCREMENT, IDENTITY_MAXIMUM, IDENTITY_MINIMUM,
    IDENTITY_CYCLE, IDENTITY_BASE, IDENTITY_CACHE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST' AND COLUMN_NAME = 'ID';
> COLUMN_NAME DATA_TYPE IS_IDENTITY IDENTITY_START IDENTITY_INCREMENT IDENTITY_MAXIMUM    IDENTITY_MINIMUM IDENTITY_CYCLE IDENTITY_BASE IDENTITY_CACHE
> ----------- --------- ----------- -------------- ------------------ ------------------- ---------------- -------------- ------------- --------------
> ID          BIGINT    YES         10             2                  9223372036854775807 3                YES            14            16
> rows: 1

ALTER TABLE TEST ALTER COLUMN ID SET DATA TYPE INTEGER;
> ok

SELECT COLUMN_NAME, DATA_TYPE, IS_IDENTITY, IDENTITY_START, IDENTITY_INCREMENT, IDENTITY_MAXIMUM, IDENTITY_MINIMUM,
    IDENTITY_CYCLE, IDENTITY_BASE, IDENTITY_CACHE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST' AND COLUMN_NAME = 'ID';
> COLUMN_NAME DATA_TYPE IS_IDENTITY IDENTITY_START IDENTITY_INCREMENT IDENTITY_MAXIMUM IDENTITY_MINIMUM IDENTITY_CYCLE IDENTITY_BASE IDENTITY_CACHE
> ----------- --------- ----------- -------------- ------------------ ---------------- ---------------- -------------- ------------- --------------
> ID          INTEGER   YES         10             2                  2147483647       3                YES            14            16
> rows: 1

DROP TABLE TEST;
> ok

CREATE MEMORY TABLE TEST(ID BIGINT GENERATED ALWAYS AS IDENTITY, V INT);
> ok

SELECT COLUMN_NAME, IS_IDENTITY, IDENTITY_GENERATION
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST' AND COLUMN_NAME = 'ID';
> COLUMN_NAME IS_IDENTITY IDENTITY_GENERATION
> ----------- ----------- -------------------
> ID          YES         ALWAYS
> rows: 1

INSERT INTO TEST(V) VALUES 10;
> update count: 1

INSERT INTO TEST(ID, V) VALUES (2, 20);
> exception GENERATED_COLUMN_CANNOT_BE_ASSIGNED_1

UPDATE TEST SET ID = ID + 1;
> exception GENERATED_COLUMN_CANNOT_BE_ASSIGNED_1

MERGE INTO TEST(ID, V) KEY(V) VALUES (2, 10);
> exception GENERATED_COLUMN_CANNOT_BE_ASSIGNED_1

MERGE INTO TEST USING (VALUES (2, 20)) S(ID, V) ON TEST.ID = S.ID
    WHEN NOT MATCHED THEN INSERT VALUES (S.ID, S.V);
> exception GENERATED_COLUMN_CANNOT_BE_ASSIGNED_1

@reconnect

SELECT COLUMN_NAME, IS_IDENTITY, IDENTITY_GENERATION
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST' AND COLUMN_NAME = 'ID';
> COLUMN_NAME IS_IDENTITY IDENTITY_GENERATION
> ----------- ----------- -------------------
> ID          YES         ALWAYS
> rows: 1

SCRIPT NODATA NOPASSWORDS NOSETTINGS NOVERSION;
> SCRIPT
> -----------------------------------------------------------------------------------------------------------------------------------
> CREATE USER IF NOT EXISTS "SA" PASSWORD '' ADMIN;
> CREATE MEMORY TABLE "PUBLIC"."TEST"( "ID" BIGINT GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 2) NOT NULL, "V" INTEGER );
> -- 1 +/- SELECT COUNT(*) FROM PUBLIC.TEST;
> rows (ordered): 3

DROP TABLE TEST;
> ok

CREATE TABLE TEST(ID BIGINT, V INT);
> ok

ALTER TABLE TEST ALTER COLUMN ID SET GENERATED ALWAYS;
> ok

INSERT INTO TEST(V) VALUES 1;
> update count: 1

SELECT COLUMN_NAME, IS_IDENTITY, IDENTITY_GENERATION, IDENTITY_BASE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST' AND COLUMN_NAME = 'ID';
> COLUMN_NAME IS_IDENTITY IDENTITY_GENERATION IDENTITY_BASE
> ----------- ----------- ------------------- -------------
> ID          YES         ALWAYS              2
> rows: 1

ALTER TABLE TEST ALTER COLUMN ID SET GENERATED BY DEFAULT;
> ok

INSERT INTO TEST(V) VALUES 2;
> update count: 1

SELECT COLUMN_NAME, IS_IDENTITY, IDENTITY_GENERATION, IDENTITY_BASE
    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST' AND COLUMN_NAME = 'ID';
> COLUMN_NAME IS_IDENTITY IDENTITY_GENERATION IDENTITY_BASE
> ----------- ----------- ------------------- -------------
> ID          YES         BY DEFAULT          3
> rows: 1

DROP TABLE TEST;
> ok

CREATE TABLE TEST(A INT DEFAULT 1, B INT DEFAULT 2 DEFAULT ON NULL);
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, DEFAULT_ON_NULL FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT DEFAULT_ON_NULL
> ----------- -------------- ---------------
> A           1              FALSE
> B           2              TRUE
> rows: 2

ALTER TABLE TEST ALTER COLUMN A SET DEFAULT ON NULL;
> ok

ALTER TABLE TEST ALTER COLUMN B DROP DEFAULT ON NULL;
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, DEFAULT_ON_NULL FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT DEFAULT_ON_NULL
> ----------- -------------- ---------------
> A           1              TRUE
> B           2              FALSE
> rows: 2

ALTER TABLE TEST ALTER COLUMN A SET DEFAULT ON NULL;
> ok

ALTER TABLE TEST ALTER COLUMN B DROP DEFAULT ON NULL;
> ok

SELECT COLUMN_NAME, COLUMN_DEFAULT, DEFAULT_ON_NULL FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TEST';
> COLUMN_NAME COLUMN_DEFAULT DEFAULT_ON_NULL
> ----------- -------------- ---------------
> A           1              TRUE
> B           2              FALSE
> rows: 2

DROP TABLE TEST;
> ok
