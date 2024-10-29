import { CommonModule } from '@angular/common';
import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ReservationApi} from "../revenue_api/reservation-api";
import {ReservationResponse} from "../model/reservation.model";
import { AccountService } from 'app/core/auth/account.service';
import HasAnyAuthorityDirective from "../shared/auth/has-any-authority.directive";


@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
    HasAnyAuthorityDirective
  ],
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent implements OnInit{
  currentPage: string = 'reservations';

  reservationForm: FormGroup;
  fb = inject(FormBuilder);

  reservations: ReservationResponse[] = [];

  constructor(private api: ReservationApi, private accountService: AccountService) {

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
    if(this.accountService.hasAnyAuthority("ROLE_ADMIN")) {
      this.fetchAll();
    } else {
      this.fetchAllForUser();
    }
  }

  fetchAll(): void {
    this.api.getAll().subscribe((data) => {
      this.reservations = data;
      console.log(this.reservations);
    })
  }

  fetchAllForUser(): void {
    this.api.getAllForUser().subscribe((data) => {
      this.reservations = data;
      console.log(this.reservations);
    })
  }

  addReservation() {
    const request: any = this.reservationForm.value;

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
