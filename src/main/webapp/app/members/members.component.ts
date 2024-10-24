import {Component, OnInit} from '@angular/core';
import {CurrencyPipe, NgForOf} from "@angular/common";
import {MemberApiService} from "./member-api.service";
import {Member} from "../model/member-model";

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
export class MembersComponent implements OnInit{
    members: Member[] = [];

    constructor(private api: MemberApiService) {
    }
    ngOnInit(): void {
        this.loadMemberModel();
    }

    loadMemberModel(): void {
        this.api.getAllMembers().subscribe(data => {
            this.members = data;
        })
        console.log(this.members);
    }

    // loadPricingModel(): void {
    //     this.api.getPricingList().subscribe(data =>{
    //         this.prices = data;
    //     });
    // }

}
