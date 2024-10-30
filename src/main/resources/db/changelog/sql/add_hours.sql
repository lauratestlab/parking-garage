create or replace function add_hours(date_and_time timestamp without time zone, hours integer) returns timestamp without time zone
    language plpgsql
as
$$
declare
begin
    return date_and_time + make_interval(hours => hours);
end;
$$;

