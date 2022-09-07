create table if not exists basket (
    id uuid not null,
    customer_id int8 not null,
    total decimal,
    totalPayable decimal,
    totalPromos decimal,
    constraint basket_pkey primary key (id)
);

create table if not exists basket_products (
    basket_id uuid not null,
    product_id varchar(32) not null,
    constraint basket_fkey foreign key (basket_id)
        references basket(id)
);

create index bkt_ctm_index on basket (customer_id);

create table if not exists customer (
    id int8 not null,
    name varchar(40) not null,
    constraint customer_pkey primary key (id)
);
