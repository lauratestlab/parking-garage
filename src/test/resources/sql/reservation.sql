select *
from spots s1_0
         left join reservations r1_0 on s1_0.spot_id = r1_0.spot_id
    AND r1_0.start_time < '2024-10-22 11:45:00' AND
                                        r1_0.end_time > '2024-10-22 11:00:00' AND r1_0.status = 'ORDERED'
where r1_0.reservation_id is null;


select s1_0.spot_id, s1_0.floor_id, s1_0.name
from spots s1_0
         left join reservations r1_0
                   on s1_0.spot_id = r1_0.spot_id
                          and r1_0.start_time<'2024-10-30 22:42:35'
                          and COALESCE(r1_0.end_time, r1_0.start_time + interval '24 hours')>'2024-10-29 22:42:35'
                          and r1_0.status in ('STARTED', 'ORDERED')
where r1_0.reservation_id is null
order by s1_0.floor_id, s1_0.name
offset 0 rows fetch first 1 rows only;


select s1_0.spot_id, s1_0.floor_id, s1_0.name
from spots s1_0
         left join reservations r1_0
                   on s1_0.spot_id = r1_0.spot_id
                       and r1_0.start_time<'2024-10-30 22:42:35'
                       and COALESCE(r1_0.end_time, date_add(r1_0.start_time, '24 hour'::interval, 'Europe/Warsaw'))>'2024-10-29 22:42:35'
                       and r1_0.status in ('STARTED', 'ORDERED')

where s1_0.car_id is null and
    r1_0.reservation_id is null
order by s1_0.floor_id, s1_0.name
offset 0 rows fetch first 1 rows only;


select date_add(now(), interval '1 day') as date;

select s1_0.spot_id, s1_0.car_id, s1_0.floor_id, s1_0.name
from spots s1_0
         left join reservations r1_0 on s1_0.spot_id = r1_0.spot_id and r1_0.start_time<? and
                                        coalesce(r1_0.end_time, date_add(r1_0.start_time, 'interval 1 day')) >? and
                                        r1_0.status in (?, ?)
where r1_0.reservation_id is null
order by s1_0.floor_id, s1_0.name
offset ? rows fetch first ? rows only;


select s1_0.spot_id, s1_0.car_id, s1_0.floor_id, s1_0.name
from spots s1_0
         left join reservations r1_0 on s1_0.spot_id = r1_0.spot_id and r1_0.start_time<? and
                                        coalesce(r1_0.end_time, add_hours(r1_0.start_time, 24)) >? and
                                        r1_0.status in (?, ?)
where r1_0.reservation_id is null
order by s1_0.floor_id, s1_0.name
offset ? rows fetch first ? rows only




