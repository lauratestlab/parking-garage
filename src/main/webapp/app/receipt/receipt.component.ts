import {Component, inject} from '@angular/core';
import {VerifyCodeService} from "../revenue_api/verify-code.service";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CommonModule, CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-receipt',
  standalone: true,
  imports: [
      CommonModule,
      FormsModule,
      CurrencyPipe,
      ReactiveFormsModule
  ],
  templateUrl: './receipt.component.html',
  styleUrl: './receipt.component.css'
})
export class ReceiptComponent {
  currentPage: string = 'receipt';


  receiptData: any | null = null;
  confirmationCode = '';

  codeForm: FormGroup;
  fb = inject(FormBuilder);

  constructor(private api: VerifyCodeService) {
    this.codeForm = this.fb.group({
      confirmationCode: ['', Validators.required],
    });
  }

  getReceipt(): void {
    const confirmationCode = this.codeForm.getRawValue();
    this.api.verify(confirmationCode).subscribe({
      next: (data) => {
        this.receiptData = {
          startTime: data.startTime,
          endTime: data.endTime,
          spotId: data.spotId,
          price: data.price
        };
      },
      error: () => {
        window.alert('Error getting reservation data. Try again');
      }
    })
  }

}
