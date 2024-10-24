CREATE TYPE tasks_priority_enum AS ENUM ('HIGH', 'MEDIUM', 'LOW');

CREATE TABLE "tasks" (
    id SERIAL PRIMARY KEY,
    to_do_id INTEGER REFERENCES to_dos(id) ON DELETE CASCADE,
    description TEXT,
    priority tasks_priority_enum NOT NULL,
    checked BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);