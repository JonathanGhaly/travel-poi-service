-- Index for filtering by category
CREATE INDEX IF NOT EXISTS idx_pois_category
    ON pois (category);

-- Index for sorting or filtering by created_at
CREATE INDEX IF NOT EXISTS idx_pois_created_at
    ON pois (created_at DESC);

-- Composite index for category + created_at (useful for paginated queries)
CREATE INDEX IF NOT EXISTS idx_pois_category_created_at
    ON pois (category, created_at DESC);

-- GIN index for JSONB opening_hours queries
CREATE INDEX IF NOT EXISTS idx_pois_opening_hours_gin
    ON pois
    USING GIN (opening_hours);

-- Basic index for latitude and longitude (for bounding box queries)
CREATE INDEX IF NOT EXISTS idx_pois_lat_lon
    ON pois (latitude, longitude);

-- Index for updated_at (useful for sync or event publishing)
CREATE INDEX IF NOT EXISTS idx_pois_updated_at
    ON pois (updated_at DESC);
