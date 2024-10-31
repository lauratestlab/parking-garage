export interface ReservationInfo {
    success: boolean;
    overstay: boolean;
    message: string;
    confirmationCode: string;
    userId: number;
    startTime: string;
    endTime: string | null;
    spotId: number;
    carId: number;
    price: number;
}
