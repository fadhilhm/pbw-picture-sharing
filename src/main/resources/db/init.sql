CREATE TABLE account(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(80) NOT NULL,
    url_pfp VARCHAR(50),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    role VARCHAR NOT NULL,

    CONSTRAINT role_values
        CHECK ( role IN ('admin', 'user') )
);

CREATE TABLE post(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id INT NOT NULL,
    url_image VARCHAR(50) NOT NULL,
    description VARCHAR(120) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_account_id
        FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE tag(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(25),
    is_active BOOLEAN
);

CREATE TABLE has_tag(
    post_id INT NOT NULL,
    tag_id INT NOT NULL,

    CONSTRAINT pk_composite_has_tag
        PRIMARY KEY (post_id, tag_id),
    CONSTRAINT fk_post
        FOREIGN KEY (post_id) REFERENCES post(id),
    CONSTRAINT fk_tag
        FOREIGN KEY (tag_id) REFERENCES tag(id)
);

CREATE TABLE comment(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id INT NOT NULL,
    post_id INT NOT NULL,
    comment VARCHAR(150) NOT NULL,
    created_at TIMESTAMP,

    CONSTRAINT fk_account_id
        FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT fk_post
        FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE views(
    account_id INT NOT NULL,
    post_id INT NOT NULL,

    CONSTRAINT pk_composite_views
        PRIMARY KEY (account_id, post_id),
    CONSTRAINT fk_account_id
        FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT fk_post
        FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE likes(
    account_id INT NOT NULL,
    post_id INT NOT NULL,

    CONSTRAINT pk_composite_likes
        PRIMARY KEY (account_id, post_id),
    CONSTRAINT fk_account_id
        FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT fk_post
        FOREIGN KEY (post_id) REFERENCES post(id)
);