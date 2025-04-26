DO $$
BEGIN
    -- Check if the table 'order' exists in the 'public' schema
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.tables
        WHERE table_name = 'order'
        AND table_schema = 'public'
    ) THEN
        -- If the table does not exist, it will be created
CREATE TABLE public.order (
                              id BIGSERIAL PRIMARY KEY,
                              order_code VARCHAR(50) NOT NULL UNIQUE,
                              description VARCHAR(255),
                              status VARCHAR(20) CHECK (
                                  status IN ('WAITING', 'PREPARATION', 'READY')
                                  )
);

RAISE NOTICE 'Table "order" created successfully.';
ELSE
        -- Print a message if the table already exists
        RAISE NOTICE 'The table "order" already exists in the "public" schema.';
END IF;
END $$;
