CREATE TABLE IF NOT EXISTS url (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_url VARCHAR(255) NOT NULL,
    short_url VARCHAR(255) NOT NULL,
    UNIQUE (original_url),
    UNIQUE (short_url)
    );


INSERT INTO url (original_url, short_url) VALUES ('https://www.linkedin.com/in/danieljulcamorohuanca/', 'exmpl1');
