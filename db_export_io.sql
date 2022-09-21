CREATE TABLE "courses" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(255),
  "code" varchar(255),
  "description" text,
  "date_created" date,
  "date_last_modified" date
);

CREATE TABLE "course_moderator" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" bigint,
  "course_id" bigint
);

CREATE TABLE "materials" (
  "id" BIGSERIAL PRIMARY KEY,
  "title" varchar(255),
  "created_by" bigint,
  "date_created" date,
  "is_published" boolean,
  "description" text,
  "approved_by" bigint,
  "course_id" bigint,
  "up_votes" int default 0,
  "down_votes" int default
);

CREATE TABLE "questions" (
  "id" BIGSERIAL PRIMARY KEY,
  "description" text,
  "answer" text
);

CREATE TABLE "files" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(255),
  "download_url" varchar,
  "data" bytea,
  "date_uploaded" datetime,
  "virus_total_link" text,
  "safe" boolean
);

CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY,
  "username" varchar(255),
  "first_name" text not null ,
  "last_name" text not null ,
  "password" varchar(255),
  "email" varchar(255),
  "date_created" datetime,
  "is_activated" boolean,
  "role" int
);

CREATE TABLE "comments" (
  "id" BIGSERIAL PRIMARY KEY,
  "description" text,
  "up_votes" int,
  "down_votes" int,
  "created_by" bigint,
  "date_posted" datetime,
  "material_id" bigint
);

CREATE TABLE "roles" (
  "id" serial PRIMARY KEY,
  "role_name" varchar(255)
);

CREATE TABLE "types" (
  "id" serial PRIMARY KEY,
  "name" varchar(255)
);

CREATE TABLE "items" (
    "id" bigserial primary key not null,
    "type" int references types("id"),
    "material_id" bigint references materials("id"),
    "question_id" bigint references questions("id")
)

ALTER TABLE "course_moderator" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "course_moderator" ADD FOREIGN KEY ("course_id") REFERENCES "courses" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("approved_by") REFERENCES "users" ("id");

ALTER TABLE "materials" ADD FOREIGN KEY ("course_id") REFERENCES "courses" ("id");

ALTER TABLE "users" ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("material_id") REFERENCES "materials" ("id");

insert into roles(id,name) values (0, 'SUPER_ADMIN'), (1, 'ADMIN'), (2, 'MODERATOR'), (3, 'BASIC'),