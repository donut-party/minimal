CREATE TABLE IF NOT EXISTS todo (
  id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  todo_list_id int NOT NULL REFERENCES todo_list (id) ON DELETE CASCADE,
  description varchar(255),
  due_date date,
  done boolean NOT NULL DEFAULT FALSE
);
