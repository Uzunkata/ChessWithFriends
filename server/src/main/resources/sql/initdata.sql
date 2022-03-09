
INSERT INTO users (email, emailverification, password, enabled) values ('test1@test', true, '$2a$12$.IPUQiOUrt2M1N8ceg3Pz.MvQdt3GX2ZFxZtxzi7HGzbK6lf2mOH6', true) ON CONFLICT DO NOTHING;
INSERT INTO users (email, emailverification, password, enabled) values ('test2@test', true, '$2a$12$.IPUQiOUrt2M1N8ceg3Pz.MvQdt3GX2ZFxZtxzi7HGzbK6lf2mOH6', true) ON CONFLICT DO NOTHING;
