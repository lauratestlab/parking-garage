jest.mock('app/core/auth/account.service');

import { ComponentFixture, TestBed, fakeAsync, inject, tick, waitForAsync } from '@angular/core/testing';
import { HttpHeaders, HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { SpotService } from '../service/spot.service';
import { Spot } from '../spot.model';

import SpotManagementComponent from './spot.component';

describe('Spot Management Component', () => {
  let comp: SpotManagementComponent;
  let fixture: ComponentFixture<SpotManagementComponent>;
  let service: SpotService;
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
      imports: [SpotManagementComponent],
      providers: [provideHttpClient(), { provide: ActivatedRoute, useValue: { data, queryParamMap } }, AccountService],
    })
      .overrideTemplate(SpotManagementComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpotManagementComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SpotService);
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
              body: [new Spot(123, "test")],
              headers,
            }),
          ),
        );

        // WHEN
        comp.ngOnInit();
        tick(); // simulate async

        // THEN
        expect(service.query).toHaveBeenCalled();
        expect(comp.spots()?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      }),
    ));
  });

  describe('setActive', () => {
    it('Should update spot and call load all', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        const spot = new Spot(123);
        jest.spyOn(service, 'query').mockReturnValue(
          of(
            new HttpResponse({
              body: [spot],
              headers,
            }),
          ),
        );
        jest.spyOn(service, 'update').mockReturnValue(of(spot));

        // WHEN
        comp.setActive(spot, true);
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith({ ...spot, activated: true });
        expect(service.query).toHaveBeenCalled();
        expect(comp.spots()?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      }),
    ));
  });
});
