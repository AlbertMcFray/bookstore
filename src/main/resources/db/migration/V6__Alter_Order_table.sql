ALTER TABLE client_order
    RENAME COLUMN client_id TO user_id;

ALTER TABLE client_order
    RENAME COLUMN client_name TO user_username;
