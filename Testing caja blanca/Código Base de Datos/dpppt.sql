--
-- PostgreSQL database dump
--

-- Dumped from database version 10.15
-- Dumped by pg_dump version 10.15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: auth_codes; Type: TABLE; Schema: public; Owner: dpppt
--

CREATE TABLE public.auth_codes (
    id bigint NOT NULL,
    value character varying(255)
);


ALTER TABLE public.auth_codes OWNER TO dpppt;

--
-- Name: exposees; Type: TABLE; Schema: public; Owner: dpppt
--

CREATE TABLE public.exposees (
    id integer NOT NULL,
    canton character varying(255),
    key character varying(255),
    key_date bigint NOT NULL,
    parroquia character varying(255),
    provincia character varying(255)
);


ALTER TABLE public.exposees OWNER TO dpppt;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: dpppt
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO dpppt;

--
-- Data for Name: auth_codes; Type: TABLE DATA; Schema: public; Owner: dpppt
--

COPY public.auth_codes (id, value) FROM stdin;
1	qh48uwe579
2	4sl91juyxy
3	ul7wyuhi9k
4	xwe4w9lxa4
5	ZFSWYSRIL9
6	XLU357UZK3
\.


--
-- Data for Name: exposees; Type: TABLE DATA; Schema: public; Owner: dpppt
--

COPY public.exposees (id, canton, key, key_date, parroquia, provincia) FROM stdin;
1	GUAYAQUIL	LJMaX+P79unB7QdzJnY8Al6RTW8UOanhbhlDbDeGYWM=	1609804800000	NUEVE DE OCTUBRE	GUAYAS
2	MANTA	C/qT9Ny2/4A+V57t03stVAW20ZFt9UgGg8aO2okdGb4=	1609804800000	ELOY ALFARO	MANABI
3	QUEVEDO	h1KQa9R5fJAtTkeU9dKy1A5DvrYsmOeP6tn0DrsEDHE=	1609804800000	GUAYACÁN	LOS RIOS
4	LA MANÁ	v3RCnd2BTV7EfeiYc9t2qqU8pwPZnXG2OvZ6Dm0egQc=	1609891200000	GUASAGANDA (CAB.EN GUASAGANDA	COTOPAXI
5	GUAYAQUIL	Hwb0moUVaTEmhQR1WcWzf7klht3aCBFu9AKl9zQoWsE=	1610064000000	NUEVE DE OCTUBRE	GUAYAS
6	DURÁN	Ewk2n4qyzJ/mAbkLvg6HFNc2qKfcyK/9OraojN8oBQk=	1612915200000	EL RECREO	GUAYAS
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: dpppt
--

SELECT pg_catalog.setval('public.hibernate_sequence', 9, true);


--
-- Name: auth_codes auth_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: dpppt
--

ALTER TABLE ONLY public.auth_codes
    ADD CONSTRAINT auth_codes_pkey PRIMARY KEY (id);


--
-- Name: exposees exposees_pkey; Type: CONSTRAINT; Schema: public; Owner: dpppt
--

ALTER TABLE ONLY public.exposees
    ADD CONSTRAINT exposees_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

