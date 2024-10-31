import {CommonModule} from '@angular/common';
import {Component, inject, signal} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CheckinService} from "app/admin/checkin/checkin.service";
import HasAnyAuthorityDirective from "../../shared/auth/has-any-authority.directive";
import {AlertErrorComponent} from "../../shared/alert/alert-error.component";
import {AlertComponent} from "../../shared/alert/alert.component";
import {ReservationRequest} from "../../model/reservation-request.model"
import {ReservationInfo} from "../../model/reservation-info.model";
import {map} from "rxjs/operators";
import {DATE_TIME_FORMAT} from "../../config/input.constants";

const reservationTemplate = {} as ReservationRequest;

@Component({
  selector: 'app-checkin',
  standalone: true,
    imports: [
        ReactiveFormsModule,
        CommonModule,
        FormsModule,
        HasAnyAuthorityDirective,
        AlertErrorComponent,
        AlertComponent
    ],
  templateUrl: './checkin.component.html',
  styleUrl: './checkin.component.css'
})
export class CheckinComponent {
  currentPage: string = 'checkout';

    isSaving = signal(false);
    imagePath : string = "";
    ticketInfo: ReservationInfo | undefined;

    editForm = new FormGroup({
        color: new FormControl(reservationTemplate.color, {
            nonNullable: true,
            validators: [
                Validators.required,
                Validators.minLength(1),
                Validators.maxLength(50),
            ],
        }),
        make: new FormControl(reservationTemplate.make, {
            nonNullable: true,
            validators: [
                Validators.required,
                Validators.minLength(1),
                Validators.maxLength(50),
            ],
        }),
        model: new FormControl(reservationTemplate.model, { validators: [Validators.maxLength(25)] }),
        registration: new FormControl(reservationTemplate.registration, { validators: [Validators.maxLength(20)] }),
        email: new FormControl(reservationTemplate.email
        //     , {
        //     nonNullable: false,
        //     validators: [Validators.minLength(0), Validators.maxLength(254), Validators.email],
        // }
        ),
    });

    private checkinService = inject(CheckinService);

    addReservation(): void {
        this.isSaving.set(true);
        const reservationRequest = this.editForm.getRawValue();
        if (!reservationRequest.email) {
            reservationRequest.email = null;
        }
        this.checkinService.addNewReservation(reservationRequest).pipe(map(response => {
            if (response.success) {
                // const date = new Date(response.startTime);
                // response.startTime = date.toLocaleDateString(DATE_TIME_FORMAT);
                this.ticketInfo = response;
                this.ticketInfo = response;
                this.imagePath = this.checkinService.getQrImage(response.confirmationCode);
            } else {
                window.alert(response.message);
            }
            return response.success;
        })).subscribe({
            next: () => this.onSaveSuccess(),
            error: () => this.onSaveError(),
        });
    }

    getQrImage() {
        this.checkinService.getQrImage("test")
    }

    private onSaveSuccess(): void {
        this.isSaving.set(false);
    }

    private onSaveError(): void {
        this.isSaving.set(false);
    }

}
