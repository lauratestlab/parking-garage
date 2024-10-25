import { Component, OnInit } from '@angular/core';
import { SpotService } from '../service/spot-api';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Spot } from '../model/spot-model';

@Component({
  selector: 'app-spot',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './spot.component.html',
  styleUrl: './spot.component.css'
})
export class SpotComponent implements OnInit{
  currentPage: string = 'spot';
  
  spot: Spot[] = [];

  constructor(private api: SpotService, private router: Router) { }
  ngOnInit(): void {
      this.loadSpotModel();
  }

  loadSpotModel(): void {
    this.api.getSpotList().subscribe(data =>{
      this.spot = data;
    });
  }
}
