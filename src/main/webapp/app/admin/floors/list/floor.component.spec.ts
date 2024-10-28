jest.mock('app/core/auth/account.service');

import { ComponentFixture, TestBed, fakeAsync, inject, tick, waitForAsync } from '@angular/core/testing';
import { HttpHeaders, HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { FloorService } from '../service/floor.service';
import { Floor } from '../floor.model';

import FloorManagementComponent from './floor.component';

describe('Floor Management Component', () => {
  let comp: FloorManagementComponent;
  let fixture: ComponentFixture<FloorManagementComponent>;
  let service: FloorService;
  let mockAccountService: AccountService;
  const data = of({
    defaultSort: 'id,asc',
  });
  const queryParamMap = of(
    jest.requireActual('@angular/router').convertToParamMap({
      page: '1',
      size: '1',
      sort: 'id,desc',
    }),
  );

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [FloorManagementComponent],
      providers: [provideHttpClient(), { provide: ActivatedRoute, useValue: { data, queryParamMap } }, AccountService],
    })
      .overrideTemplate(FloorManagementComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FloorManagementComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FloorService);
    mockAccountService = TestBed.inject(AccountService);
    mockAccountService.identity = jest.fn(() => of(null));
  });

  describe('OnInit', () => {
    it('Should call load all on init', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        jest.spyOn(service, 'query').mockReturnValue(
          of(
            new HttpResponse({
              body: [new Floor(123, "test")],
              headers,
            }),
          ),
        );

        // WHEN
        comp.ngOnInit();
        tick(); // simulate async

        // THEN
        expect(service.query).toHaveBeenCalled();
        expect(comp.floors()?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      }),
    ));
  });

  describe('setActive', () => {
    it('Should update floor and call load all', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        const floor = new Floor(123, "G");
        jest.spyOn(service, 'query').mockReturnValue(
          of(
            new HttpResponse({
              body: [floor],
              headers,
            }),
          ),
        );
        jest.spyOn(service, 'update').mockReturnValue(of(floor));

        // WHEN
        comp.setActive(floor, true);
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith({ ...floor, activated: true });
        expect(service.query).toHaveBeenCalled();
        expect(comp.floors()?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      }),
    ));
  });
});
