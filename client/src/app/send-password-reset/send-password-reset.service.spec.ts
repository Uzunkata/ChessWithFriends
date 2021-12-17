import { TestBed } from '@angular/core/testing';

import { SendPasswordResetService } from './send-password-reset.service';

describe('SendPasswordResetService', () => {
  let service: SendPasswordResetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SendPasswordResetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
