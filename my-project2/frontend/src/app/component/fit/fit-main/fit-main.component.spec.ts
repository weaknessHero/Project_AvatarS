import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FitMainComponent } from './fit-main.component';

describe('FitMainComponent', () => {
  let component: FitMainComponent;
  let fixture: ComponentFixture<FitMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FitMainComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FitMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
