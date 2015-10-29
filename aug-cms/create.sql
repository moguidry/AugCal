-- Table: article

-- DROP TABLE article;

CREATE TABLE article
(
  cmsid bigserial NOT NULL,
  cmstitle text,
  cmscontent text,
  cmsdatecreated timestamp without time zone,
  cmslastmodified timestamp without time zone,
  cmsisactive boolean,
  cmsauthorid character varying(100),
  cmsminicontent text,
  CONSTRAINT cmsid_pk PRIMARY KEY (cmsid)
);
