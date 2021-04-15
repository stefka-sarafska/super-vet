import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorBlankComponent } from './doctor-blank.component';

describe('DoctorBlankComponent', () => {
  let component: DoctorBlankComponent;
  let fixture: ComponentFixture<DoctorBlankComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorBlankComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorBlankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
