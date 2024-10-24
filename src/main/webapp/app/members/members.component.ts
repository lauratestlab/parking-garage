import { Component } from '@angular/core';
import {CurrencyPipe, NgForOf} from "@angular/common";

@Component({
  selector: 'app-members',
  standalone: true,
    imports: [
        CurrencyPipe,
        NgForOf
    ],
  templateUrl: './members.component.html',
  styleUrl: './members.component.css'
})
export class MembersComponent {

}
