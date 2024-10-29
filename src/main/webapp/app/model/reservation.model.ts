export interface ReservationResponse {
    id: number;
    startTime: string;
    endTime: string;
    price: number;
    userId: number;
    spotId: number;
    status: string;
    carId: number;
    confirmationCode: string;
}