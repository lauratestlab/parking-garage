import { Component, OnInit } from '@angular/core';
import { FloorService } from '../service/floor-api';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Floor } from '../model/floor-model';

@Component({
  selector: 'app-floor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './floor.component.html',
  styleUrl: './floor.component.css'
})
export class FloorComponent implements OnInit{
  currentPage: string = 'floor';
  
  floor: Floor[] = [];

  constructor(private api: FloorService, private router: Router) { }
  ngOnInit(): void {
      this.loadFloorModel();
  }

  loadFloorModel(): void {
    this.api.getFloorList().subscribe(data =>{
      this.floor = data;
    });
  }

}
