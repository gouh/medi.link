CREATE TABLE IF NOT EXISTS assistant
(
    assistant_id  UUID PRIMARY KEY  DEFAULT (UUID()),
    name          VARCHAR(100),
    contact_info  VARCHAR(150),
    created_at    DATETIME NOT NULL DEFAULT NOW(),
    updated_at    DATETIME NOT NULL DEFAULT NOW()
);

ALTER TABLE appointment
    ADD COLUMN IF NOT EXISTS assistant_id UUID,
    ADD FOREIGN KEY IF NOT EXISTS (assistant_id) REFERENCES assistant (assistant_id);

ALTER TABLE patient
    ADD COLUMN IF NOT EXISTS user_id UUID,
    ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE doctor
    ADD COLUMN IF NOT EXISTS user_id UUID,
    ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE assistant
    ADD COLUMN IF NOT EXISTS user_id UUID,
    ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE users DROP COLUMN IF EXISTS name;