import { CommonModule } from '@angular/common';
import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ReservationApi} from "../revenue_api/reservation-api";
import {ReservationResponse} from "../model/reservation.model";


@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    FormsModule
  ],
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent implements OnInit{
  currentPage: string = 'reservations';

  reservationForm: FormGroup;
  fb = inject(FormBuilder);

  reservations: ReservationResponse[] = [];

  constructor(private api: ReservationApi) {
    this.reservationForm = this.fb.group({
      startTime: ['', Validators.required],
      endTime: ['', Validators.required],
      car: this.fb.group({
        color: ['', Validators.required],
        make: ['', Validators.required],
        model: ['', Validators.required],
        registration: ['', Validators.required]
      }),
      paymentMethod: this.fb.group({
        card_number: ['', [Validators.required]],
        ccv: ['', [Validators.required]],
        expirationDate: ['', Validators.required],
        fullName: ['', Validators.required],
        address: this.fb.group({
          deliveryStreet: ['', Validators.required],
          deliveryCity: ['', Validators.required],
          deliveryState: ['', Validators.required],
          deliveryZip: ['', [Validators.required]]
        })
      })
    });
  }

  ngOnInit(): void {
    this.fetchAll();
  }

  fetchAll(): void {
    this.api.get().subscribe((data) => {
      this.reservations = data;
      console.log(this.reservations);
    })
  }

  addReservation() {
    const request: any = this.reservationForm.value;
    console.log('Form submitted: ', request);

    if(this.reservationForm.valid) {
      this.api.add(request).subscribe({
          next: () => {
            window.alert('Booking was successful!');
          },
          error: () => {
            window.alert('Booking was unsuccessful!');
          }
      });
    }
  }

}
