ALTER TABLE SYSCD
 DROP PRIMARY KEY CASCADE;
DROP TABLE SYSCD CASCADE CONSTRAINTS;

CREATE TABLE SYSCD
(
  GRP  VARCHAR2(32 BYTE),
  CD   VARCHAR2(32 BYTE),
  PCD  VARCHAR2(32 BYTE),
  VAL  VARCHAR2(512 BYTE),
  ORD  NUMBER(3)                                DEFAULT 0                     NOT NULL,
  USE  CHAR(1 BYTE)                            DEFAULT ('Y'),
  DSC  VARCHAR2(128 BYTE),
  ANN  VARCHAR2(32 BYTE)
)
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

COMMENT ON COLUMN SYSCD.GRP IS '구분';

COMMENT ON COLUMN SYSCD.CD IS '코드';

COMMENT ON COLUMN SYSCD.PCD IS '부모코드';

COMMENT ON COLUMN SYSCD.VAL IS '값';

COMMENT ON COLUMN SYSCD.ORD IS '정렬순서';

COMMENT ON COLUMN SYSCD.USE IS 'Y:사용,N:미사용';

COMMENT ON COLUMN SYSCD.DSC IS '설명';

COMMENT ON COLUMN SYSCD.ANN IS '부가기능';


CREATE UNIQUE INDEX PK_SYSCD ON SYSCD
(GRP, CD)
LOGGING
NOPARALLEL;


ALTER TABLE SYSCD ADD (
  CONSTRAINT PK_SYSCD
 PRIMARY KEY
 (GRP, CD));


SET DEFINE OFF;
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'SYSTEM', '-', NULL, 0, 'Y', 
    '샘플 데이터');
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'MENU', 'SYSTEM', '샘플 메뉴', 0, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'INFORMATION', 'MENU', '소개', 1, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'MYCOM', 'INFORMATION', '회사', 1, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'COM_INTRO', 'MYCOM', '회사소개', 1, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'AD_MOVIE', 'MYCOM', '홍보영상', 2, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'BUSINESS', 'INFORMATION', '사업소개', 2, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'CORPERATOR', 'BUSINESS', '계열사', 1, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'GLOBAL_CHAIN', 'BUSINESS', '해외사업장', 2, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'MGMT_PLSP', 'INFORMATION', '경영철학', 3, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'IDEA', 'MGMT_PLSP', '경영이념', 1, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'PRINCIPLE', 'MGMT_PLSP', '경영원칙', 2, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'CORE_VALUE', 'MGMT_PLSP', '핵심가치', 3, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'HISTORY', 'INFORMATION', '회사연혁', 5, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'RESULT', 'INFORMATION', '회사실적', 4, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'NEWS', 'MENU', '소식', 2, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'BROADCAST', 'NOTICE', '보도자료', 1, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'INSIDE', 'NOTICE', '인사이드', 2, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'MOVIE', 'NEWS', '영상', 2, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'CAMPAIGN', 'NEWS', '캠페인', 3, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'STORY', 'NEWS', '사원이야기', 4, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'SHARE', 'MENU', '나눔', 3, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'CONTRIBUTE', 'SHARE', '사회공헌', 1, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'CULTURE', 'SHARE', '문화/예술', 3, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'EDUCATION', 'SHARE', '교육/장학', 4, 'Y', 
    NULL);
Insert into SYSCD
   (GRP, CD, PCD, VAL, ORD, USE, 
    DSC)
 Values
   ('SAMPLE', 'SPORTS', 'SHARE', '스포츠/스폰서쉽', 2, 'Y', 
    NULL);
COMMIT;
