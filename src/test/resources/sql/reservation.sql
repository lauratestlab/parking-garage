select *
from spots s1_0
         left join reservations r1_0 on s1_0.spot_id = r1_0.spot_id
    AND r1_0.start_time < '2024-10-22 11:45:00' AND
                                        r1_0.end_time > '2024-10-22 11:00:00' AND r1_0.status = 'ORDERED'
where r1_0.reservation_id is null


