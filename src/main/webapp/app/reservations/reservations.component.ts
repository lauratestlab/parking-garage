import { CommonModule } from '@angular/common';
import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ReservationApi} from "../service/reservation-api";


@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent{
  currentPage: string = 'reservations';

  reservationForm: FormGroup;
  fb = inject(FormBuilder);

  constructor(private api: ReservationApi) {
    this.reservationForm = this.fb.group({
      startDate: ['', Validators.required],
      startTime: ['', Validators.required],
      endDate: ['', Validators.required],
      endTime: ['', Validators.required],
      carInfo: this.fb.group({
        color: ['', Validators.required],
        make: ['', Validators.required],
        model: ['', Validators.required],
        registrationNumber: ['', Validators.required]
      }),
      paymentInfo: this.fb.group({
        cardNumber: ['', [Validators.required]],
        ccv: ['', [Validators.required]],
        expirationDate: ['', Validators.required],
        fullName: ['', Validators.required],
        address: this.fb.group({
          street: ['', Validators.required],
          city: ['', Validators.required],
          state: ['', Validators.required],
          zipCode: ['', [Validators.required]]
        })
      })
    });
  }

  submitForm() {
    const request: any = this.reservationForm.value;
    console.log('Form submitted: ', request);

    if(this.reservationForm.valid) {
      this.api.addReservation(request).subscribe({
          next: () => {
            console.log('Check db');
          },
          error: () => {
            console.error("'Error submitting reservation data");
          }
      });

    } else {

    }
  }

}
