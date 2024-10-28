import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { Authority } from 'app/config/authority.constants';
import { Spot } from '../spot.model';

import { SpotService } from './spot.service';

describe('Spot Service', () => {
  let service: SpotService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(SpotService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('Service methods', () => {
    it('should return Spot', () => {
      let expectedResult: string | undefined;

      service.find('spot').subscribe(received => {
        expectedResult = received.login;
      });

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(new Spot(123, 'spot'));
      expect(expectedResult).toEqual('spot');
    });

    it('should return Authorities', () => {
      let expectedResult: string[] = [];

      service.authorities().subscribe(authorities => {
        expectedResult = authorities;
      });
      const req = httpMock.expectOne({ method: 'GET' });

      req.flush([{ name: Authority.USER }, { name: Authority.ADMIN }]);
      expect(expectedResult).toEqual([Authority.USER, Authority.ADMIN]);
    });

    it('should propagate not found response', () => {
      let expectedResult = 0;

      service.find('spot').subscribe({
        error: (error: HttpErrorResponse) => (expectedResult = error.status),
      });

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush('Invalid request parameters', {
        status: 404,
        statusText: 'Bad Request',
      });
      expect(expectedResult).toEqual(404);
    });
  });
});
