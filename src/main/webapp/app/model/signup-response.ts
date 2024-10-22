export interface SignupResponse {
    id: number;
    login: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    activated: boolean;
    activationKey: string;
}