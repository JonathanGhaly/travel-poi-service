-- =========================
-- TAGS TABLE
-- =========================

CREATE TABLE IF NOT EXISTS tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ DEFAULT NOW()
    );

-- Index for fast tag name lookup
CREATE INDEX IF NOT EXISTS idx_tags_name
    ON tags (name);


-- =========================
-- POI_TAGS JOIN TABLE
-- =========================

CREATE TABLE IF NOT EXISTS poi_tags (
    poi_uuid UUID NOT NULL,
    tag_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    CONSTRAINT fk_poi
    FOREIGN KEY (poi_uuid)
    REFERENCES pois (uuid)
    ON DELETE CASCADE,

    CONSTRAINT fk_tag
    FOREIGN KEY (tag_id)
    REFERENCES tags (id)
    ON DELETE CASCADE,

    CONSTRAINT pk_poi_tags
    PRIMARY KEY (poi_uuid, tag_id)
    );

-- Index for filtering POIs by tag
CREATE INDEX IF NOT EXISTS idx_poi_tags_tag_id
    ON poi_tags (tag_id);

-- Index for fetching tags of a POI
CREATE INDEX IF NOT EXISTS idx_poi_tags_poi_uuid
    ON poi_tags (poi_uuid);
-- =========================
-- TAGS TABLE
-- =========================

CREATE TABLE IF NOT EXISTS tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ DEFAULT NOW()
    );

-- Index for fast tag name lookup
CREATE INDEX IF NOT EXISTS idx_tags_name
    ON tags (name);


-- =========================
-- POI_TAGS JOIN TABLE
-- =========================

CREATE TABLE IF NOT EXISTS poi_tags (
    poi_uuid UUID NOT NULL,
    tag_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    CONSTRAINT fk_poi
    FOREIGN KEY (poi_uuid)
    REFERENCES pois (uuid)
    ON DELETE CASCADE,

    CONSTRAINT fk_tag
    FOREIGN KEY (tag_id)
    REFERENCES tags (id)
    ON DELETE CASCADE,

    CONSTRAINT pk_poi_tags
    PRIMARY KEY (poi_uuid, tag_id)
    );

-- Index for filtering POIs by tag
CREATE INDEX IF NOT EXISTS idx_poi_tags_tag_id
    ON poi_tags (tag_id);

-- Index for fetching tags of a POI
CREATE INDEX IF NOT EXISTS idx_poi_tags_poi_uuid
    ON poi_tags (poi_uuid);
